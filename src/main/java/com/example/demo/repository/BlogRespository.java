package com.example.demo.repository;

import com.example.demo.entity.Banner;
import com.example.demo.entity.Blog;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.mapper.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BlogRespository extends AbstractRepository <Blog>{
    @Override
    public List<Blog> findAll(String sql, RowMapper<Blog> rowMapper, Object... params) {
        return super.findAll(sql, rowMapper, params);
    }

    @Override
    public int saveAndReturnId(String sql, Object... params) {
        return super.saveAndReturnId(sql, params);
    }
    public List<Blog> findAllByShopId(Long shopId) {
        String sql = "SELECT * FROM blog WHERE shop_id = ?";
        return findAll(sql, new BlogMapper(), shopId);
    }
    @Override
    public boolean save(String sql, Object... params) {
        return super.save(sql, params);
    }

    @Override
    public int selectCount(String sql, Object... params) {
        return super.selectCount(sql, params);
    }

    @Override
    public Blog findOne(String sql, RowMapper<Blog> mapper, Object... params) {
        return super.findOne(sql, mapper, params);
    }

    @Override
    public boolean delete(String sql, Object... params) {
        return super.delete(sql, params);
    }

    @Override
    public void close(Connection con, PreparedStatement ps, ResultSet rs) {
        super.close(con, ps, rs);
    }

    @Override
    public List<Blog> findAllWithChildren(String sql, RowMapper<Blog> parentMapper, RowMapper<?> childMapper, String parentIdField, String childSql, String childSetterMethod, Object... params) {
        return super.findAllWithChildren(sql, parentMapper, childMapper, parentIdField, childSql, childSetterMethod, params);
    }

    @Override
    public void setParam(PreparedStatement ps, Object... params) throws SQLException {
        super.setParam(ps, params);
    }
}
