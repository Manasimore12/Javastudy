package com.code.SellCar_Spring.dto;

import com.code.SellCar_Spring.enums.UserRole;

import lombok.*;

@Data
public class UserDTO {
	private Long id;
	private String name;
	private String email;
	private UserRole userRole;
	
	
}
