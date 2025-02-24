package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.OrderTransaction;

public interface IOrderTransactionRespository {
    public int save(OrderTransaction orderTransaction);
    public OrderTransaction getOrderTransactionById(int id);
    public boolean deleteOrderTransactionById(int id);
}
