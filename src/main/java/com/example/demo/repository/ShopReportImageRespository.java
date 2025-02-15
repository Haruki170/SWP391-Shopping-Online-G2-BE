package com.example.demo.repository;

import com.example.demo.entity.*;
import com.example.demo.mapper.CustomerReportMapper;
import com.example.demo.mapper.ShopReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopReportImageRespository extends  AbstractRepository<shop_report> {
    public boolean saveShopImage(String iamge, int id){
        AbstractRepository a = new AbstractRepository();
        String sql ="INSERT INTO shop_report_image (report_image_content, report_id) VALUES (?, ?)";
        return  a.save(sql, iamge,id);
    }


    public boolean saveResponse(shop_report report) {
        String sql = "Update shop_report set report_response= ? , status = 1 where id=? ";
        return super.save(sql, report.getReportResponse(), report.getId());
    }


    public boolean saveSeen(int id) {
        String sql = "Update shop_report set status = 0 where report_id=? ";
        return super.save(sql, id);
    }

}
