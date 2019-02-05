package com.kalpv.t_kart.Model;

public class Product {
    private String id;
    private String productName;
    private String productDescription;
    private String productOfferPrice;
    private String productPrice;

    public Product() {}

    public Product(String id, String productName, String productDescription, String productOfferPrice, String productPrice) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productOfferPrice = productOfferPrice;
        this.productPrice = productPrice;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getProductOfferPrice() {
        return "Rs. " + productOfferPrice;
    }

    public String getProductPrice() {
        return "Rs. " + productPrice;
    }
}
