package com.example.demo.service;


import com.example.demo.entity.Shipper;
import com.example.demo.repository.ShipperRepository;
import com.example.demo.service.ServiceInterface.IShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipperService implements IShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    @Override
    public List<Shipper> getAllShippers() {
        return shipperRepository.findAllShippers();
    }

    @Override
    public Shipper getShipperById(int id) {
        return shipperRepository.findShipperById(id);
    }

    @Override
    public boolean addShipper(Shipper shipper) {
        return shipperRepository.insert(shipper);
    }

    @Override
    public boolean updateShipper(Shipper shipper) {
        return shipperRepository.update(shipper);
    }

    @Override
    public boolean removeShipper(int id) {
        return shipperRepository.delete(id);
    }

    @Override
    public List<Shipper> searchShippersByEmail(String email) {
        return shipperRepository.searchShipperByEmail(email);
    }

    @Override
    public List<Shipper> filterShippersByStatus(int status) {
        return shipperRepository.filterShippersByStatus(status);
    }
}
