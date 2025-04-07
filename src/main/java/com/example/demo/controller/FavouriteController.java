package com.example.demo.controller;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;
import com.example.demo.jwt.Token;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.FavouriteService;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favourite")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavouriteController {

    @Autowired
    private Token token;

    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("/view-favourite")
    public ResponseEntity viewFavourite() {
        int id = token.getIdfromToken(); // Lấy id từ token
        Favourite favourite = favouriteService.getFavourite(id);
        ApiResponse<Favourite> api = new ApiResponse<>(200, "success", favourite);
        return ResponseEntity.ok(api);
    }

    @PostMapping("/add-to-favourite")
    public ResponseEntity addToFavourite(@RequestBody Favourite_item favourite_item) {
        int id = token.getIdfromToken(); // Lấy id từ token
        favouriteService.addFavourite(favourite_item, id);
        ApiResponse<Favourite> api = new ApiResponse<>(200, "success", null);
        return ResponseEntity.ok(api);
    }

    @PutMapping("/delete-favourite")
    public ResponseEntity deleteFavourite(@RequestBody Favourite_item favourite_item) {
        favouriteService.deleteFavourite(favourite_item);
        ApiResponse<Favourite> api = new ApiResponse<>(200, "success", null);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/total-item")
    public ResponseEntity totalItem() {
        int id = token.getIdfromToken(); // Lấy id từ token
        int total = favouriteService.totalItemFromFavourite(id);
        ApiResponse<Integer> api = new ApiResponse<>(200, "success", total);
        return ResponseEntity.ok(api);
    }

    @DeleteMapping("/delete-all-favourite")
    public ResponseEntity deleteAllFavourite() {
        int id = token.getIdfromToken(); // Lấy id từ token
        favouriteService.DeleteAllFavourites(id);
        ApiResponse<Integer> api = new ApiResponse<>(200, "success", null);
        return ResponseEntity.ok(api);
    }

    @GetMapping("/is-product-favourite/{productId}")
    public ResponseEntity isProductFavourite(@PathVariable int productId) {
        int id = token.getIdfromToken(); // Lấy id từ token
        boolean isFavourite = favouriteService.isProductInFavourite(id, productId);
        ApiResponse<Boolean> api = new ApiResponse<>(200, "success", isFavourite);
        return ResponseEntity.ok(api);
    }

    @DeleteMapping("/delete-details/{productid}")
    public ResponseEntity deleteDetails(@PathVariable int productid) {
        int id = token.getIdfromToken(); // Lấy id từ token
        boolean delete = favouriteService.DeleteFromDetails(productid, id);
        ApiResponse<Boolean> api = new ApiResponse<>(200, "success", delete);
        return ResponseEntity.ok(api);
    }
}