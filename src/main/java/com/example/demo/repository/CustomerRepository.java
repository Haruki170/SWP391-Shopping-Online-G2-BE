package com.example.demo.repository;

import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopRegister;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.repository.repositoryInterface.ICustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CustomerRepository extends AbstractRepository<Customer> implements ICustomerRepository {
    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        List<Customer> list = super.findAll(sql, new CustomerMapper());
        return list;
    }





    @Override
    public boolean insert(Customer customer) {
        String sql = "insert into customer(customer_email, customer_name, customer_password, status) values(?,?,?,0)";
        super.save(sql,customer.getEmail(),customer.getName(),customer.getPassword());
        return true;
    }

    @Override
    public boolean update(Customer customer) {
        String sql = "";
        if(customer.getPhoneNumber() == null){
            sql = "update customer set customer_name=? where customer_id = ?";
            boolean check = super.save(sql, customer.getName(), customer.getId());
            return check;
        }
        else{
            sql = "update customer set customer_name=?, phone = ? where customer_id = ?";
            boolean check = super.save(sql, customer.getName(), customer.getPhoneNumber(),customer.getId());
            return check;
        }


    }

    @Override
    public boolean delete(Customer customer) {
        return  false;
    }

    @Override
    public Customer findByEmail(String email) {
        String sql = "select * from customer where customer_email = ?";
        Customer customer = super.findOne(sql, new CustomerMapper(), email);
        return customer;
    }

    @Override
    public Customer findById(int id) {
        String sql = "select * from customer where customer_id = ?";
        Customer customer = super.findOne(sql, new CustomerMapper(), id);
        return customer;
    }

    @Override
    public int checkExist(String email) {
        String sql = "select count(*) from customer where customer_email = ?";
        int count = super.selectCount(sql, new CustomerMapper(), email);
        return count;
    }

    @Override
    public boolean uploadAvatar(String avatar, int id) {
        String sql = "update customer set customer_avatar = ? where customer_id = ?";
         boolean check = super.save(sql, avatar, id);
        return check;
    }

    @Override
    public boolean updatePassword(String password, int id) {
        String sql = "update customer set customer_password = ? where customer_id = ?";
        boolean check = super.save(sql, password, id);
        return check;
    }

    @Override
    public int updateStatus(int id, int status) {
        String sql = "update customer set status = ? where customer_id = ?";
        super.save(sql, status, id);
        return 1;
    }

    @Override
    public boolean updateCusManager(Customer customer) {
        String sql = "update customer set customer_name=?, customer_email=?, customer_password=? where customer_id = ?";
        return super.save(sql, customer.getName(), customer.getEmail(), customer.getPassword(), customer.getId());
    }

    @Override
    public boolean forgotPasswordCode(String code, String email) {
        String sql = "update customer set forgot_password_code = ? where customer_email = ?";
        boolean check = super.save(sql, code, email);
        return check;
    }

    @Override
    public boolean deletePasswordCode(String email) {
        String sql = "update customer set forgot_password_code = null where customer_email = ?";
        return super.save(sql, email);
    }

    @Override
    public Customer resetPasswordByCode(String code) {
        String sql = "select * from customer where forgot_password_code = ?";
        Customer customer = super.findOne(sql, new CustomerMapper(), code);
        return customer;
    }

    @Override
    public int checkCode(String code) {
        String sql = "SELECT count(*) FROM `customer` WHERE forgot_password_code = ?";
        return super.selectCount(sql, code);
    }


}
