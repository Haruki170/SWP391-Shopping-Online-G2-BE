package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Address;
import com.example.demo.exception.AppException;

import java.util.List;

public interface IAddressService {
    public boolean addAddress(int userId, Address address) throws AppException;
    public boolean deleteAddress(int userId, int addressId) throws AppException;
    public boolean updateAddress(int userId, Address address) throws AppException;
    public Address getAddress(int addressId, int userId) throws AppException;
    public List<Address> getAllAddressByUserId(int userId) throws AppException;

}
