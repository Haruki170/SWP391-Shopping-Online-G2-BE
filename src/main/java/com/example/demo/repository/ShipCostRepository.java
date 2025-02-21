package com.example.demo.repository;

import com.example.demo.entity.ShipCost;
import com.example.demo.mapper.ShipCostMapper;
import com.example.demo.repository.repositoryInterface.IShipCostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipCostRepository extends AbstractRepository<ShipCost> implements IShipCostRepository {
    @Override
    public boolean save(ShipCost shipCost, int shopId) {
        String sql = "INSERT INTO `shipping_cost`(`start_calculated_weight`, `cost`, `end_calculated_weight`, `shop_id`) " +
                "VALUES (?,?,?,?)";
        super.save(sql,shipCost.getStartWeight(), shipCost.getCost(), shipCost.getEndWeight(), shopId);
        return true;
    }

    @Override
    public List<ShipCost> findByShopId(int id) {
        String sql = "SELECT * FROM `shipping_cost` WHERE `shop_id` = ?";
        return super.findAll(sql, new ShipCostMapper(), id);
    }

    @Override
    public boolean update(ShipCost shipCost) {
        String sql = "UPDATE `shipping_cost` SET `start_calculated_weight`=?,`cost`=?," +
                "`end_calculated_weight`=? WHERE  shipping_cost_id = ?";
        return super.save(sql, shipCost.getStartWeight(), shipCost.getCost(), shipCost.getEndWeight(), shipCost.getId());
    }
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM `shipping_cost` WHERE  shipping_cost_id = ?";
        return super.delete(sql, id);
    }

}
