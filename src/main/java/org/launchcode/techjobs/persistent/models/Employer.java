package org.launchcode.techjobs.persistent.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class Employer extends AbstractEntity {

    @NotBlank(message = "Please enter a location.")
    @Size(max = 120, message = "Location must be under 120 characters.")
    private String location;

    public Employer(String location) {
        this.location = location;
    }

    public Employer() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
