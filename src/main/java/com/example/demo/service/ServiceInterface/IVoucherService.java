package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Voucher;
import com.example.demo.exception.AppException;

import java.util.List;

public interface IVoucherService {
    public List<Voucher> findAllVoucher() throws AppException;
    public Voucher findById(int id) throws AppException;
    public boolean addVoucher(Voucher voucher) throws AppException;
    public boolean updateVoucher(int id, Voucher voucher) throws AppException;
    public boolean deleteVoucher(int id) throws AppException;
    public List<Voucher> searchVouchers(String code, Integer shopId, Double minDiscount, Double maxDiscount, String startDate, String endDate);
}
