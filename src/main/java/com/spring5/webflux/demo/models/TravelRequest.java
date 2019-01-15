package com.spring5.webflux.demo.models;

import com.spring5.webflux.demo.helpers.BaseId;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelRequest {

    private BaseId travel;

    private Integer requiredSeats = 1; // default 1

    private Boolean hasLuggage;
}
