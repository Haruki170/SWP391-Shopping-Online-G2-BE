package com.example.demo.mapper;

import com.example.demo.dto.DailyTransaction;

import java.sql.ResultSet;

public class DaiLyMapper implements RowMapper<DailyTransaction> {

    @Override
    public DailyTransaction mapRow(ResultSet rs) {
        DailyTransaction dailyTransaction = new DailyTransaction();
        try {
            dailyTransaction.setDailyIncome(rs.getInt("daily_income"));
            dailyTransaction.setTransactionDate(rs.getString("transaction_date"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dailyTransaction;
    }
}
