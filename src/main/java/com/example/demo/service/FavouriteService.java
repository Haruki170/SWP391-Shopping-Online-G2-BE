package com.example.demo.service;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;
import com.example.demo.repository.repositoryInterface.IFavouriteItemRepository;
import com.example.demo.repository.repositoryInterface.IFavouriteRepository;
import com.example.demo.service.ServiceInterface.IFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService implements IFavouriteService {
    @Autowired
    IFavouriteRepository favouriteRepository;

    @Autowired
    IFavouriteItemRepository favouriteItemRepository;

    @Override
    public Favourite getFavourite(int id) {
        //lấy wishlist_id bằng customer_id
        Favourite favourite = favouriteRepository.findFavouriteById(id);

        favourite = favouriteRepository.getItemInFavourite(favourite);
        return favourite;
    }

    //hàm add favourite
    @Override
    public boolean addFavourite(Favourite_item favourite_item, int id) {
        //tìm wishlist_id bằng customer id
        Favourite fav = favouriteRepository.findFavouriteById(id);
        //nếu k tồn tại
        if(fav == null) {
            //tạo bảng ghi mới cho wishlist
            boolean insert = favouriteRepository.insertFavourite(id);
            //nếu insert thành công
            if(insert) {
                //tìm lại wishlist_id
                fav = favouriteRepository.findFavouriteById(id);
                //insert favourite vô
                favouriteItemRepository.insertNewFavouriteItem(fav.getId(),favourite_item);
            }
            return true;
            // nếu wishlist tồn tại rồi
        } else {
            //lấy ra danh sách fav items
            List<Favourite_item> items = favouriteItemRepository.getFavouriteItems(fav.getId());
            //lặp qua các danh sách fav items
            for(Favourite_item item : items) {
                //nếu sản phầm tồn tại thì không add
                if(item.getProduct().getId() == favourite_item.getProduct().getId()) {
                    return false;
                }
            }
            //nếu sản phầm chưa tồn tại thì cho vào
            favouriteItemRepository.insertNewFavouriteItem(fav.getId(),favourite_item);
            return true;
        }

    }

    //xóa sản phẩm yêu thích
    @Override
    public boolean deleteFavourite(Favourite_item favourite_item) {
//        System.out.println("favourite id: " + favourite_item.getId());
        favouriteItemRepository.deleteFavouriteItem(favourite_item.getId());

        return true;
    }

    //lấy tất cả các sản phẩm để hiện tim
    @Override
    public int totalItemFromFavourite(int id) {
        //lấy wishlist_id
        Favourite favourite = favouriteRepository.findFavouriteById(id);
        int total = favouriteItemRepository.getTotalItemFromFavourite(favourite.getId());
        return total;
    }

    // hàm để xóa
    @Override
    public boolean DeleteAllFavourites(int id) {
        //lấy wishlist id
        Favourite fav = favouriteRepository.findFavouriteById(id);
        favouriteItemRepository.deleteAllFavouriteItems(fav.getId());
        return true;
    }

    //xem sản phầm tồn tại hay k
    @Override
    public boolean isProductInFavourite(int customer_id, int product_id) {
        //lấy wishlist_id
        Favourite fav = favouriteRepository.findFavouriteById(customer_id);
        return favouriteRepository.existsByProductIdAndCustomerId(product_id, fav.getId());
    }


    //xóa sản phầm ở details
    @Override
    public boolean DeleteFromDetails(int id, int customer_id) {
        //lấy wishlist_id
        Favourite fav = favouriteRepository.findFavouriteById(customer_id);
        return favouriteRepository.deleteFavouriteFromDetails(id, fav.getId());
    }
}
