package com.zziri.comment.service;

import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.controller.dto.PatchDto;
import com.zziri.comment.controller.dto.PostDto;
import com.zziri.comment.controller.dto.ResponseDto;
import com.zziri.comment.domain.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        PostDto dto = PostDto.of("jihoon", "aododfjsk123123", "Hello World");
        commentService.put(dto.getAuthor(), dto.getPassword(), dto.getContent(), "conment.zziri.com");
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
        List<ResponseDto> result = commentService.getCommentsByUrl("this is url");
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void modify() {
        commentService.put(new Comment("author", "this is url", "hello world", "1111"));
        Comment target = commentService.getCommentsAll().get(0);
        commentService.modify(PatchDto.of(target.getId(), target.getPassword(), "modified content"), target.getUrl());
        Comment modified = commentService.getCommentById(target.getId());
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