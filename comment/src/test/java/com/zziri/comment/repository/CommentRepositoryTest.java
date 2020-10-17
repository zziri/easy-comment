package com.zziri.comment.repository;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.Date;
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
        commentRepository.deleteAll();
        commentRepository.save(new Comment(Date.of(LocalDateTime.now()), "jihoon", "zziri.service.comment", "hello hyewon"));
        commentRepository.save(new Comment(Date.of(LocalDateTime.now()), "hyewon", "zziri.service.comment", "hello jihoon"));
    }

    @Test
    void findByUrl() {
        List<Comment> comments = commentRepository.findByUrl("zziri.service.comment");

        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments.get(0).getAuthor()).isEqualTo("jihoon");
    }

    @Test
    void modify() {
        Comment firstComment = commentRepository.findAll().get(0);
        Long id = firstComment.getId();
        String content = firstComment.getContent() + " modified";

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("id가 %d인 comment가 존재하지 않습니다", id))
        );

        comment.setContent(content);
        commentRepository.save(comment);

        Comment result = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("id가 %d인 comment가 존재하지 않습니다", id))
        );

        assertThat(result.getContent()).isEqualTo(content);
        System.out.println(String.format("[jihoon] %s", result.getContent()));
    }
}
