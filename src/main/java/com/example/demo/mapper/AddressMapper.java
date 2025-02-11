package com.example.demo.mapper;

import com.example.demo.entity.Address;

import java.sql.ResultSet;

public class AddressMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs) {
        Address address = new Address();
        try {
            address.setId(rs.getInt("customer_address_id"));
            address.setAddress(rs.getString("address"));
            address.setName(rs.getString("customer_address_name"));
            address.setProvince(rs.getString("province"));
            address.setDistrict(rs.getString("district"));
            address.setWard(rs.getString("ward"));
            address.setPhone(rs.getString("phone_receiver"));
            address.setNameReceiver(rs.getString("name_receiver"));
            address.setIsDefault(rs.getInt("is_default"));
            address.setCreate_at(rs.getString("create_at"));
            address.setUpdate_at(rs.getString("update_at"));


        }catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
