package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;

import java.util.List;

public interface IFavouriteItemRepository {
    public List<Favourite_item> getFavouriteItems(int favouriteId);
    public boolean insertNewFavouriteItem(int id, Favourite_item favouriteItem);
    public boolean deleteFavouriteItem(int favourite_itemid);
    public int getTotalItemFromFavourite(int id);
    public boolean deleteAllFavouriteItems(int wishlist_id);
}
