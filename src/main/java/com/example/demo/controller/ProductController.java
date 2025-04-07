package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.Shop;
import com.example.demo.entity.shop_report;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopOwnerReportRespository;
import com.example.demo.repository.repositoryInterface.IProductImageRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.Product_ImageService;
import com.example.demo.service.ServiceInterface.IProductService;
import com.example.demo.service.ServiceInterface.IShopService;
import com.example.demo.service.ShopService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin
@RestController // nhan request tra ve response
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @Autowired
    FileUpload fileUpload;
    @Autowired
    private IShopService shopService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Token token;
    // Declare uploadDir as a class-level variable
    @Autowired
    IProductImageRepository productImageRepository;
    @Autowired
    private  Product_ImageService productImageService;
    @Autowired
    ShopOwnerReportRespository  shopOwnerReportRespository;
    private final String uploadDir = "uploads/";


    @GetMapping("/getCategory/{id}")
    public List<Product> getProductsById(@PathVariable int id) {
        return productService.findProductsById(id);
    }

    @GetMapping("/getbyID/{id}")
    public ResponseEntity<Product> getByID(@PathVariable int id) {

        Product product = productService.findIDProduct2(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() throws AppException {
        int id = token.getIdfromToken();
        Shop shop = shopService.getShopByOwnerId(id);

        List<Product> products;
        if (shop != null) {
            // Shop owner sees all their products
            products = productService.findAllProducts(shop.getId());
        } else {
            // Regular users only see active products (status = 1)
            products = productService.findAllProducts(shop.getId()).stream()
                    .filter(p -> p.getStatus() == 1)
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Products retrieved successfully", products));
    }

//

    @PostMapping("/add")
        public ResponseEntity<ApiResponse<Product>> addProduct(@RequestParam("avatar") MultipartFile file,
                                                               @RequestParam("product") String product,
                                                               @RequestParam(value="images[]", required = false) List<MultipartFile> images) throws IOException, AppException {
        ObjectMapper o = new ObjectMapper();
        System.out.println(product);
        Product p = o.readValue(product, Product.class);
        p.setAvatar(fileUpload.uploadImage(file));

        //get shop ip
        int id = token.getIdfromToken();
        Shop shop = shopService.getShopByOwnerId(id);
        productService.addProduct(p,shop.getId());
        if(images != null){
            for (MultipartFile imageFile : images) {
                String imageUrl = fileUpload.uploadImage(imageFile);
                ProductImage productImage = new ProductImage();
                productImage.setProductId(productService.getLastId());
                productImage.setImage(imageUrl);
                productImageRepository.addProductImageById(productImage); // Save image using service
            }
        }


        return ResponseEntity.ok(new ApiResponse<>(200, "Product added successfully", p));
        }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @RequestParam(value = "avatar", required = false) MultipartFile file,
            @RequestParam("product") String product) throws IOException, AppException {

        ObjectMapper objectMapper = new ObjectMapper();
        Product productToUpdate = objectMapper.readValue(product, Product.class);

        // Check if the product exists
        Product existingProduct = productService.findIDProduct2(productToUpdate.getId());//tach

        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Product not found", null));
        }

        // Handle file upload if a new file is provided
        if (file != null && !file.isEmpty()) {
            String uploadedImagePath = fileUpload.uploadImage(file);
            productToUpdate.setAvatar(uploadedImagePath);
        } else {
            // Retain the existing avatar if no new file is uploaded
            productToUpdate.setAvatar(existingProduct.getAvatar());
        }

        // Update the product
        Product updatedProduct = productService.updateProduct(productToUpdate);
        return ResponseEntity.ok(new ApiResponse<>(200, "Product updated successfully", updatedProduct));
    }
    @PutMapping("/updateStatus")
    public ResponseEntity<ApiResponse<Product>> updateProductStatusProdcut(
            @RequestParam int productId,
            @RequestParam int status) {

        Product existingProduct = productRepository.findById2(productId);
        if (existingProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Product not found", null));
        }

        // Only allow transitions between 1 and 2 (active <-> ceased)
        if (status != 1 && status != 2) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, "Invalid status transition", null));
        }

        existingProduct.setStatus(status);
        Product updatedProduct = productService.updateProductStatus(existingProduct);
        return ResponseEntity.ok(new ApiResponse<>(200, "Product updated successfully", updatedProduct));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable int id) {
        Product product = productService.findIDProduct(id);
        if (product != null) {
            productService.deleteProduct(product);
            return ResponseEntity.ok(new ApiResponse<>(200, "Product deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Product not found", null));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Product>>> filterProducts(
            @RequestParam(value = "category", defaultValue = "0") int category,
            @RequestParam(value = "province", defaultValue = "") String province,
            @RequestParam(value = "rating", defaultValue = "0") int rating,
            @RequestParam(value = "price", defaultValue = "0") double price,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "sort", defaultValue = "asc") String sortOrder, // "asc" or "desc" for price
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "16") int size) {

        List<Product> products = productService.filterProducts(category, province, rating, price, search, sortOrder, page, size);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Products retrieved successfully", products));
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> searchProductsByName(@RequestParam(value = "name", defaultValue = "") String productName) {
        List<Map<String, Object>> products = productService.searchProductsByName(productName);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Products retrieved successfully", products));
    }


    @GetMapping("/hot")
    public ResponseEntity<List<Product>> findProductByCategoryHot(@RequestParam("cID") int cID) {
        List<Product> products = productService.findProductByCategoryHot(cID);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{productID}/images")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProductWithImages(@PathVariable int productID) {
        Product product = productService.findIDProduct(productID);

        if (product != null) {
            List<ProductImage> productImages = productImageService.getProductImagesByProductID(productID);
            Map<String, Object> responseData = new LinkedHashMap<>();
            responseData.put("id", product.getId());
            responseData.put("name", product.getName());
            responseData.put("images", productImages);
            ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(200, "Product images found", responseData);
            return ResponseEntity.ok(apiResponse);
        } else {
            ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>(404, "Product not found", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        }
    }
    @GetMapping("/get-all-product/{id}")
    public ResponseEntity getAllAdmin(@PathVariable int id) {
        System.out.println(id);
        List<Product> products = productService.findProductAdmin(id);
        ApiResponse apiResponse = new ApiResponse<>(200, "success", products);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/update-status")
    public ResponseEntity<ApiResponse<Product>> updateProductStatus(@RequestParam int id,
                                                                    @RequestParam int status,
                                                                    @RequestBody(required = false) shop_report report) {
        int idUser = token.getIdfromToken();
        if(report != null ){
            shopOwnerReportRespository.insertWithResponse(report,report.getId());
        }
        productService.updateStatus(id,status);
        return ResponseEntity.ok(new ApiResponse<>(200, "Product updated successfully", null));
    }

    @PostMapping("/{productId}/add-image")
    public ResponseEntity<String> addProductImage(
            @PathVariable int productId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            // Giả sử bạn đã lưu trữ ảnh và có URL của ảnh (có thể là URL từ CDN hoặc đường dẫn máy chủ)
            String imageUrl = fileUpload.uploadImage(image);  // Giả sử phương thức lưu ảnh trả về URL ảnh
            ProductImage productImage = new ProductImage();
            productImage.setProductId(productId);
            productImage.setImage(imageUrl);
            productImageRepository.addProductImageById(productImage);
            return ResponseEntity.ok("Image added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding image: " + e.getMessage());
        }
    }
    // API để xóa hình ảnh khỏi sản phẩm
    @DeleteMapping("/remove-image/{imageId}")
    public ResponseEntity<String> removeProductImage(
            @PathVariable int imageId
    ) {
        try {
            productImageRepository.removeProductImageById(imageId);
            return ResponseEntity.ok("Image removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error removing image: " + e.getMessage());
        }
    }

    // API để cập nhật hình ảnh cho sản phẩm
    @PostMapping("/update-image/{imageId}")
    public ResponseEntity<String> updateProductImage(
            @PathVariable int imageId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            String newImageUrl = fileUpload.uploadImage(image);  // Lưu ảnh mới và lấy URL
            productImageRepository.updateProductImage(imageId, newImageUrl);
            return ResponseEntity.ok("Image updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating image: " + e.getMessage());
        }
    }

    // API để cập nhật tất cả hình ảnh cho sản phẩm
    @PostMapping("/update-images/{productId}")
    public ResponseEntity<String> updateAllProductImages(
            @PathVariable int productId,
            @RequestParam("images[]") List<MultipartFile> images
    ) {
        try {
            List<String> imageUrls = saveImages(images);  // Lưu tất cả ảnh và lấy URL của chúng
            productImageRepository.updateAllProductImages(productId, imageUrls);
            return ResponseEntity.ok("All images updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating images: " + e.getMessage());
        }
    }
    private List<String> saveImages(List<MultipartFile> images) {
        // Lưu tất cả ảnh và trả về danh sách URL
        // Giả sử trả về các URL của ảnh đã lưu
        return images.stream()
                .map(image -> {
                    try {
                        return fileUpload.uploadImage(image);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })  // Giả sử mỗi ảnh đều được lưu và trả về URL
                .collect(Collectors.toList());
    }
}
