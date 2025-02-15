package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Voucher;

import java.util.List;

public interface IVoucherRepository {
    public List<Voucher> findAll();
    public Voucher findById(int id);
   public List<Voucher> findByShopId(int id);
    public boolean insert(Voucher voucher);
    public boolean update(Voucher voucher);
    public boolean delete(int id);
    public int checkExist(String code);
}
