package com.example.demo.controller;

import com.example.demo.dto.OrderCancelDto;
import com.example.demo.entity.OrderCancel;
import com.example.demo.entity.shop_report;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.jwt.Token;
import com.example.demo.repository.OrderCancelRepository;
import com.example.demo.repository.OrderRespository;
import com.example.demo.repository.ShopOwnerReportRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.OrderService;
import com.example.demo.service.ShopService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/order-cancel")
public class OrderCancelController {
    @Autowired
    private OrderCancelRepository orderCancelRepository;
    @Autowired
    FileUpload fileUpload;
    @Autowired
    Token token;
    @Autowired
    ShopService shopService;
    @Autowired
    OrderService orderService;
    @Autowired
    private OrderRespository orderRespository;
    @Autowired
    ShopOwnerReportRespository shopOwnerReportRespository;

    @PostMapping("/save")
    public ResponseEntity save(@RequestParam("reason") String reason,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images) throws AppException {

        ObjectMapper o = new ObjectMapper();
        int sid = token.getIdfromToken();
        int shopid = shopService.getIdByOwnerId(sid);
        try {
            OrderCancelDto c = o.readValue(reason, OrderCancelDto.class);
            c.setShop_id(shopid);
            int id = orderCancelRepository.insert(c);
            if(id == 0){
                throw new AppException(ErrorCode.SERVER_ERR);
            } else {
                // Kiểm tra `images` có tồn tại và không rỗng
                if (images != null && !images.isEmpty()) {
                    for (MultipartFile image : images) {
                        String path = fileUpload.uploadImage(image);
                        orderCancelRepository.insertImage(path, id);
                    }
                }
                ApiResponse apiResponse = new ApiResponse(200, "success", null);
                return ResponseEntity.ok(apiResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.SERVER_ERR); // Trả về lỗi phù hợp nếu có lỗi xảy ra
        }
    }
    @GetMapping("/get-request/{id}")
    public ResponseEntity getRequest(@PathVariable int id) {
        List<OrderCancel> list = orderCancelRepository.getRequestByShopId(id);
        for (OrderCancel orderCancel : list){
            orderCancel.setImages(orderCancelRepository.getImageByOrderCancelId(orderCancel.getId()));
        }
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/approve-cancel")
    public ResponseEntity approveCancelOrder(@RequestParam String code, @RequestParam int id) throws AppException {
        orderRespository.updateOrderStatusByCode(code, 4);
        orderCancelRepository.updateStatus(id, 1);
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity cancelOrder(@RequestBody shop_report report, @PathVariable int id) throws AppException {
        shopOwnerReportRespository.insertWithResponse(report, id);
        orderCancelRepository.updateStatus(report.getId(), 1);
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }


}
