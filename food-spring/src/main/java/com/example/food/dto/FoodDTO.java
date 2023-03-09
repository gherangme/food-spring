package com.example.food.dto;

import java.io.Serializable;

public class FoodDTO implements Serializable {

    private String image;
    private String name;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
