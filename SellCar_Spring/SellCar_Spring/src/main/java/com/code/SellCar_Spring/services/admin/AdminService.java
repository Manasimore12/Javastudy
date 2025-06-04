package com.code.SellCar_Spring.services.admin;

import java.util.List;

import com.code.SellCar_Spring.dto.BidDTO;
import com.code.SellCar_Spring.dto.CarDTO;
import com.code.SellCar_Spring.dto.SearchCarDTO;

public interface AdminService {

	List<CarDTO> getAllCars();
	CarDTO getCarById(Long id);
	void deleteCar(Long id);
	List<CarDTO> searchCar(SearchCarDTO searchCarDTO);
	
	List<BidDTO> getBids();
	boolean changeBidStatus(Long bidId, String status);
}
