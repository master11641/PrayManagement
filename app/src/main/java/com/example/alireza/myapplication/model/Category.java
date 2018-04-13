package com.example.alireza.myapplication.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;


@Table(name = "Categories")
public class Category extends Model {

    @Column(name = "Name")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    // This method is optional, does not affect the foreign key creation.
    public List<Product> products() {
        return getMany(Product.class, "Category");
    }
    @Column(name = "ImgSrc")
    private String imgSrc;

    public String getImgSrc() {
        return imgSrc;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
