package com.example.demo.service;

import com.example.demo.entity.Shipper;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    List<Shipper> getAllShippers();
    Optional<Shipper> getShipperById(Long id);
    Shipper createShipper(Shipper shipper);
    Shipper updateShipper(Long id, Shipper shipper);
    void deleteShipper(Long id);
}
package com.example.demo.service.impl;

import com.example.demo.entity.Shipper;
import com.example.demo.repository.ShipperRepository;
import com.example.demo.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;

    @Override
    public List<Shipper> getAllShippers() {
        return shipperRepository.findAll();
    }

    @Override
    public Optional<Shipper> getShipperById(Long id) {
        return shipperRepository.findById(id);
    }

    @Override
    public Shipper createShipper(Shipper shipper) {
        return shipperRepository.save(shipper);
    }

    @Override
    public Shipper updateShipper(Long id, Shipper shipper) {
        return shipperRepository.findById(id)
                .map(existing -> {
                    existing.setName(shipper.getName());
                    existing.setPhoneNumber(shipper.getPhoneNumber());
                    existing.setVehicleNumber(shipper.getVehicleNumber());
                    existing.setAvailable(shipper.getAvailable());
                    return shipperRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Shipper not found with id: " + id));
    }

    @Override
    public void deleteShipper(Long id) {
        shipperRepository.deleteById(id);
    }
}
