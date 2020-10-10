package com.zziri.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CommentDto {
    private LocalDateTime date;
    private String author;
    private String url;
    private String content;
}
