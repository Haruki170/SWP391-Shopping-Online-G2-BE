package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.*;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.AdminTransactionRepository;
import com.example.demo.repository.OrderRespository;
import com.example.demo.repository.ShopRespository;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.repository.repositoryInterface.IOrderDetailAddOnRepository;
import com.example.demo.response.OrderList;
import com.example.demo.service.ServiceInterface.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService implements IOrderService {
    @Autowired
    ShopRespository shopRespository;
    @Autowired
    private OrderRespository orderRespository;
    @Autowired
    IOrderDetailAddOnRepository orderDetailAddOnRepository;
    @Autowired
    AdminTransactionRepository adminTransactionRepository;
    @Autowired
    VoucherRepository voucherRepository;
    @Override
    public List<OrderDto> getOrder(List<Cart_item> cartItems) {
        // Bước 1: Lấy danh sách shop_id từ CartItem và truyền vào repository để lấy danh sách Shop
        String ids = cartItems.stream()
                .map(cartItem -> String.valueOf(cartItem.getProduct().getShop().getId())) // Chuyển id thành chuỗi
                .collect(Collectors.joining(","));
        // Shop repository để lấy danh sách Shop'System.out.println(ids);
        List<Shop> shops = shopRespository.findShopByCartItem(ids);

        // Bước 2: Tạo danh sách OrderDTO
        List<OrderDto> getOrderResponses = new ArrayList<>();

        // Bước 3: Duyệt qua từng Shop và nhóm các sản phẩm theo từng Shop
        for (Shop shop : shops) {
            OrderDto getOrderResponse = new OrderDto();

            getOrderResponse.setShop(shop);
            // Nhóm các CartItem theo Shop hiện tại
            List<OrderList> orderListsForShop = cartItems.stream()
                    .filter(cartItem -> cartItem.getProduct().getShop().getId() == shop.getId()) // Lọc các CartItem thuộc về Shop hiện tại
                    .map(cartItem -> {
                        // Tạo OrderList từ CartItem
                        OrderList orderList = new OrderList();
                        orderList.setCartId(cartItem.getId());
                        orderList.setProduct(cartItem.getProduct());
                        orderList.setProductOption(cartItem.getOption());
                        orderList.setQuantity(cartItem.getQuantity());
                        orderList.setAddOns(cartItem.getAddOns());
                        orderList.setProductAddOns(cartItem.getProductAddOns().size() == 0 ? new ArrayList<>() : cartItem.getProductAddOns());
                        return orderList;
                    })
                    .collect(Collectors.toList());

            // Bước 4: Đặt danh sách OrderList vào OrderDTO
            getOrderResponse.setOrderList(orderListsForShop);
            //getOrderResponse.setVoucherList(voucherRepository.findValidByShopId(shop.getId()));
            // Bước 5: Tính phí vận chuyển (giả định bạn có logic để tính shipCost)
            int shipcost = calculateShipCostForShop(orderListsForShop, shop);
            int totalCost = calulateTotalCost(orderListsForShop);
            getOrderResponse.setShipCost(shipcost);
            getOrderResponse.setTotalCost(totalCost);
            if(shop.getAutoShipCost() == 0){
                getOrderResponse.setCostStatus(0);
            }
            else{
               if(shop.getAutoShipCost() == 1){
                   getOrderResponse.setCostStatus(1);
               }
               else{
                   getOrderResponse.setCostStatus(2);
               }
            }

            // Thêm OrderDTO vào danh sách kết quả
            getOrderResponses.add(getOrderResponse);
        }

        // Bước 6: Trả về danh sách OrderDTO
        return getOrderResponses;
    }

    @Override
    public List<Order> getAllOrdersByShop(int shop_id, int page,String startDate, String endDate,List<Integer> statuses) {
        int offset = (page - 1) * 5;
        List<Order> list = orderRespository.getAllOrdersByShop(shop_id,offset, startDate, endDate, statuses);
        for (Order order : list) {
            List<OrderDetail> orderDetails  = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setProductAddOns(orderDetailAddOnRepository.getDetailAddOns(orderDetail.getId()));
            }
        }
        return list;
    }
    @Override
    public List<Order> getAllOrdersByShop(int shop_id) {
        return orderRespository.getAllOrdersByShop(shop_id);
    }
    @Override
    public int getQuantity(int shop_id) {
        return orderRespository.getQuantity(shop_id);
    }

    @Override
    public Order getOrderById(int order_id) throws AppException {
        Order order= orderRespository.getOrderByID(order_id);
        if(order == null){
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        List<OrderDetail> orderDetails  = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setProductAddOns(orderDetailAddOnRepository.getDetailAddOns(orderDetail.getId()));
        }
        return order;
    }

    @Override
    public boolean updateOrder(int orderId, int status, int type) throws AppException {
        System.out.println(orderId);
        if(type == 1){
            orderRespository.updateOrderStatus(orderId, status);
        }
        else{
            orderRespository.updateOrderPayment(orderId, status);
        }
        return true;
    }

    public boolean customerHandleOrder(int id) throws AppException {
        Order order = orderRespository.getOrderByID(id);
        Shop shop = shopRespository.getShopByOrderId(order.getId());
        if (order == null){
            throw new AppException(ErrorCode.ORDER_Notfound);
        }
        else{
            if(order.getPayment_status() == 0){
                return updateOrder(id,4,1);
            }
            else{
                TransactionAdmin transactionAdmin = new TransactionAdmin();
                transactionAdmin.setShop(shop);
                transactionAdmin.setAmount(order.getOrderTotal());
                transactionAdmin.setDescription("Hoàn tiền cho khách hàng "+ order.getCustomer().getEmail()+" Vì lý do hủy đơn");
                transactionAdmin.setType(0);
                transactionAdmin.setIsPaid(4);
                transactionAdmin.setOrder(order);
                adminTransactionRepository.saveTransaction(transactionAdmin);
                System.out.println("cccc");
                return updateOrder(id,4,1);
            }
        }
    }




    @Override
    public int countOrder(int shop_id, String startDate, String endDate, List<Integer> statuses) throws AppException {
        return orderRespository.countOrdersByShop(shop_id,startDate, endDate, statuses);
    }

    @Override
    public void updateShipCost(int orderId, int shipCost) {
        orderRespository.updateOrderShipping(orderId, shipCost);
    }

    // phương thức tính phí vận chuyển cho mỗi Shop
    private int calculateShipCostForShop(List<OrderList> orderList, Shop shop) {
        //   tính toán dựa trên khối lượng,,
        if(shop.getAutoShipCost() == 0){
            return 0;
        }
       else{
            double totalWeight = orderList.stream()
                    .mapToDouble(order -> {
                        Product p = order.getProduct();
                        // Công thức tính khối lượng dựa trên kích thước sản phẩm
                        return (p.getHeight() * p.getLength() * p.getWidth()) / 6000.0 * order.getQuantity();
                    })
                    .sum();
            int cost = shopRespository.getShipCost(totalWeight, shop.getId());
            return cost;
        }
    }


    private int calulateTotalCost(List<OrderList> orderList){
        int total = 0;

        for(OrderList order : orderList){
            if(order.getProductAddOns().size() != 0){
                for(ProductAddOn addOn : order.getProductAddOns()){
                    total += addOn.getPrice() * order.getQuantity();

                }
            }
            Product p = order.getProduct();
            total+= p.getPrice() * order.getQuantity();
        }
        return total;
    }

    public List<Order> getOrderHistory(int customerId, String status){
        List<Order> list= new ArrayList<>();
        if(status.equals("all")){
            list = orderRespository.GetHistoryOrder(customerId,-1);
        }
        else if(status.equals("paid")){
            list = orderRespository.GetHistoryOrder(customerId,1);
        }
        else if(status.equals("pending")){
            list = orderRespository.GetHistoryOrder(customerId,2);
        }
        else if(status.equals("shipped")){
            list = orderRespository.GetHistoryOrder(customerId,3);
        }else{
            list = orderRespository.GetHistoryOrder(customerId,4);
        }
        return list;
    }
}
