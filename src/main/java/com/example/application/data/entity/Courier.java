package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Courier extends AbstractEntity {
    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @NotNull
    @ManyToOne
    private Region region;

}
