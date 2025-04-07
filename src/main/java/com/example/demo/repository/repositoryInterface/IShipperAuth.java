package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Shipper;

public interface IShipperAuth {
    public Shipper shipperLogin(String phone);
    public int checkShipperPhone(String phone);
}
