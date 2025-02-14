package com.example.demo.service.ServiceInterface;

import com.example.demo.dto.ShopDto;
import com.example.demo.entity.ShipCost;
import com.example.demo.entity.Shop;
import com.example.demo.entity.ShopAddress;
import com.example.demo.entity.ShopPhone;
import com.example.demo.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IShopService {
    public boolean addShop(Shop shop, MultipartFile file, int shopownerId) throws IOException, AppException;
    public Shop getShopByOwnerId(int id) throws AppException;
    public int getIdByOwnerId(int id) throws AppException;
    public Shop getShopDetails(int id) throws AppException;
    public boolean updateShop(Shop shop, MultipartFile file) throws IOException, AppException;
    public boolean updateShopAddress(List<ShopAddress> shopAddress, int shopid) throws AppException;
    public boolean updateShippingCost(Shop shop, int shopId) throws AppException;
    public boolean updateShopPhones(List<ShopPhone> shopPhones, int shopid) throws AppException;
    public List<ShopDto> getAllShops() throws AppException;
    public Shop getShopByAdmin(int id) throws AppException;
    public Shop getShopById(int id);
}
