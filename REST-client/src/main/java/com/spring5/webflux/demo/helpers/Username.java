package com.spring5.webflux.demo.helpers;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Username implements Serializable {
    private String username;
}
