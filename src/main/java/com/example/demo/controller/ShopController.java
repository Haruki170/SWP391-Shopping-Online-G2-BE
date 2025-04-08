package com.example.demo.controller;

import com.example.demo.dto.IdentifyDto;
import com.example.demo.dto.ShopDto;
import com.example.demo.entity.Shop;
import com.example.demo.entity.ShopAddress;
import com.example.demo.entity.ShopPhone;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ShopRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IShopOwnerService;
import com.example.demo.service.ServiceInterface.IShopService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private IShopService shopService;
    @Autowired
    private IShopOwnerService shopOwnerService;
    @Autowired
    private Token token;
    private final ObjectMapper jacksonObjectMapper;
    @Autowired
    private FileUpload fileUpload;

    @Autowired
    ShopRespository shopRespository;
    public ShopController(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @PostMapping("/add")
    public ResponseEntity addShop(@RequestParam("data") String data, @RequestParam("logo") MultipartFile file) throws IOException, AppException {
        Shop shop = jacksonObjectMapper.readValue(data, Shop.class);
        int id = token.getIdfromToken();
        shopService.addShop(shop, file, id);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get")
    public ResponseEntity getShop() throws AppException {

        int id = token.getIdfromToken();
        System.out.println(id);
        Shop shop = shopService.getShopByOwnerId(id);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", shop);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/detail")
    public ResponseEntity detailShop() throws AppException {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);
        Shop shop = shopService.getShopDetails(shopId);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", shop);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/update-detail")
    public ResponseEntity updateShop(@RequestParam("data") String data, @RequestParam(value = "logo", required = false) MultipartFile file) throws IOException, AppException {
        Shop shop = jacksonObjectMapper.readValue(data, Shop.class);
        shopService.updateShop(shop, file);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/update-address")
    public ResponseEntity updateAdress(@RequestBody List<ShopAddress> shop) throws IOException, AppException {
        int id = token.getIdfromToken();
        int shopId = shopService.getIdByOwnerId(id);
        shopService.updateShopAddress(shop, shopId);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/update-shipping-cost")
    public ResponseEntity updateShippingCost(@RequestBody Shop shop) throws IOException, AppException {
        shopService.updateShippingCost(shop, shop.getId());
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/update-phone")
    public ResponseEntity updatePhone(@RequestBody Shop shop) throws IOException, AppException {
        List<ShopPhone> phones = shop.getShopPhones();
        shopService.updateShopPhones(phones, shop.getId());
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/save-v2")
    public ResponseEntity addShopV2(@RequestParam("shop") String shop,
                                    @RequestParam("logo") MultipartFile logo,
//                                    @RequestParam("businessLicenseImages") List<MultipartFile> listImageBussiness,
                                    @RequestParam("frontImage") MultipartFile frontImage,
                                    @RequestParam("backImage") MultipartFile backImage,
                                    @RequestParam("identify") String identify
    ) throws IOException, AppException {
        System.out.println(identify);
        int id = token.getIdfromToken();
        String frontImagePath = fileUpload.uploadImage(frontImage);
        String backImagePath = fileUpload.uploadImage(backImage);
        Shop shopData = jacksonObjectMapper.readValue(shop, Shop.class);
        IdentifyDto identifyDto = jacksonObjectMapper.readValue(identify, IdentifyDto.class);
        identifyDto.setId(id);
        shopService.addShop(shopData, logo, id);
        shopOwnerService.updateIndentify(identifyDto, frontImagePath, backImagePath, identifyDto.getProvince(), identifyDto.getDistrict(), identifyDto.getWard());
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity getAll() throws AppException {
        List<ShopDto> list = shopService.getAllShops();
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);

    }
    


    @GetMapping("/get/{id}")
    public ResponseEntity getShopAdmin(@PathVariable int id) throws AppException {
        Shop shop = shopService.getShopByAdmin(id);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", shop);
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity changeStatus(@PathVariable int id){
        shopRespository.changeStatus(id);
        ApiResponse<String> apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/count-active")
    public ResponseEntity getCount (){
            int count = shopRespository.selectCountActive();
        System.out.println(count);
            ApiResponse apiResponse = new ApiResponse(200, "success", count);
            return ResponseEntity.ok(apiResponse);
    }

}
