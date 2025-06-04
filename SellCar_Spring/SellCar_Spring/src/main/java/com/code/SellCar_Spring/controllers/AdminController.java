package com.code.SellCar_Spring.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.SellCar_Spring.dto.BidDTO;
import com.code.SellCar_Spring.dto.CarDTO;
import com.code.SellCar_Spring.dto.SearchCarDTO;
import com.code.SellCar_Spring.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {
	
	private final AdminService adminService;
	@GetMapping("/cars")
	public ResponseEntity<List<CarDTO>> getAllCars(){
		return ResponseEntity.ok(adminService.getAllCars());
		
	}
	@GetMapping("/car/{id}")
	public ResponseEntity<CarDTO> getCarById(@PathVariable Long id){
		return ResponseEntity.ok(adminService.getCarById(id));
		
	}
	
	@DeleteMapping("/car/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id){
		adminService.deleteCar(id);
		return ResponseEntity.ok(null);
		
	}
	@PostMapping("/car/search")
	public ResponseEntity<List<CarDTO>> searchCar(@RequestBody SearchCarDTO searchCarDTO){
		return ResponseEntity.ok(adminService.searchCar(searchCarDTO));
		
	}
	
	@GetMapping("/car/bids")
	public ResponseEntity<List<BidDTO>> getBids(){
		return ResponseEntity.ok(adminService.getBids());
		
	}
	@GetMapping("/car/bid/{bidId}/{status}")
	public ResponseEntity<?> changeBidStatus(@PathVariable Long bidId, @PathVariable String status){
		boolean success= adminService.changeBidStatus(bidId,status);
		if (success) return ResponseEntity.ok().build();
		return ResponseEntity.notFound().build();
		
		
	}
	
}
