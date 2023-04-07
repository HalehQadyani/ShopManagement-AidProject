package com.example.shop;

import java.sql.Date;

public class productData {

    private Integer productId;
    private String brand;
    private String productType;
    private String country;
    private Date date;
    private Double price;
    private String image;

    public productData(Integer productId, String brand, String productType, String country
            , Date date, Double price, String image){
        this.productId = productId;
        this.brand = brand;
        this.productType = productType;
        this.country = country;
        this.date = date;
        this.price = price;
        this.image = image;
    }
    public Integer getproductId(){
        return productId;
    }
    public String getBrand(){
        return brand;
    }
    public String getproductType(){
        return productType;
    }
    public String getcountry(){
        return country;
    }
    public Date getDate(){
        return date;
    }
    public Double getPrice(){
        return price;
    }
    public String getImage(){
        return image;
    }

}