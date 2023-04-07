package com.example.shop;

import java.sql.Date;

public class customerData {

    private Integer customerId;
    private Integer productId;
    private String brand;
    private String productType;
    private String country;
    private Integer quantity;
    private Double price;
    private Date date;

    public customerData(Integer customerId, Integer productId, String brand, String productType
            , String country, Integer quantity, Double price, Date date){
        this.customerId = customerId;
        this.productId = productId;
        this.brand = brand;
        this.productType = productType;
        this.country = country;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
    public Integer getcustomerId(){
        return customerId;
    }
    public Integer getproductId(){
        return productId;
    }
    public String getbrand(){
        return brand;
    }
    public String getproductType(){
        return productType;
    }
    public String getcountry(){
        return country;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public Double getPrice(){
        return price;
    }
    public Date getDate(){
        return date;
    }

}
