package com.spring5.webflux.demo.models;

import com.spring5.webflux.demo.helpers.BaseId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelOffer {

    @Id
    private String id;

    private BaseId travel;

    private Integer freeSeats;

    private BaseId car; // carId

    private Double price;
}
