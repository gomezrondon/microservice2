package com.gomezrondon.carservice.repositories;

import com.gomezrondon.carservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
