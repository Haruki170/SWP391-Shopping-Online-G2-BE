package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Favourite;

public interface IFavouriteRepository {
    public Favourite findFavouriteById(int id);
    public boolean addFavourite(int userId);
    public Favourite getItemInFavourite(Favourite favourite);
    public boolean insertFavourite(int id);
    public boolean existsByProductIdAndCustomerId(int productId, int wishlistid);
    public boolean deleteFavouriteFromDetails(int id, int wishlistid);
}
