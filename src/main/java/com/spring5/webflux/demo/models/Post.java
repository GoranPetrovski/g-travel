package com.spring5.webflux.demo.models;

import com.spring5.webflux.demo.helpers.BaseId;
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
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    @Id
    private String id;

    private BaseId travel;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private Status status = DRAFT;

    private LocalDateTime createdDate = LocalDateTime.now();

    public enum Status {
        DRAFT,
        PUBLISHED
    }
}
