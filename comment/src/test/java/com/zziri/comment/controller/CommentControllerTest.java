package com.zziri.comment.controller;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.Date;
import com.zziri.comment.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class CommentControllerTest {
    @Autowired
    private CommentController commentController;

    @Autowired
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void postComment() throws Exception {
        Comment input = new Comment(
                Date.of(LocalDateTime.now()),
                "postman",
                "comment.zziri.com",
                "posted by postman");

        String commentJson = String.format("{\n" +
                "\t\"date\":\"%s\",\n" +
                "\t\"author\":\"%s\",\n" +
                "\t\"url\":\"%s\",\n" +
                "\t\"content\":\"%s\"\n" +
                "}\n", input.getDate(), input.getAuthor(), input.getUrl(), input.getContent());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andDo(print())
                .andExpect(status().isCreated());

        Comment result = commentService.getCommentsAll().get(0);

        assertThat(result.getAuthor()).isEqualTo(input.getAuthor());
        assertThat(result.getUrl()).isEqualTo(input.getUrl());
        assertThat(result.getContent()).isEqualTo(input.getContent());
        assertThat(result.getDate()).isEqualTo(input.getDate());
    }

}