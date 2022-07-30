package com.example.ppt06_2072029.model;

public class Items {
    private int id;
    private String name;
    private double price;
    private String description;
    private Category Category_id;

    public Items(int id, String name, double price, String description, Category Category_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.Category_id = Category_id;
    }

    public Items() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(Category category_id) {
        this.Category_id = category_id;
    }
}
