package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Courier extends AbstractEntity {
    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @OneToMany
    @Nullable
    public List<Package> packages = new LinkedList<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(@Nullable List<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " id: " + getId();
    }
}
