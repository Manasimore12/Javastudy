package com.code.SellCar_Spring.dto;

import lombok.Data;

@Data
public class SearchCarDTO {
	private String brand;
	private String type;
	private String color;
	private String transmission;
}
