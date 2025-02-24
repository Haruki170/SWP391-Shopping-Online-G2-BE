package com.example.demo.repository.repositoryInterface;


import com.example.demo.entity.Order;

import java.util.List;

public interface IOrderRespository {
    public int saveOrder(Order order);
    public Order getOrderByID(int id);
    public List<Order> getAllOrdersByShop(int shopid, int offset,String startDate, String endDate,List<Integer> statuses);

    List<Order> getAllOrdersByShop(int shopId);

    int  getQuantity(int shopId);

    public boolean deleteOrder(int id);
    public boolean updateOrderStatus(int orderId, int status);
    public boolean updateOrderPayment(int orderId, int status);
    public int countOrdersByShop(int shopid, String startDate, String endDate,List<Integer> statuses);
    public Order getOrderByCode(String code);
    public List<Order> GetHistoryOrder(int customerId,int status);
}
