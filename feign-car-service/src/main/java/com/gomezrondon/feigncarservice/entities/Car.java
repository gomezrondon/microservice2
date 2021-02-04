package com.gomezrondon.feigncarservice.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
public class Car {

    private Long id;
    @NonNull
    private String name;

    public Car(String name) {
        this.name = name;
    }

}
