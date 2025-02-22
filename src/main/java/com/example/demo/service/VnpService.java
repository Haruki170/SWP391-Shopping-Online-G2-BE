package com.example.demo.service;

import com.example.demo.config.VnpConfig;
import com.example.demo.dto.OrderDto;
import com.example.demo.dto.PaymentDto;
import com.example.demo.entity.Customer;
import com.example.demo.entity.OrderTransaction;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.repository.repositoryInterface.ICustomerRepository;
import com.example.demo.repository.repositoryInterface.IOrderTransactionRespository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VnpService {
    @Autowired
    ICustomerRepository customerRepository;
    @Autowired
    IOrderTransactionRespository orderTransactionRespository;
    @Autowired
    Token token;
    public String getUrlVnPay(PaymentDto payment) throws Exception {
        if(payment.getOrders() == null || payment.getOrders().isEmpty()) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        else{
            int total = 0;
            int shipCost = 0;
            for(OrderDto order : payment.getOrders()) {
               total += order.getShipCost() + order.getTotalCost();
               shipCost += order.getShipCost();
            }
            int id = token.getIdfromToken();
            Customer customer = customerRepository.findById(id);
            payment.setCustomer(customer);
            ObjectMapper ob = new ObjectMapper();
            String paymentJson = ob.writeValueAsString(payment);
            int idOrder = orderTransactionRespository.save(new OrderTransaction(id, paymentJson));
            if(idOrder == 0){
                throw new AppException(ErrorCode.SERVER_ERR);
            }
            else{
                return createPayment(total, idOrder);
            }

        }
    }
  private String createPayment(int totalamount,int id) throws Exception {
       String vnp_Version = "2.1.0";
       String vnp_Command = "pay";
       String orderType = "other";
       long amount = totalamount*100;
       String bankCode = "";

       String vnp_TxnRef = id+"";
       String vnp_IpAddr = "127.0.0.1";

       String vnp_TmnCode = VnpConfig.vnp_TmnCode;

       Map<String, String> vnp_Params = new HashMap<>();
       vnp_Params.put("vnp_Version", vnp_Version);
       vnp_Params.put("vnp_Command", vnp_Command);
       vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
       vnp_Params.put("vnp_Amount", String.valueOf(amount));
       vnp_Params.put("vnp_CurrCode", "VND");

       if (bankCode != null && !bankCode.isEmpty()) {
           vnp_Params.put("vnp_BankCode", bankCode);
       }
       vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
       vnp_Params.put("vnp_OrderInfo", "Order_id:" + id);
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

       List fieldNames = new ArrayList(vnp_Params.keySet());
       Collections.sort(fieldNames);
       StringBuilder hashData = new StringBuilder();
       StringBuilder query = new StringBuilder();
       Iterator itr = fieldNames.iterator();
       while (itr.hasNext()) {
           String fieldName = (String) itr.next();
           String fieldValue = vnp_Params.get(fieldName);
           if ((fieldValue != null) && (fieldValue.length() > 0)) {
               //Build hash data
               hashData.append(fieldName);
               hashData.append('=');
               hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
               //Build query
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
