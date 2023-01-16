package com.example.application.data.entity;

import javax.persistence.Entity;

@Entity
public class Region extends AbstractEntity {
    private String name;

    public Region() {

    }

    public Region(String name) {
        this.name = name;
    }
}
