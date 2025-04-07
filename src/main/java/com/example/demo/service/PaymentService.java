package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.PaymentDto;
import com.example.demo.entity.*;
import com.example.demo.jwt.Token;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.repositoryInterface.*;
import com.example.demo.response.OrderList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    IOrderRespository orderRespository;
    @Autowired
    IOrderDetailResposiotry orderDetailResposiotry;
    @Autowired
    IOrderTransactionRespository orderTransactionRespository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    Token token;
    @Autowired
    ICustomerRepository customerRepository;
    @Autowired
    IOrderDetailAddOnRepository orderDetailAddOnRepository;
    @Autowired
    TransactionService transactionService;

    public String savePaymentVnp(String info, String amount, String code) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OrderTransaction orderTransaction = orderTransactionRespository.getOrderTransactionById(Integer.parseInt(info));
        PaymentDto paymentDto = mapper.readValue(orderTransaction.getValue(), PaymentDto.class);
        orderTransactionRespository.deleteOrderTransactionById(orderTransaction.getId());
        if (!code.equals("00")) {
            return null;
        }
        String url = savePaymentGeneral(paymentDto, 0, 0, 2, 1); // Không cần amount ở đây
        orderTransactionRespository.deleteOrderTransactionById(orderTransaction.getId());
        return url;
    }

    public String savePaymentCod(PaymentDto paymentDto) {
        int id = token.getIdfromToken();
        Customer customer = customerRepository.findById(id);
        paymentDto.setCustomer(customer);

        return savePaymentGeneral(paymentDto, 0, 0, 1, 0); // Gọi hàm chung với các tham số mặc định
    }

    public String savePaymentGeneral(PaymentDto paymentDto, int amount, int status, int payment, int pay) {
        List<OrderDto> orders = paymentDto.getOrders();
        Address address = paymentDto.getAddress();
        Customer customer = paymentDto.getCustomer();

        for (OrderDto order : orders) {
            Order createOrder = new Order();
            createOrder.setShop(order.getShop());
            createOrder.setCustomer(customer);
            createOrder.setAddress(address);
            createOrder.setShipCost(order.getShipCost());
            createOrder.setPayment_status(pay);
            createOrder.setPayment(payment);
            createOrder.setOrder_status(status);

            // Tính subTotal chỉ từ orderList (giá trị hàng hóa của shop)
            int subTotal = order.getOrderList().stream()
                    .mapToInt(item ->
                            item.getProduct().getPrice() * item.getQuantity() +
                                    item.getProductAddOns().stream().mapToInt(ProductAddOn::getPrice).sum() * item.getQuantity()
                    )
                    .sum();
            createOrder.setOrderTotal(subTotal); // Chỉ lưu subTotal vào order_total
            createOrder.setDiscount(order.getSaleCost()); // Lưu saleCost (discountAmount) nếu có

            int check = orderRespository.saveOrder(createOrder);
            if (check != 0) {
                createOrder.setId(check);
                List<OrderList> orderList = order.getOrderList();
                for (OrderList productItem : orderList) {
                    OrderDetail detail = new OrderDetail();
                    detail.setProduct(productItem.getProduct());
                    detail.setQuantity(productItem.getQuantity());
                    detail.setProductOption(productItem.getProductOption());
                    detail.setPrice(productItem.getProduct().getPrice());
                    detail.setOrder(createOrder);
                    int insert = orderDetailResposiotry.saveOrderDetail(detail);
                    if (insert == 0) {
                        return null;
                    } else {
                        cartItemRepository.deleteCart(productItem.getCartId());
                        if (!productItem.getProductAddOns().isEmpty()) {
                            for (ProductAddOn productAddOn : productItem.getProductAddOns()) {
                                orderDetailAddOnRepository.addDetailAddOn(productAddOn, insert);
                            }
                        }
                    }
                }
            } else {
                return null;
            }
        }

        // Tính tổng giá trị hiển thị trên URL (bao gồm shipCost và trừ discount)
        int totalAmount = orders.stream()
                .mapToInt(order -> {
                    int subTotal = order.getOrderList().stream()
                            .mapToInt(item ->
                                    item.getProduct().getPrice() * item.getQuantity() +
                                            item.getProductAddOns().stream().mapToInt(ProductAddOn::getPrice).sum() * item.getQuantity()
                            )
                            .sum();
                    return subTotal + order.getShipCost() - order.getSaleCost();
                })
                .sum();

        if (pay != 0) {
            totalAmount = totalAmount / 100; // Điều chỉnh nếu cần (VNPay)
        }

        return "http://localhost:5173/payment-success"
                + "?amount=" + URLEncoder.encode(String.valueOf(totalAmount), StandardCharsets.UTF_8)
                + "&email=" + URLEncoder.encode(customer.getEmail(), StandardCharsets.UTF_8)
                + "&numberOrder=" + URLEncoder.encode(String.valueOf(orders.size()), StandardCharsets.UTF_8)
                + "&address=" + URLEncoder.encode(paymentDto.getAddress().getAddress(), StandardCharsets.UTF_8)
                + "&name=" + URLEncoder.encode(paymentDto.getAddress().getNameReceiver(), StandardCharsets.UTF_8)
                + "&phone=" + URLEncoder.encode(paymentDto.getAddress().getPhone(), StandardCharsets.UTF_8)
                + "&pay=" + pay;
    }
}