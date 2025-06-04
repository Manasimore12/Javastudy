package com.code.SellCar_Spring.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.SellCar_Spring.dto.BidDTO;
import com.code.SellCar_Spring.dto.CarDTO;
import com.code.SellCar_Spring.dto.SearchCarDTO;
import com.code.SellCar_Spring.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {
	private final CustomerService customerService;
	
	@PostMapping("/car")
	public ResponseEntity<?> addCar(@ModelAttribute CarDTO carDTO) throws IOException{
		boolean success = customerService.createCar(carDTO);
		if (success) return ResponseEntity.status(HttpStatus.CREATED).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/cars")
	public ResponseEntity<List<CarDTO>> getAllCars(){
		return ResponseEntity.ok(customerService.getAllCars());
		
	}
	@GetMapping("/car/{id}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable Long id){
		return ResponseEntity.ok(customerService.getCarById(id));
		
	}
	@DeleteMapping("/car/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id){
		customerService.deleteCar(id);
		return ResponseEntity.ok(null);
		
	}
	
	@PutMapping("/car/{id}")
	public ResponseEntity<?> updateCar(@PathVariable Long id,@ModelAttribute CarDTO carDTO) throws IOException{
		boolean success = customerService.updateCar(id,carDTO);
		if (success) return ResponseEntity.status(HttpStatus.OK).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	@PostMapping("/car/search")
	public ResponseEntity<List<CarDTO>> searchCar(@RequestBody SearchCarDTO searchCarDTO){
		return ResponseEntity.ok(customerService.searchCar(searchCarDTO));
		
	}
	@GetMapping("/my-cars/{userId}")
	public ResponseEntity<List<CarDTO>> getMyCars(@PathVariable Long userId){
		return ResponseEntity.ok(customerService.getMyCars(userId));
		
	}
	
	@PostMapping("/car/bid")
	public ResponseEntity<?> bidACar(@RequestBody BidDTO bidDTO) throws IOException{
		boolean success = customerService.bidACar(bidDTO);
		if (success) return ResponseEntity.status(HttpStatus.CREATED).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/car/bids/{userId}")
	public ResponseEntity<List<BidDTO>> getBidsByUserId(@PathVariable Long userId){
		return ResponseEntity.ok(customerService.getBidsByUserId(userId));
		
	}
	
	@GetMapping("/car/{carId}/bids")
	public ResponseEntity<List<BidDTO>> getBidsByCarId(@PathVariable Long carId){
		return ResponseEntity.ok(customerService.getBidsByCarId(carId));
		
	}
	@GetMapping("/car/bid/{bidId}/{status}")
	public ResponseEntity<?> changeBidStatus(@PathVariable Long bidId, @PathVariable String status){
		boolean success= customerService.changeBidStatus(bidId,status);
		if (success) return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
		
		
	}
	@GetMapping("/car/analytics/{userId}")
	public ResponseEntity<?> getAnalytics(@PathVariable Long userId){
		return ResponseEntity.ok(customerService.getAnalytics(userId));
		
	}
}
