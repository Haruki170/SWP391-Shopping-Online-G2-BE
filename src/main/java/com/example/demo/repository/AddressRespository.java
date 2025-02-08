package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.repository.repositoryInterface.IAddressRespository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressRespository extends AbstractRepository<Address> implements IAddressRespository {
    @Override
    public boolean saveAddress(int userId, Address address) {
        String sql = "INSERT INTO customer_address (customer_id, customer_address_name," +
                " province, district, ward, address, phone_receiver, name_receiver, is_default) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        boolean result = super.save(sql, userId, address.getName(),
                                    address.getProvince(),address.getDistrict(),address.getWard(),address.getAddress()
                                    ,address.getPhone(), address.getNameReceiver(), address.getIsDefault());
       return result;
    }

    @Override
    public Address getAddress(int id) {
        String sql = "select * from address where customer_address_id = ?";
        Address address = super.findOne(sql, new AddressMapper(), id);
        return address;
    }

    @Override
    public boolean deleteAddress(int id) {
        String sql = "delete from customer_address where customer_address_id = ?";
        boolean result = super.delete(sql, id);
        return result;
    }

    @Override
    public boolean updateAddress(int id, Address address) {
        String sql = "UPDATE `customer_address` SET" +
                "`customer_address_name`=?,`province`=?," +
                "`district`=?,`ward`=?,`address`=?,`phone_receiver`=?" +
                ",`name_receiver`=?,`is_default`=? WHERE customer_address_id = ?";
        boolean result = super.save(sql, address.getName(), address.getProvince(),
                address.getDistrict(),address.getWard(), address.getAddress(), address.getPhone(),
                address.getNameReceiver(), address.getIsDefault(), address.getId());
        return result;
    }

    @Override
    public List<Address> getAddressByUser(int userId) {
        String sql = "select * from customer_address where customer_id=?";
        List<Address> list = super.findAll(sql, new AddressMapper(), userId);
        return list;
    }

    @Override
    public int checkAddressExist(int id, int userId) {
        String sql = "SELECT count(*) FROM `customer_address` WHERE customer_id = ? and customer_address_id = ?";
        int count = super.selectCount(sql, userId, id);
        return count;
    }

    @Override
    public boolean resetDefaultAddress(int userId) {
        String sql = "update customer_address set is_default = 0 where customer_id = ?";
        boolean check = super.save(sql, userId);
        return check;
    }
}
