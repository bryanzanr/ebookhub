package com.herokuapp.ebookhub.category.entities;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long no;

    @Column(name="sub_category")
    private String subCategory;
    @Column
    private String category;

    public Long getNo() {
        return this.no;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public String getCategory() {
        return this.category;
    }

}