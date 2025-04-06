package com.example.demo.repository;

import com.example.demo.entity.Shipper;
import com.example.demo.mapper.ShipperMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipperRepository extends AbstractRepository<Shipper> {

    public List<Shipper> findAllShippers() {
        String sql = "SELECT * FROM shipper";
        return super.findAll(sql, new ShipperMapper());
    }

    public Shipper findShipperById(int id) {
        String sql = "SELECT * FROM shipper WHERE shipper_id = ?";
        return super.findOne(sql, new ShipperMapper(), id);
    }

    public boolean insert(Shipper shipper) {
        String sql = "INSERT INTO shipper (shipper_name, shipper_email, shipper_phone, shipper_address, shipper_status) VALUES (?, ?, ?, ?, ?)";
        return super.save(sql, shipper.getName(), shipper.getEmail(), shipper.getPhone(), shipper.getAddress(), shipper.getStatus());
    }

    public boolean update(Shipper shipper) {
        String sql = "UPDATE shipper SET shipper_name = ?, shipper_email = ?, shipper_phone = ?, shipper_address = ?, shipper_status = ? WHERE shipper_id = ?";
        return super.save(sql, shipper.getName(), shipper.getEmail(), shipper.getPhone(), shipper.getAddress(), shipper.getStatus(), shipper.getId());
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM shipper WHERE shipper_id = ?";
        return super.save(sql, id);
    }
    
}
