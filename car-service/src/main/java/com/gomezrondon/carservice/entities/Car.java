package com.gomezrondon.carservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;

    public Car(String name) {
        this.name = name;
    }

}
