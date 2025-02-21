package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ProductAddOn;

import java.util.List;

public interface IOrderDetailAddOnRepository {
    public boolean addDetailAddOn(ProductAddOn productAddOn, int order_detail_id);
    public List<ProductAddOn> getDetailAddOns(int order_detail_id);
}
