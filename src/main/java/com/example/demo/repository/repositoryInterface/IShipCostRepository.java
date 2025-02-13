package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ShipCost;

import java.util.List;

public interface IShipCostRepository {
    public boolean save(ShipCost shipCost,int shopId);
    public List<ShipCost> findByShopId(int id);
    public boolean update(ShipCost shipCost);
    public boolean delete(int id);
}
