package com.example.demo.repository;

import com.example.demo.entity.Favourite;
import com.example.demo.mapper.FavouriteMapper;
import com.example.demo.repository.repositoryInterface.IFavouriteItemRepository;
import com.example.demo.repository.repositoryInterface.IFavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FavouriteRepository extends AbstractRepository<Favourite> implements IFavouriteRepository {
    @Autowired
    IFavouriteItemRepository IFavouriterepository;

    //hàm lấy wishlist từ customer id
    @Override
    public Favourite findFavouriteById(int id) {
        String sql = "select * from wishlist where customer_id = ?";
        Favourite favourite = super.findOne(sql, new FavouriteMapper(), id);
        return favourite;
    }

    @Override
    public boolean addFavourite(int userId) {
        return false;
    }

    //hàm lấy ra các favourite items
    @Override
    public Favourite getItemInFavourite(Favourite favourite) {
        //lấy ra các favourite item
        favourite.setFavourite_items(IFavouriterepository.getFavouriteItems(favourite.getId()));
//        System.out.println(favourite.getFavourite_items());
        return favourite;
    }

    // hàm insert vào bảng wishlist
    @Override
    public boolean insertFavourite(int id) {
        String sql = "insert into wishlist (customer_id) values (?)";
        boolean check = super.save(sql, id);

        return true;
    }

    //check xem sản phẩm có tồn tại không
    @Override
    public boolean existsByProductIdAndCustomerId(int productId, int wishlistid) {
        String sql = "SELECT COUNT(*) > 0 FROM wishlist_item WHERE product_id = ? AND wishlist_id = ?";
        //đếm xem sản phẩm tồn tại không
        int check = super.selectCount(sql, productId, wishlistid);
        //nếu tồn tại > 0 (tức là check = 1)
        if (check > 0) {
            return true;
            //ngược lại
        } else {
            return false;
        }

    }

    //xóa sản phầm dựa vào product id và wishlist id
    @Override
    public boolean deleteFavouriteFromDetails(int id, int wishlistid) {
        String sql = "DELETE FROM wishlist_item WHERE product_id = ? AND wishlist_id = ?";
        boolean check = super.delete(sql, id, wishlistid);
        return check;
    }
}
