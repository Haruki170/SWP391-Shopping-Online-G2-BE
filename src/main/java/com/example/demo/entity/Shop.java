package com.example.demo.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Shop extends General{
    private int id;
    private String name;
    private String description;
    private String logo;
    private int status;
    private int autoShipCost;
    private String taxNumber;
    private List<ShopAddress> shopAddresses;
    private List<ShopPhone> shopPhones;
    private List<ShipCost> shipCosts;
    private List<Product> products;
    private double rating;
    private int totalFeedback;
    private ShopOwner shopOwner;
}
