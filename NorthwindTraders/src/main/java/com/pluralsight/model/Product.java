package com.pluralsight.model;

public class Product {
    private int productId;
    private String productName;
    private double unitPrice;
    private int unitsInStock;

    public Product(int productId, String productName, double unitPrice, int unitsInStock) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }
}