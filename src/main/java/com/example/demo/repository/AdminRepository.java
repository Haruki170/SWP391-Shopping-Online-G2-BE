package com.example.demo.repository;

import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.repository.repositoryInterface.IAdminRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class AdminRepository extends AbstractRepository<Admin> implements IAdminRepository {
    @Override
    public List<Admin> findAllAdmin() {
        String sql = "select * from admin";
        List<Admin> list = super.findAll(sql, new AdminMapper());
        return list;
    }

    @Override
    public Admin findAdminById(int id) {
        String sql = "select * from admin where admin_id = ? ";
        Admin admin = (Admin) super.findOne(sql, new AdminMapper(), id);
        return admin;
    }



    @Override
    public boolean update(Admin admin) {
        String sql = "update admin set admin_name=?, admin_email=?, admin_password=?, admin_phone=?, admin_address=?, admin_district=?, admin_ward=?, admin_province=?, status=? where admin_id=?";
        super.save(sql, admin.getName(), admin.getEmail(), admin.getPassword(), admin.getPhone(), admin.getAddress(), admin.getDistrict(), admin.getWard(), admin.getProvince(), admin.getStatus(), admin.getId());
        return true;
    }

    @Override
    public boolean delete(Admin admin) {
        String sql = "delete from admin where admin_id = ?";
        super.save(sql, admin.getId());
        return true;
    }

    @Override
    public int checkExist(String email) {
        String sql = "select admin_id from admin where admin_email=? union select customer_id from customer where customer_email=? union select shop_owner_id from shop_owner where shop_owner_email=?";
        List<Admin> list = super.findAll(sql, new AdminMapper(), email, email, email);
        return list.size();
    }

    @Override
    public Admin findByEmail(String email) {
        String sql = "select * from admin where admin_email = ? ";
        Admin admin = (Admin) super.findOne(sql, new AdminMapper(), email);
        return admin;
    }

    @Override
    public int updateStatus(int id, int status) {
        System.out.println(id + status);
        String sql = "update admin set status=? where admin_id=?";
        super.save(sql,status,id);
        return 1;
    }

    @Override
    public boolean forgotPasswordAdminCode(String code, String email) {
        String sql = "update admin set forgot_password_code = ? where admin_email = ?";
        boolean check = super.save(sql, code, email);
        return check;
    }

    @Override
    public boolean deletePasswordAdminCode(String email) {
        String sql = "update admin set forgot_password_code = null where admin_id = ?";
        return super.save(sql, email);
    }

    @Override
    public Admin resetPasswordAdminByCode(String code) {
        String sql = "select * from admin where forgot_password_code = ?";
        Admin admin = super.findOne(sql,new AdminMapper(),code);
        return admin;
    }

    @Override
    public int checkCodeAdmin(String code) {
        String sql = "SELECT count(*) FROM `admin` WHERE forgot_password_code = ?";
        return super.selectCount(sql, code);
    }

    @Override
    public boolean updatePassword(String password, int id) {
        String sql = "update admin SET admin_password = ? WHERE admin_id = ?";
        super.save(sql, password, id);
        return true;
    }

    public int checkLogin(String email, int role){
        String sql = "select count(*) from admin where admin_email = ? and role = ?";
        return super.selectCount(sql, email, role);
    }



}
