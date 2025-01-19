package com.example.demo.controller;


import com.example.demo.entity.ProductOption;
import com.example.demo.repository.repositoryInterface.IProductOptionRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ProductOptionService;
import com.example.demo.service.ServiceInterface.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductOptionController {
    @Autowired
    IProductOptionRepository productService;
    @Autowired
    private ProductOptionService productOptionService;

    @PostMapping("/addOption")
    public ResponseEntity<ApiResponse<ProductOption>> addOption(@RequestBody ProductOption productOption) {
        ProductOption savedProductOption = productOptionService.addOption(productOption);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201,"Option added successfully",savedProductOption));
    }
    @PutMapping("/updateOption")
    public ResponseEntity<ApiResponse<ProductOption>> updateOption(@RequestBody ProductOption productOption) {
        System.out.println("put: "+productOption);
        ProductOption savedProductOption = productOptionService.updateOption(productOption);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201,"Option added successfully",savedProductOption));
    }
    @DeleteMapping("/deleteOption")
    public ResponseEntity<ApiResponse<ProductOption>> deleteOption(@RequestParam int id) {
        productOptionService.updateOption(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201,"Option added successfully"));
    }

}
