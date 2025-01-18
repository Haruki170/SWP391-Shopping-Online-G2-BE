package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.FavouriteService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavouriteController {
    @Autowired
    Token tokenService;

    @Autowired
    FavouriteService favouriteService;

    //lấy danh sách customer
    @PreAuthorize("hasAuthority('customer')")
    @GetMapping("/view-favourite")
    public ResponseEntity viewFavourite() {
        int id = tokenService.getIdfromToken();
        Favourite favourite = favouriteService.getFavourite(id);
        ApiResponse<Favourite> api = new ApiResponse<>(200, "success",favourite);
        return ResponseEntity.ok(api);
    }

    //add vào favourite
    @PreAuthorize("hasAuthority('customer')")
    @PostMapping("/add-to-favourite")
    public ResponseEntity addToFavourite(@RequestBody Favourite_item favourite_item) {
        int id = tokenService.getIdfromToken();
        favouriteService.addFavourite(favourite_item, id);
        ApiResponse<Favourite> api = new ApiResponse<>(200, "success", null);
        return ResponseEntity.ok(api);
    }

    //xóa sản phẩm yêu thích, hàm này sẽ truyền một đối tượng favourite_item
    @PreAuthorize("hasAuthority('customer')")
    @PutMapping("/delete-favourite")
    public ResponseEntity deleteFavourite(@RequestBody Favourite_item favourite_item) {
//        System.out.println("favourite_item = " + favourite_item);
        favouriteService.deleteFavourite(favourite_item);
//        System.out.println("XOA THANH CONG");
        ApiResponse<Favourite> api = new ApiResponse<>(200, "success", null);
        return ResponseEntity.ok(api);
    }

    //Tổng sản phẩm từ favourite items (hiện tim)
    @PreAuthorize("hasAuthority('customer')")
    @GetMapping("/total-item")
    public ResponseEntity totalItem() {
        int id = tokenService.getIdfromToken();
        int total = favouriteService.totalItemFromFavourite(id);
        ApiResponse<Integer> api = new ApiResponse<>(200, "success",total);
        return ResponseEntity.ok(api);
    }

    //xóa hết sản phẩm fav
    @PreAuthorize("hasAuthority('customer')")
    @DeleteMapping("/delete-all-favourite")
    public ResponseEntity deleteAllFavourite() {
        int id = tokenService.getIdfromToken();
        favouriteService.DeleteAllFavourites(id);
        ApiResponse<Integer> api = new ApiResponse<>(200, "success", null);
        return ResponseEntity.ok(api);
    }

    //xem sản phẩm có được yêu thích không
    @PreAuthorize("hasAuthority('customer')")
    @GetMapping("/is-product-favourite/{productId}")
    public ResponseEntity isProductFavourite(@PathVariable int productId) {
        int customerId = tokenService.getIdfromToken();
        boolean isFavourite = favouriteService.isProductInFavourite(customerId, productId);
        ApiResponse<Boolean> api = new ApiResponse<>(200, "success", isFavourite);
        return ResponseEntity.ok(api);
    }

    //hàm delete ở phần details (dựa vào product_id và customer_id)
    @PreAuthorize("hasAuthority('customer')")
    @DeleteMapping("/delete-details/{productid}")
    public ResponseEntity deleteDetails(@PathVariable int productid) {
        int customerId = tokenService.getIdfromToken();
        boolean delete = favouriteService.DeleteFromDetails(productid, customerId);
        ApiResponse<Boolean> api = new ApiResponse<>(200, "success", delete);
        return ResponseEntity.ok(api);
    }
}

