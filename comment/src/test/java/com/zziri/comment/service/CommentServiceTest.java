package com.zziri.comment.service;

import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.controller.dto.PostDto;
import com.zziri.comment.domain.Comment;
import com.zziri.comment.controller.dto.CommentDto;
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
class CommentServiceTest {
    @Autowired
    CommentService commentService;

    @BeforeEach
    void beforeEach() {
        commentService.clear();
    }

    @Test
    void put() {
        commentService.put(PostDto.of("jihoon", "aododfjsk123123", "Hello World"), "conment.zziri.com");
        List<Comment> comments = commentService.getCommentsAll();
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getAuthor()).isEqualTo("jihoon");
    }

    @Test
    void deleted() {
        commentService.put(new Comment("author", "this is url", "hello world", "1111"));
        Comment comment = commentService.getCommentsAll().get(0);
        commentService.delete(DeleteDto.of(comment.getId(), comment.getPassword()), comment.getUrl());
        assertThat(commentService.getCommentById(comment.getId())).isEqualTo(null);
        List<Comment> result = commentService.getCommentsByUrl("this is url");
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void modify() {
        Comment comment = new Comment("author", "url", "content", "1111");
        commentService.put(comment);
        Long id = commentService.getCommentsAll().get(0).getId();
        commentService.modify(id, "modified content");

        Comment modified = commentService.getCommentById(id);
        assertThat(modified.getContent()).isEqualTo("modified content");
    }

    @Test
    void getDeleted() {
        Comment origin = new Comment(
                "author",
                "url",
                "content",
                "1111"
        );

        commentService.put(origin);
        Comment target = commentService.getCommentsAll().get(0);

        commentService.delete(DeleteDto.of(target.getId(), target.getPassword()), target.getUrl());

        Comment comment = commentService.getCommentById(target.getId());
        assertThat(comment).isEqualTo(null);
    }
}