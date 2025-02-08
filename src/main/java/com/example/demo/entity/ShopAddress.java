package com.example.demo.entity;


import lombok.ToString;

@ToString
public class ShopAddress {
    private Long id;

    private String province;

    private String district;

    private String ward;


    private String addressDetail;
//    private Shop shop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

//    public Shop getShop() {
//        return shop;
//    }
//
//    public void setShop(Shop shop) {
//        this.shop = shop;
//    }
    public String getFullAddress() {
        return this.addressDetail + ", " + this.ward + ", " + this.district + ", " + this.province;
    }
}
