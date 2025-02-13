package com.example.demo.repository.repositoryInterface;

import com.example.demo.dto.IdentifyDto;
import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopOwner;

import java.util.List;

public interface IShopOwnerRepository {
    public List<ShopOwner> findAll();
    public ShopOwner findById(int id);
    public boolean insert(ShopOwner shopOwner);
    public boolean delete(ShopOwner shopOwner);
    public ShopOwner findByEmail(String email);
    public int checkExist(String email);
    public int updateStatus(int id, int status);
    public boolean updateIdentify(IdentifyDto identifyDto, String imageFront, String imageBack,String province, String district, String ward);
    public int updatePassword(int id, String password);
    public boolean updatePasswordShopOwner(String password,int id);
    public boolean forgotPasswordShopOwnerCode(String code, String email);
    public boolean deletePasswordShopOwnerCode(String email);
    public ShopOwner resetPasswordShopOwnerByCode(String code);
    public int checkCodeShopOwner(String code);

}
