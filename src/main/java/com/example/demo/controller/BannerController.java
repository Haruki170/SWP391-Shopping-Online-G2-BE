package com.example.demo.controller;

import com.example.demo.entity.Banner;
import com.example.demo.service.BannerService;
import com.example.demo.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<Object> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }
    @GetMapping("/all")
    public ResponseEntity<Object> getAllBannersPublic() {
        List<Banner> banners = bannerService.getAllBanners();
        return ResponseEntity.ok(banners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable int id) {
        Banner banner = bannerService.getBannerById(id);
        return banner != null ? ResponseEntity.ok(banner) : ResponseEntity.notFound().build();
    }
    @Autowired
    private FileUpload fileUpload;
    @PostMapping
    public ResponseEntity<Banner> createBanner(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description
    ) {
        try {
            // Upload file ảnh
            String imageUrl = fileUpload.uploadImage(image);

            // Lưu thông tin banner vào DB
            Banner banner = new Banner();
            banner.setImage(imageUrl);  // Lưu đường dẫn URL
            banner.setDescription(description);
            Banner savedBanner = bannerService.createBanner(banner);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedBanner);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banner> updateBanner(
            @PathVariable int id,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("description") String description
    ) {
        try {
            Banner existingBanner = bannerService.getBannerById(id);
            if (existingBanner == null) {
                return ResponseEntity.notFound().build();
            }

            existingBanner.setDescription(description);
            if (image != null && !image.isEmpty()) {
                String imageUrl = fileUpload.uploadImage(image);
                existingBanner.setImage(imageUrl);
            }

            Banner updatedBanner = bannerService.updateBanner(id, existingBanner);
            return ResponseEntity.ok(updatedBanner);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable int id) {
        boolean deleted = bannerService.deleteBanner(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

