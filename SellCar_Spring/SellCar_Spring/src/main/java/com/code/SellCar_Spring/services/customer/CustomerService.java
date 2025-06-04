package com.code.SellCar_Spring.services.customer;

import java.io.IOException;
import java.util.List;

import com.code.SellCar_Spring.dto.AnalyticsDTO;
import com.code.SellCar_Spring.dto.BidDTO;
import com.code.SellCar_Spring.dto.CarDTO;
import com.code.SellCar_Spring.dto.SearchCarDTO;

public interface CustomerService {
	boolean createCar(CarDTO carDTO) throws IOException;
	
	
	List<CarDTO> getAllCars();


	CarDTO getCarById(Long id);


	void deleteCar(Long id);

	boolean updateCar(Long id,CarDTO carDTO) throws IOException;
	List<CarDTO> searchCar(SearchCarDTO searchCarDTO);
	List<CarDTO> getMyCars(Long userId);
	
	
	boolean bidACar(BidDTO bidDTO) throws IOException;
	
	List<BidDTO> getBidsByUserId(Long userId);
	
	List<BidDTO> getBidsByCarId(Long carId);
	boolean changeBidStatus(Long bidId, String status);
	
	AnalyticsDTO getAnalytics(Long userId);
	

	
}
