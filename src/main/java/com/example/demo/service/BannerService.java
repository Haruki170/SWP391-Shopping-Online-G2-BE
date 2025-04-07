package com.example.demo.service;

import com.example.demo.entity.Banner;
import com.example.demo.mapper.BannerRowMapper;
import com.example.demo.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    private final BannerRepository bannerRepository = new BannerRepository();

    public List<Banner> getAllBanners() {
        String sql = "SELECT * FROM banner";
        return bannerRepository.findAll(sql, new BannerRowMapper());
    }

    public Banner getBannerById(int id) {
        String sql = "SELECT * FROM banner WHERE id = ?";
        return bannerRepository.findOne(sql, new BannerRowMapper(), id);
    }

    public Banner createBanner(Banner banner) {
        String sql = "INSERT INTO banner (image, description) VALUES (?, ?)";
        int id = bannerRepository.saveAndReturnId(sql, banner.getImage(), banner.getDescription());
        banner.setId(id);
        return banner;
    }

    public Banner updateBanner(int id, Banner banner) {
        String sql = "UPDATE banner SET image = ?, description = ? WHERE id = ?";
        boolean updated = bannerRepository.save(sql, banner.getImage(), banner.getDescription(), id);
        return updated ? getBannerById(id) : null;
    }

    public boolean deleteBanner(int id) {
        String sql = "DELETE FROM banner WHERE id = ?";
        return bannerRepository.delete(sql, id);
    }
}
