package com.code.SellCar_Spring.entities;

import java.time.OffsetDateTime;
import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.code.SellCar_Spring.dto.CarDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private String type;
    private String transmission;
    private String color;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime year;

    private Boolean sold;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public CarDTO getCarDTO() {
        CarDTO carDto = new CarDTO();
        carDto.setId(id); // Include ID in response
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setColor(color);
        carDto.setYear(year);
        carDto.setSold(sold);
        carDto.setDescription(description);
        carDto.setPrice(price);
        carDto.setReturnedImg(img);
        carDto.setUserId(this.user != null ? this.user.getId() : null); // Safe check for user
        return carDto;
    }
}
