package com.example.demo.controller;


import com.example.demo.entity.ProductAddOn;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IProductAddOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductAddOnController {
    @Autowired
    private IProductAddOnService productAddOnService;

    @PostMapping("/addAddOn")
    public ResponseEntity<ApiResponse<ProductAddOn>> addAddOn(@RequestBody ProductAddOn productAddOn) {
        System.out.println("productAddOn: "+productAddOn);
        ProductAddOn savedProductAddOn = productAddOnService.addAddOn(productAddOn);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201,"AddOn added successfully",savedProductAddOn));
    }
    @PutMapping("/updateAddOn")
    public ResponseEntity<ApiResponse<ProductAddOn>> updateAddOn(@RequestBody ProductAddOn productAddOn) {
        System.out.println("productAddOn: "+productAddOn);
        productAddOnService.updateProductAddOn(productAddOn);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(201,"AddOn added successfully"));
    }
    @DeleteMapping("/deleteAddOn")
    public ResponseEntity<ApiResponse<ProductAddOn>> deleteAddOn(@RequestParam int addOnId) {
        System.out.println("productAddOn: "+addOnId);
        productAddOnService.deleteProductAddOn(addOnId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(201,"AddOn added successfully"));
    }

}
