package com.example.demo.repository;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;
import com.example.demo.mapper.FavouriteItemMapper;
import com.example.demo.repository.repositoryInterface.IFavouriteItemRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FavouriteItemRepository extends AbstractRepository<Favourite_item> implements IFavouriteItemRepository {
    @Override
    public List<Favourite_item> getFavouriteItems(int favouriteId) {
//        System.out.println("FAVOURITE ID CO NHAN DC HAY K A: "+favouriteId);
        String sql = "SELECT wi.wishlist_item_id, wi.wishlist_id, p.* FROM `wishlist_item` " +
                "as wi join product as p on wi.product_id = p.product_id " +
                "WHERE wishlist_id = ?";
        List<Favourite_item> favouriteItems = super.findAll(sql, new FavouriteItemMapper(), favouriteId);
//        System.out.println("CAC SAN PHAM YEU THICH: " + favouriteItems);
        return favouriteItems;
    }

    //hàm insert favourite item mới vào bảng wishlist_item
    @Override
    public boolean insertNewFavouriteItem(int id, Favourite_item favouriteItem) {
        int product_id = favouriteItem.getProduct().getId();
        String sql = "INSERT INTO `wishlist_item`(`wishlist_id`, `product_id`) VALUES (?,?)";
        super.save(sql,id,product_id);
        return true;
    }

    //xóa sản phẩm dựa vào favourite_item id
    @Override
    public boolean deleteFavouriteItem(int favourite_itemid) {
        String sql = "DELETE FROM `wishlist_item` WHERE wishlist_item_id = ?";
        super.save(sql, favourite_itemid);
        return true;
    }

    //Đếm ra các favourite item của người dùng (hiện tim)
    @Override
    public int getTotalItemFromFavourite(int id) {
        String sql = "select count(*) from `wishlist_item` where wishlist_id = ?";
        int total = super.selectCount(sql,id);
        return total;
    }

    //xóa hết các sản phẩm có wishlist_id = number
    @Override
    public boolean deleteAllFavouriteItems(int wishlist_id) {
        String sql = "delete from `wishlist_item` where wishlist_id = ?";
        super.save(sql,wishlist_id);
        return false;
    }




}
