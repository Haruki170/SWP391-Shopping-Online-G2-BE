package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Shipper;
import java.util.List;

public interface IShipperRepository {
    List<Shipper> findAllShippers();
    Shipper findShipperById(int id);
    boolean insert(Shipper shipper);
    boolean update(Shipper shipper);
    boolean delete(int id);
    List<Shipper> searchShipperByEmail(String email);
    List<Shipper> filterShippersByStatus(int status);
}
