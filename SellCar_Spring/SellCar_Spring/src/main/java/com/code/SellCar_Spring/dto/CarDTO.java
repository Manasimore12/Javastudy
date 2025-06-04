package com.code.SellCar_Spring.dto;



import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.Date;


@Data
public class CarDTO {
	private Long id;
	private String name;
	private String brand;
	private String type;
	private String transmission;
	private String color;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private OffsetDateTime year;
	private Boolean sold;
	private String description;
	private Long price;
	private Long userId;
	private MultipartFile img;
	private byte[] returnedImg;
	
	
}
