package com.spring5.webflux.demo.helpers;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PostId implements Serializable {
    private String id;
}
