package com.example.demo.repository;

import com.example.demo.dto.DailyTransaction;
import com.example.demo.entity.TransactionAdmin;
import com.example.demo.mapper.AdminTransactionMapper;
import com.example.demo.mapper.DaiLyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminTransactionRepository extends AbstractRepository<TransactionAdmin> {
    public List<TransactionAdmin> getAllTransactionsByShop(int shopId) {
        String sql = "SELECT * \n" +
                "FROM admin_transaction AS a\n" +
                "JOIN `order` AS o ON o.order_id = a.order_id\n" +
                "JOIN shop AS s ON s.shop_id = a.shop_id\n" +
                "LEFT JOIN customer AS c ON c.customer_id = a.customer_id\n" +
                "WHERE a.shop_id = ? order by a.created_at DESC";

        return super.findAll(sql, new AdminTransactionMapper(), shopId);
    }


    public List<TransactionAdmin> getAllTransactions() {
        String sql = "SELECT *\n" +
                "FROM admin_transaction AS a\n" +
                "JOIN `order` AS o ON o.order_id = a.order_id\n" +
                "JOIN shop AS s ON s.shop_id = a.shop_id\n" +
                "LEFT JOIN customer AS c ON c.customer_id = a.customer_id order by a.created_at desc " +
                "LIMIT 6;";
        return super.findAll(sql, new AdminTransactionMapper());
    }


    public boolean saveTransaction(TransactionAdmin transaction) {
        String sql = " INSERT INTO admin_transaction (shop_id,  order_id, amount, type, is_paid, net_amount, description)\n" +
                "  VALUES (?,?,?,?,?,?, ?)";
        return super.save(sql, transaction.getShop().getId(), transaction.getOrder().getId(), transaction.getAmount(),transaction.getType(),
                transaction.getIsPaid(), transaction.getNetAmount(),transaction.getDescription());
    }

    public boolean updateIsPaid(int tid) {
        String sql = "UPDATE admin_transaction SET is_paid = 1 WHERE id = ?";
        return super.save(sql, tid);
    }

    public boolean savePayForShop(TransactionAdmin transaction) {
        String sql = " INSERT INTO admin_transaction (shop_id,  order_id, amount, type,description, is_paid )\n" +
                "  VALUES (?,?,?,?,?,?)";
        return super.save(sql, transaction.getShop().getId(), transaction.getOrder().getId(), transaction.getAmount(),
                transaction.getType(), transaction.getDescription(), transaction.getIsPaid());
    }

    public List<DailyTransaction> getDaily() {
        String sql = "SELECT \n" +
                "    DATE_FORMAT(created_at, '%Y-%m-%d') AS transaction_date,\n" +
                "    SUM(CASE \n" +
                "            WHEN type = 1 AND is_paid = 2 THEN amount \n" +
                "            WHEN type = 1 AND net_amount IS NOT NULL THEN net_amount \n" +
                "            ELSE 0 \n" +
                "        END) - \n" +
                "    SUM(CASE \n" +
                "            WHEN type = 0 AND net_amount IS NOT NULL THEN net_amount \n" +
                "            ELSE 0 \n" +
                "        END) AS daily_income\n" +
                "FROM \n" +
                "    admin_transaction\n" +
                "WHERE \n" +
                "    (type = 1 AND (is_paid = 2 OR net_amount IS NOT NULL)) OR\n" +
                "    (type = 0 AND net_amount IS NOT NULL)\n" +
                "GROUP BY \n" +
                "    DATE_FORMAT(created_at, '%Y-%m-%d')\n" +
                "ORDER BY \n" +
                "    transaction_date;";
        AbstractRepository a = new AbstractRepository();
        return a.findAll(sql, new DaiLyMapper());
    }

    public int getIncomeDay() {
        String sql = "SELECT \n" +
                "    SUM(CASE \n" +
                "            WHEN type = 1 AND is_paid = 2 THEN amount \n" +
                "            WHEN type = 1 AND net_amount IS NOT NULL THEN net_amount \n" +
                "            ELSE 0 \n" +
                "        END) AS total_income_today\n" +
                "FROM \n" +
                "    admin_transaction\n" +
                "WHERE \n" +
                "    DATE(created_at) = CURDATE() \n" +
                "    AND (type = 1 AND (is_paid = 2 OR net_amount IS NOT NULL)) OR \n" +
                "    (type = 0 AND net_amount IS NOT NULL);;";
        return super.selectCount(sql);
    }

    public int countShopTransaction(int shopId) {
        String sql = "SELECT count(*) FROM admin_transaction  WHERE shop_id = ? AND `type` = 1 AND is_paid = 0";
        return super.selectCount(sql, shopId);
    }
}
