package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.Cart_item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.OrderDetailRespository;
import com.example.demo.repository.repositoryInterface.IOrderRespository;
import com.example.demo.repository.repositoryInterface.IShopRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.ApiResponseWithPage;
import com.example.demo.service.OrderService;
import com.example.demo.service.ServiceInterface.IShopService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    IShopService shopService;
    @Autowired
    private Token token;
    @Autowired
    OrderDetailRespository orderDetailRespository;

    @PostMapping("/get-order")
    public ResponseEntity getOrder(@RequestBody List<Cart_item> cartItemList) {
        List<OrderDto> list = orderService.getOrder(cartItemList);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get-history")
    public ResponseEntity getOrderHistory(@RequestParam String orderStatus) {
        int id = token.getIdfromToken();
        List<Order> list = orderService.getOrderHistory(id, orderStatus);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/cancel-order")
    public ResponseEntity cancelOrder(@RequestParam int orderId) throws AppException {
        orderService.updateOrder(orderId, 4 ,1);
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/customer-handle-cancel")
    public ResponseEntity cumstomerHandleCancel(@RequestParam int orderId) throws AppException {
        orderService.customerHandleOrder(orderId);
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/get-all")
    public ResponseEntity<ApiResponseWithPage<Order>> getAllOrder(
            @RequestParam int page,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String date) throws AppException {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);

        List<Integer> statuses = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            statuses = Arrays.stream(status.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        List<Order> list = orderService.getAllOrdersByShop(shopId, page, startDate, endDate,statuses);
        int totalProduct = orderService.countOrder(shopId, startDate, endDate,statuses);
        int totalPage = (int) Math.ceil((double) totalProduct / 4);
        ApiResponseWithPage apiResponse = new ApiResponseWithPage<>(200, "success", list, totalPage);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllOrderByShop() throws AppException {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);


        List<Order> list = orderService.getAllOrdersByShop(shopId);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/getQuantity")
    public ResponseEntity<ApiResponse> getQuantity() throws AppException {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);
        int  quantity = orderService.getQuantity(shopId);
        ApiResponse apiResponse = new ApiResponse(200, "success", quantity);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<ApiResponse<Order>> getOneOrder(@PathVariable int id) throws AppException {
        System.out.println(id);
        Order order = orderService.getOrderById(id);
        ApiResponse<Order> apiResponse = new ApiResponse(200, "success", order);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update-status")
    public ResponseEntity<ApiResponse> updateStatus(@RequestParam int orderId, @RequestParam int status, @RequestParam int type) throws AppException {
        orderService.updateOrder(orderId, status, type);
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get-success-detail")
    public ResponseEntity<ApiResponse> getProductSuccess(){
        int id =token.getIdfromToken();
        List<OrderDetail> list = orderDetailRespository.getProductOrderSuccess(id);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/get-order-feedback/{id}")
    public ResponseEntity getOrderFeedback(@PathVariable int id) throws AppException {
        OrderDetail list =orderDetailRespository.getOrderProductDetail(id);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);

    }




}
