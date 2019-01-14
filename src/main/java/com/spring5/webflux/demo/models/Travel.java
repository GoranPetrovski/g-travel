package com.spring5.webflux.demo.models;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Travel {

    private String id;

    private City fromLocation; //Skopje

    private City toDestination; //Probishtip

    private LocalDate date;

    private User user;

    private Type type = Type.TRAVELER;

    private Integer seats;

    private Car car;

    public enum Type {
        DRIVER,
        TRAVELER
    }
}
