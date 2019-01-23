package com.spring5.webflux.demo.models;


import com.spring5.webflux.demo.helpers.BaseId;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Travel {

    private String id;

    private Boolean isPreciseTime;

    private LocalTime fromTime;

    private LocalDate toTime;

    private BaseId fromLocation; // cityId (Skopje)

    private BaseId toDestination; // cityId (Probishtip)

    private LocalDate date;

    private BaseId user; //userId

    private Type type = Type.TRAVELER; // default

    public enum Type {
        DRIVER,
        TRAVELER
    }
}
