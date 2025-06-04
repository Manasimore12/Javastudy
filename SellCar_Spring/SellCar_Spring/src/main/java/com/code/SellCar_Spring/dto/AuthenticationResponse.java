package com.code.SellCar_Spring.dto;

import com.code.SellCar_Spring.enums.UserRole;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String jwt;
	private Long userId;
	private UserRole userRole;
}
