package com.example.socket.mapper;

import java.sql.ResultSet;

public interface RowMapper<T>{
    public T mapRow(ResultSet rs);
}
