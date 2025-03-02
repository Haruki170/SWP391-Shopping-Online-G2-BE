package com.example.socket.respository;

import com.example.socket.entity.Customer;
import com.example.socket.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class CustomerRepository  extends AbstractRepository<Customer> {

    public Customer findByEmail(String email) {
        String sql = "select * from customer where customer_email = ?";
        Customer customer = super.findOne(sql, new CustomerMapper(), email);
        return customer;
    }

    public Customer findById(int id) {
        String sql = "select * from customer where customer_id = ?";
        Customer customer = super.findOne(sql, new CustomerMapper(), id);
        return customer;
    }
}
