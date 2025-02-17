package com.example.demo.repository;

import com.example.demo.entity.TransactionShop;
import com.example.demo.mapper.ShopTransactionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ShopTransactionRepository extends AbstractRepository<TransactionShop> {
    public List<TransactionShop> getALlTransactions(int shopId) {
        String sql = "SELECT * FROM shop_transaction AS a\n" +
                "JOIN shop AS s ON s.shop_id = a.shop_id\n" +
                "LEFT JOIN customer AS c ON c.customer_id = a.customer_id\n" +
                "JOIN `order` AS o ON o.order_id = a.order_id where a.shop_id = ? ORDER BY a.created_at DESC";
        return super.findAll(sql, new ShopTransactionMapper(), shopId);
    }

    public boolean savePay(TransactionShop transactionShop) {
        String sql = "INSERT INTO shop_transaction (shop_id, order_id, amount, " +
                "type, is_paid_commission, description) " +
                "VALUES (?,?,?,?,?,?);\n";
        return super.save(sql,  transactionShop.getShop().getId(),
                transactionShop.getOrder().getId(), transactionShop.getAmount(), transactionShop.getType(), transactionShop.getIsCommisson(),transactionShop.getDescription());
    }

    public boolean updatePay(int tid) {
        System.out.println(tid);
        String sql = "UPDATE shop_transaction SET is_paid_commission = 1 WHERE id = ? ";
        return super.save(sql, tid);
    }


}
