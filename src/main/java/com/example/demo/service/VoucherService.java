package com.example.demo.service;

import com.example.demo.entity.Voucher;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.VoucherRepository;
import com.example.demo.service.ServiceInterface.IVoucherService;
import com.example.demo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService implements IVoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    private Util util;

    @Override
    public List<Voucher> findAllVoucher() throws AppException {
        return voucherRepository.findAll();
    }
//    @Override
//    public List<Voucher> findValidVouchersByShopId(int shopId) {
//        return voucherRepository.findValidByShopId(shopId);
//    }
    @Override
    public Voucher findById(int id) throws AppException {
        Voucher voucher = voucherRepository.findById(id);
        return voucher;
    }
    public List<Voucher> findVouchersByShopId(int shopId) {
        return voucherRepository.findByShopId(shopId);
    }
    @Override
    public boolean addVoucher(Voucher voucher) throws AppException {
        if (voucherRepository.checkExist(voucher.getCode()) > 0) {
            throw new AppException(ErrorCode.Voucher_Exist);
        }
        boolean check = voucherRepository.insert(voucher);
        if (check) {
            return true;
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public boolean updateVoucher(int id, Voucher voucher) throws AppException {
        if(voucherRepository.findById(id) == null) {
            throw new AppException(ErrorCode.Voucher_NotFound);
        }
        boolean check = voucherRepository.update(voucher);
        if (check) {
            return true;
        } else {
            throw new AppException(ErrorCode.SERVER_ERR);
        }
    }

    @Override
    public boolean deleteVoucher(int id) throws AppException {
        Voucher voucher = voucherRepository.findById(id);
        if (voucher == null) {
            throw new AppException(ErrorCode.Voucher_NotFound);
        }
        return voucherRepository.delete(id);
    }
    @Override
    public List<Voucher> searchVouchers(String code, Integer shopId, Double minDiscount, Double maxDiscount, String startDate, String endDate) {
        return voucherRepository.searchVouchers(code, shopId, minDiscount, maxDiscount, startDate, endDate);
    }
}
