package com.spring5.webflux.demo.models;

import com.spring5.webflux.demo.helpers.BaseId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    private String id;

    private BaseId user;

    private String brand;

    private String model;

    private String registration;

    private Integer numOfSeats;
}
