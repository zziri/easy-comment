package com.zziri.comment.repository;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.Date;
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

    @Test
    void crud() {
        Comment comment = new Comment();
        comment.setAuthor("jihoon");
        comment.setUrl("zziri.service.comment");
        comment.setDate(Date.of(LocalDateTime.now()));

        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findByUrl("zziri.service.comment");

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getAuthor()).isEqualTo("jihoon");
    }

}
