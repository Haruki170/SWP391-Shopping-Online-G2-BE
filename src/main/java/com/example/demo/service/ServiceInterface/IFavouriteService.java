package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;

public interface IFavouriteService {
    public Favourite getFavourite(int id);
    public boolean addFavourite(Favourite_item favourite_item, int id);
    public boolean deleteFavourite(Favourite_item favourite_item);
    public int totalItemFromFavourite(int id);
    public boolean DeleteAllFavourites(int id);
    public boolean isProductInFavourite(int customer_id, int product_id);
    public boolean DeleteFromDetails(int id, int customer_id);
}
