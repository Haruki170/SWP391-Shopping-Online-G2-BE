package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.repositoryInterface.IAddressRespository;
import com.example.demo.service.ServiceInterface.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdressService implements IAddressService {
    @Autowired
    IAddressRespository addressRepository;


    @Override
    public boolean addAddress(int userId, Address address) throws AppException {
        if (address.getName().isEmpty()) {
            throw new AppException(ErrorCode.ADDRESS_NAME);
        } else if (address.getProvince() == "" || address.getProvince() == null) {
            throw new AppException(ErrorCode.ADDRESS_PROVINCE_EMPTY);
        } else if (address.getDistrict() == "" || address.getDistrict() == null) {
            throw new AppException(ErrorCode.ADDRESS_DISTRICT_EMPTY);
        } else if (address.getWard() == "" || address.getWard() == null) {
            throw new AppException(ErrorCode.ADDRESS_WARD_EMPTY);
        } else if (address.getNameReceiver() == "" || address.getNameReceiver() == null) {
            throw new AppException(ErrorCode.ADDRESS_NAME_RECEIVER_EMPTY);
        } else if (address.getPhone() == "" || address.getPhone() == null) {
            throw new AppException(ErrorCode.ADDRESS_PHONE_EMPTY);
        } else if (address.getAddress() == "" || address.getAddress() == null) {
            throw new AppException(ErrorCode.ADDRESS_DETAILS_EMPTY);
        } else if (!address.getPhone().matches("0\\d{9}")) {
            throw new AppException(ErrorCode.PHONE_INVALID);
        } else {
            if (address.getIsDefault() == 1) {
                boolean reset = addressRepository.resetDefaultAddress(userId);
                if (!reset) {
                    throw new AppException(ErrorCode.SERVER_ERR);
                }
            }
            boolean check = addressRepository.saveAddress(userId, address);
            if (!check) {
                throw new AppException(ErrorCode.SERVER_ERR);
            }
            return check;
        }
    }



    @Override
    public boolean updateAddress(int userId, Address address) throws AppException {
        int check = addressRepository.checkAddressExist(address.getId(), userId);
        if(check == 0){
            throw new AppException(ErrorCode.ADDRESS_INVALID);
        }

        if(address.getIsDefault() == 1){
            boolean reset = addressRepository.resetDefaultAddress(userId);
            if (!reset) {
                throw new AppException(ErrorCode.SERVER_ERR);
            }
        }

        boolean result = addressRepository.updateAddress(userId, address);
        if (!result) {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }

    @Override
    public Address getAddress(int addressId, int userid) throws AppException {
        int check = addressRepository.checkAddressExist(addressId, userid);
        if (check == 0) {
            throw new AppException(ErrorCode.ADDRESS_INVALID);
        }
        Address a = addressRepository.getAddress(addressId);
        return a;
    }

    @Override
    public List<Address> getAllAddressByUserId(int userId) {
        List<Address> addressList = addressRepository.getAddressByUser(userId);
        return addressList;
    }


}
