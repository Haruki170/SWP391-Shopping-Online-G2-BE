package com.example.demo.repository;

import com.example.demo.entity.Voucher;
import com.example.demo.mapper.VoucherMapper;
import com.example.demo.repository.repositoryInterface.IVoucherRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VoucherRepository extends AbstractRepository<Voucher> implements IVoucherRepository {
    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        List<Voucher> list = super.findAll(sql, new VoucherMapper());
        return list;
    }

    @Override
    public Voucher findById(int id) {
        String sql = "select * from voucher where id = ?";
        Voucher voucher = super.findOne(sql, new VoucherMapper(), id);
        return voucher;
    }

    @Override
    public List<Voucher> findByShopId(int id) {
        String sql = "select * from voucher where shop_id = ?";
        return  super.findAll(sql, new VoucherMapper(), id);

    }

    @Override
    public boolean insert(Voucher voucher) {
        String sql = "insert into voucher(code, description, discount_amount, min_order_amount, start_date, end_date, quantity) values(?,?,?,?,?,?,?) ";
        super.save(sql, voucher.getCode(), voucher.getDescription(), voucher.getDiscountAmount(),voucher.getMinOrderAmount(), voucher.getStartDate(), voucher.getEndDate(), voucher.getQuantity());
        return true;
    }

    @Override
    public boolean update(Voucher voucher) {
        String sql = "update voucher set description=?, discount_amount=?, start_date=?, end_date=? where voucher_id=?";
        super.save(sql, voucher.getDescription(), voucher.getDiscountAmount(), voucher.getStartDate(), voucher.getEndDate(), voucher.getId());
        return true;
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from voucher where voucher_id=?";
        super.save(sql, id);
        return true;
    }

    @Override
    public int checkExist(String code) {
        String sql = "select * from voucher where code = ?";
        List<Voucher> list = super.findAll(sql, new VoucherMapper(), code);
        return list.size();
    }
    public List<Voucher> searchVouchers(String code, Integer shopId, Double minDiscount, Double maxDiscount, String startDate, String endDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM voucher WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (code != null && !code.isEmpty()) {
            sql.append(" AND code LIKE ?");
            params.add("%" + code + "%");
        }
        if (shopId != null) {
            sql.append(" AND shop_id = ?");
            params.add(shopId);
        }
        if (minDiscount != null) {
            sql.append(" AND discount_amount >= ?");
            params.add(minDiscount);
        }
        if (maxDiscount != null) {
            sql.append(" AND discount_amount <= ?");
            params.add(maxDiscount);
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND start_date >= ?");
            params.add(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND end_date <= ?");
            params.add(endDate);
        }

        return super.findAll(sql.toString(), new VoucherMapper(), params.toArray());
    }
}
