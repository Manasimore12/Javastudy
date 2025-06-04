package com.code.SellCar_Spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.SellCar_Spring.entities.Car;
import com.code.SellCar_Spring.entities.User;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

	List<Car> findAllByUserId(Long userId);

	Long countByUserId(Long userId);

	Long countByUserIdAndSoldTrue(Long userId);
	
}
