package com.example.demo.service;

import com.example.demo.config.VnpConfig;
import com.example.demo.entity.Order;
import com.example.demo.entity.Shop;
import com.example.demo.entity.TransactionAdmin;
import com.example.demo.entity.TransactionShop;
import com.example.demo.repository.AdminTransactionRepository;
import com.example.demo.repository.OrderRespository;
import com.example.demo.repository.ShopRespository;
import com.example.demo.repository.ShopTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShopTransactionService {

    @Autowired
    AdminTransactionRepository adminTransactionRepository;
    @Autowired
    ShopTransactionRepository shopTransactionRepository;
    @Autowired
    ShopRespository shopRespository;
    @Autowired
    OrderRespository orderRespository;
    public boolean savePayForAdmin (TransactionShop transactionShop) {
        TransactionAdmin transactionAdmin = new TransactionAdmin();
        transactionAdmin.setShop(transactionShop.getShop());
        transactionAdmin.setAmount(transactionShop.getAmount());
        transactionAdmin.setDescription("Cửa hàng thanh toán chiết khấu đơn hàng "+ transactionShop.getOrder().getCode());
        transactionAdmin.setType(1);
        transactionAdmin.setIsPaid(2);
        transactionAdmin.setOrder(transactionShop.getOrder());


        TransactionShop transactionShop1 = new TransactionShop();
        transactionShop1.setShop(transactionShop.getShop());
        transactionShop1.setAmount(transactionShop.getAmount());
        transactionShop1.setDescription("Cửa hàng thanh toán chiết khấu đơn hàng "+ transactionShop.getOrder().getCode());
        transactionShop1.setType(0);
        transactionShop1.setOrder(transactionShop.getOrder());

        shopTransactionRepository.savePay(transactionShop1);
        adminTransactionRepository.savePayForShop(transactionAdmin);
        shopTransactionRepository.updatePay(transactionShop.getId());
        return true;
    }

    public String getPayforAdmin(TransactionShop transactionShop) throws Exception {
        String url = createPayment(transactionShop.getAmount(), transactionShop.getShop().getId(),
                transactionShop.getOrder().getId(), transactionShop.getId());
        return url;
    }

    public boolean handleTransactionPay(String info){
        String data[] = info.split(":");
        Shop shop = shopRespository.findShopbyID(Integer.parseInt(data[0]));
        Order order = orderRespository.getOrderByID(Integer.parseInt(data[1]));
        TransactionShop transactionShop = new TransactionShop();
        transactionShop.setShop(shop);
        transactionShop.setAmount(Integer.parseInt(data[2]));
        transactionShop.setId(Integer.parseInt(data[3]));
        transactionShop.setOrder(order);
        return savePayForAdmin(transactionShop);
    }

    private String createPayment(int amountTotal, int shopId, int orderId, int id) throws Exception {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = amountTotal * 100;
        String bankCode = "";

        // Sử dụng shopId, orderId, amountTotal, id và thời gian hiện tại để tạo vnp_TxnRef duy nhất
        String vnp_TxnRef = shopId + ":" + orderId + ":" + amountTotal + ":" + id + ":" + System.currentTimeMillis();
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VnpConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_OrderInfo", "pay");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef); // Mã giao dịch duy nhất
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", VnpConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpConfig.hmacSHA512(VnpConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpConfig.vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

}
