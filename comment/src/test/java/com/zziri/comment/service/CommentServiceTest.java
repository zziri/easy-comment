package com.zziri.comment.service;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.CommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @BeforeEach
    void beforeEach() {
        commentService.clear();
    }

    @Test
    void put() {
        commentService.put(CommentDto.of(LocalDateTime.now(), "jihoon", "comment.zziri.com", "Hello!@#!#@"));
        List<Comment> commentList = commentService.getCommentsAll();
        assertThat(commentList.size()).isEqualTo(1);
        assertThat(commentList.get(0).getAuthor()).isEqualTo("jihoon");
    }

}