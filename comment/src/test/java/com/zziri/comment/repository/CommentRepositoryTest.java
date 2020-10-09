package com.zziri.comment.repository;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void before() {
        commentRepository.save(new Comment(Date.of(LocalDateTime.now()), "jihoon", "zziri.service.comment", "hello hyewon", false));
        commentRepository.save(new Comment(Date.of(LocalDateTime.now()), "hyewon", "zziri.service.comment", "hello jihoon", false));
    }

    @Test
    void crud() {
        List<Comment> comments = commentRepository.findByUrl("zziri.service.comment");

        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getAuthor()).isEqualTo("jihoon");
    }
}
