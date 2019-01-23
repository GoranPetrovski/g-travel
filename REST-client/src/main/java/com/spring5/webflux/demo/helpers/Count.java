package com.spring5.webflux.demo.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@AllArgsConstructor
public class Count implements Serializable {
    private long count;
}
