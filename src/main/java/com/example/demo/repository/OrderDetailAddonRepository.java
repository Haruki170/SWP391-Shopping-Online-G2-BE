package com.example.demo.repository;

import com.example.demo.entity.ProductAddOn;
import com.example.demo.mapper.ProductAddonMapper;
import com.example.demo.repository.repositoryInterface.IOrderDetailAddOnRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailAddonRepository  extends AbstractRepository<ProductAddOn> implements IOrderDetailAddOnRepository {
    @Override
    public boolean addDetailAddOn(ProductAddOn productAddOn, int order_detail_id) {
        String sql = "INSERT INTO order_detail_addon " +
                "(product_addon_price, order_detail_id, product_addon_name) VALUES (?, ?, ?)";
        return super.save(sql, productAddOn.getPrice(), order_detail_id, productAddOn.getName());
    }

    @Override
    public List<ProductAddOn> getDetailAddOns(int order_detail_id) {
        System.out.println(order_detail_id);
        String sql = "SELECT * FROM `swp_v5`.`order_detail_addon` where `order_detail_id` = ?";
        return super.findAll(sql, new ProductAddonMapper(), order_detail_id);
    }
}
