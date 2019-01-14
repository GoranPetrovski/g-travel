package com.spring5.webflux.demo.models;


import com.spring5.webflux.demo.helpers.BaseId;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Travel {

    private String id;

    private BaseId fromLocation; // cityId (Skopje)

    private BaseId toDestination; // cityId (Probishtip)

    private LocalDate date;

    private BaseId user; //userId

    private Type type = Type.TRAVELER;

    private Integer seats;

    private BaseId car; // carId

    public enum Type {
        DRIVER,
        TRAVELER
    }
}
