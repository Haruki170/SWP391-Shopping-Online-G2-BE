package com.example.demo.service.ServiceInterface;

import com.example.demo.dto.IdentifyDto;
import com.example.demo.entity.ShopOwner;
import com.example.demo.exception.AppException;

import java.util.List;

public interface IShopOwnerService {

    public ShopOwner getShopOwnerById(int id) throws AppException;
    public List<ShopOwner> getAllShopOwners();
    public boolean addShopOwner(int id, ShopOwner shopOwner) throws AppException;
    public boolean deleteShopOwner(int id) throws AppException;
    public int updateStatus(int id, int status) throws AppException;
    public boolean updateIndentify(IdentifyDto identifyDto, String frontImage,String backImage, String province, String district, String ward) throws AppException;
    public boolean forgotPassword(String email) throws AppException;
    public boolean resetPassword(String code, String password) throws AppException;
    public boolean checkCode(String code) throws AppException;
}
