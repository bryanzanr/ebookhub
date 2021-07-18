package com.herokuapp.ebookhub.category.entities;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long no;

    @Column
    private String sub_category;
    private String category;

    public Long getNo() {
        return this.no;
    }

    public String getSubCategory() {
        return this.sub_category;
    }

    public String getCategory() {
        return this.category;
    }

}