package com.example.demo.service.ServiceInterface;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.Cart_item;
import com.example.demo.entity.Order;
import com.example.demo.exception.AppException;
import com.example.demo.response.GetOrderResponse;

import java.util.List;

public interface IOrderService {
    public List<OrderDto> getOrder(List<Cart_item> cartItems);
    public List<Order> getAllOrdersByShop(int shop_id, int page,String startDate, String endDate,List<Integer> statuses);

    List<Order> getAllOrdersByShop(int shop_id);

    int getQuantity(int shop_id);

    public Order getOrderById(int order_id) throws AppException;
    public boolean updateOrder(int orderId, int status, int type) throws AppException;
    public int countOrder(int shop_id,String startDate, String endDate,List<Integer> statuses) throws AppException;
}
