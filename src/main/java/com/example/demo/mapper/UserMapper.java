package com.example.demo.mapper;
import com.example.demo.entity.User;
import java.sql.ResultSet;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setPassword(rs.getString("user_password"));
            user.setEmail(rs.getString("user_email"));
            user.setAvatar(rs.getString("user_avatar"));
            user.setCreate_at(rs.getString("create_at"));
            user.setUpdate_at(rs.getString("update_at"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
