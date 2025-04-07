package com.example.demo.repository;

import com.example.demo.entity.Shipper;
import com.example.demo.mapper.ShipperMapper;
import com.example.demo.repository.repositoryInterface.IShipperAuth;
import org.springframework.stereotype.Repository;


@Repository
public class ShipperAuthRepository extends AbstractRepository<Shipper> implements IShipperAuth {
    @Override
    public Shipper shipperLogin(String phone) {
        String sql = "select * from shipper where phone = ?";
        Shipper c = super.findOne(sql, new ShipperMapper(), phone);
        return c;
    }

    @Override
    public int checkShipperPhone(String phone) {
        String sql = "select count(*) from shipper where phone = ?";
        int count = super.selectCount(sql, phone);
        return count;
    }
}
