package com.spring5.webflux.demo.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.spring5.webflux.demo.models.Post.Status.DRAFT;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Status status = DRAFT;

    private LocalDateTime createdDate = LocalDateTime.now();

    enum Status {
        DRAFT,
        PUBLISHED
    }
}
