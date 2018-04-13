package com.example.alireza.myapplication.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "tblProduct")

public class Product extends Model {
//    @Column(name = "product_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    private int productID;


    @Column(name = "prod_name" )

    private String productName;

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    @Column(name = "prod_desc")
    private String productDescription;

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    @Column(name = "is_bookmark")
    private boolean isBookmark;

    public boolean getBookmark() {
        return isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    @Column(name = "Category")
    public Category category;
}
