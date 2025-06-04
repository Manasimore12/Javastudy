package com.code.SellCar_Spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.SellCar_Spring.entities.Bid;
import com.code.SellCar_Spring.entities.User;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long>{

	List<Bid> findAllByUserId(Long userId);

	List<Bid> findAllByCarId(Long carId);

}
