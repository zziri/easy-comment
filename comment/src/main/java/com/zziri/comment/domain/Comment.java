package com.zziri.comment.domain;

import com.zziri.comment.domain.dto.CommentDto;
import com.zziri.comment.domain.dto.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Where(clause = "deleted = false")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @Embedded
    private Date date;

    private String author;

    private String url;

    private String content;

    private String password;

    public Comment(@Valid Date date, String author, String url, String content) {
        this.date = date;
        this.author = author;
        this.url = url;
        this.content = content;
        this.deleted = false;
    }

    public Comment(String author, String url, String content) {
        this.author = author;
        this.url = url;
        this.content = content;
        this.deleted = false;
    }

    @ColumnDefault("0")
    private boolean deleted;

    static public Comment fromDto(CommentDto commentDto) {
        return new Comment(Date.of(commentDto.getDate()), commentDto.getAuthor(), commentDto.getUrl(), commentDto.getContent());
    }
}
