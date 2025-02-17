package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Address;

import java.util.List;

public interface IAddressRespository {
    public boolean saveAddress(int userId,Address address);
    public Address getAddress(int id);
    public boolean deleteAddress(int id);
    public boolean updateAddress(int id,Address address);
    public List<Address> getAddressByUser(int userId);
    public int checkAddressExist(int id, int userId);
    public boolean resetDefaultAddress(int userId);
}
