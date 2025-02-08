package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.mapper.RowMapper;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AbstractRepository<T> {
    public List<T> findAll(String sql, RowMapper<T> rowMapper, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<T> list = new ArrayList<T>();
        try {
            con = DBContext.getConnect();
            ps = con.prepareStatement(sql);
            setParam(ps, params);
            rs = ps.executeQuery();
            while (rs.next()) {
                T t = rowMapper.mapRow(rs);
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, ps, rs);
        }
        return list;
    }

    public int saveAndReturnId(String sql, Object... params) {
        int generatedId = 0;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getConnect();
            con.setAutoCommit(false); // Bắt đầu giao dịch
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setParam(ps, params);
            ps.executeUpdate(); // Thực hiện chèn

            // Lấy ID vừa chèn
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Lấy ID từ ResultSet
                }
            }
            con.commit(); // Xác nhận giao dịch
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return generatedId; // Trả về ID đã được sinh ra
    }

    public boolean save(String sql, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getConnect();
            con.setAutoCommit(false); // Bắt đầu giao dịch
            ps = con.prepareStatement(sql);
            setParam(ps, params);
            ps.executeUpdate();
            con.commit(); // Xác nhận giao dịch
            return true;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            close(con, ps, null);
        }
        return false;
    }





    public int selectCount(String sql, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBContext.getConnect();
            ps = con.prepareStatement(sql);
            setParam(ps, params);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(con, ps, rs);
        }
        return count;
    }





    public T findOne(String sql, RowMapper<T> mapper, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T t = null;
        try {
            con = DBContext.getConnect();
            ps = con.prepareStatement(sql);
            setParam(ps, params);
            rs = ps.executeQuery();
            if (rs.next()) {
                t = mapper.mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, ps, rs);
        }
        return t;
    }


    public boolean delete(String sql, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBContext.getConnect();
            con.setAutoCommit(false); // Bắt đầu giao dịch
            ps = con.prepareStatement(sql);
            setParam(ps, params);
            ps.executeUpdate();
            con.commit(); // Xác nhận giao dịch
            return true;
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback(); // Hoàn tác nếu có lỗi
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            close(con, ps, null);
        }
        return false;
    }



    public void setParam(PreparedStatement ps, Object... params) throws SQLException {
        int count = 1;
        for (Object param : params) {
            if (param instanceof String) {
                ps.setString(count, (String) param);
            } else if (param instanceof Integer) {
                ps.setInt(count, (Integer) param);
            } else if (param instanceof Double) {
                ps.setDouble(count, (Double) param);
            } else if (param instanceof Float) {
                ps.setFloat(count, (Float) param);
            } else if (param instanceof Long) {
                ps.setLong(count, (Long) param);
            } else if (param instanceof Boolean) {
                ps.setBoolean(count, (Boolean) param);
            }

            count++;
        }
    }

    public void close(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<T> findAllWithChildren(String sql, RowMapper<T> parentMapper, RowMapper<?> childMapper, String parentIdField, String childSql, String childSetterMethod, Object... params) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<Integer, T> parentMap = new LinkedHashMap<>();

        try {
            con = DBContext.getConnect();
            ps = con.prepareStatement(sql);
            setParam(ps, params);
            rs = ps.executeQuery();

            // Lấy tất cả các đối tượng cha
            while (rs.next()) {
                T parent = parentMapper.mapRow(rs);
                Integer parentId = rs.getInt(parentIdField);

                if (!parentMap.containsKey(parentId)) {
                    parentMap.put(parentId, parent);
                }
            }

            // Duyệt qua từng đối tượng cha và gán danh sách con
            for (Map.Entry<Integer, T> entry : parentMap.entrySet()) {
                List<?> children = findChildren(childSql, childMapper, entry.getKey());
                try {
                    // Sử dụng Reflection để gọi phương thức setter của đối tượng cha
                    Method setter = entry.getValue().getClass().getMethod(childSetterMethod, List.class);
                    setter.invoke(entry.getValue(), children);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to set children list using reflection", e);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, ps, rs);
        }
        return new ArrayList<>(parentMap.values());
    }

    private List<?> findChildren(String sql, RowMapper<?> childMapper, Integer parentId) {
        List<Object> children = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBContext.getConnect();
            ps = con.prepareStatement(sql);
            ps.setInt(1, parentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object child = childMapper.mapRow(rs);
                children.add(child);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(con, ps, rs);
        }
        return children;
    }


}
