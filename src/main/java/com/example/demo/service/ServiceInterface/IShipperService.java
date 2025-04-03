package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Shipper;
import java.util.List;

public interface IShipperService {
    List<Shipper> getAllShippers();
    Shipper getShipperById(int id);
    boolean addShipper(Shipper shipper);
    boolean updateShipper(Shipper shipper);
    boolean removeShipper(int id);
    List<Shipper> searchShippersByEmail(String email);
    List<Shipper> filterShippersByStatus(int status);
}