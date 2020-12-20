package com.zziri.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.domain.Comment;
import com.zziri.comment.controller.dto.CommentDto;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
class CommentControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentController commentController;

    @Autowired
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        commentService.clear();
    }

    @Test
    void postComment() throws Exception {
        CommentDto input = CommentDto.of(LocalDateTime.now(), "postman", "comment.zziri.com", "posted by postman", "1111");

        String commentJson = toJson(input);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value(input.getAuthor()))
                .andExpect(jsonPath("$.url").value(input.getUrl()))
                .andExpect(jsonPath("$.content").value(input.getContent()));

        Comment result = commentService.getCommentsAll().get(0);

        assertThat(result.getAuthor()).isEqualTo(input.getAuthor());
        assertThat(result.getUrl()).isEqualTo(input.getUrl());
        assertThat(result.getContent()).isEqualTo(input.getContent());
    }

    @Test
    void getComments() throws Exception {
        Comment input = new Comment(
                "jihoon",
                "www.naver.com",
                "get comment by url test!",
                "1111");

        commentService.put(input);

        LocalDateTime time = LocalDateTime.of(input.getDate().getYear(), input.getDate().getMonth(), input.getDate().getDay(),
                input.getDate().getHour(), input.getDate().getMin(), input.getDate().getSec());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/comment")
                        .param("url", "www.naver.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("jihoon"))
                .andExpect(jsonPath("$[0].content").value("get comment by url test!"))
                .andExpect(jsonPath("$[0].url").value("www.naver.com"))
                .andExpect(jsonPath("$[0].date.year").value(time.getYear()))
                .andExpect(jsonPath("$[0].date.month").value(time.getMonthValue()))
                .andExpect(jsonPath("$[0].date.day").value(time.getDayOfMonth()))
                .andExpect(jsonPath("$[0].date.hour").value(time.getHour()))
                .andExpect(jsonPath("$[0].date.min").value(time.getMinute()))
                .andExpect(jsonPath("$[0].date.sec").value(time.getSecond()));
    }

    @Test
    void modifyComment() throws Exception {
        Comment comment = new Comment(
                "jihoon",
                "zziri.com",
                "origin",
                "1111"
        );

        commentService.put(comment);

        Long id = commentService.getCommentsAll().get(0).getId();
        String content = "modified";

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/comment")
                        .param("id", id.toString())
                        .param("content", content)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("jihoon"))
                .andExpect(jsonPath("$.url").value("zziri.com"))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void deleteComment() throws Exception {
        Comment origin = new Comment(
                "author",
                "this is url",
                "will be delete",
                "1111"
        );

        commentService.put(origin);

        Comment target = commentService.getCommentsAll().get(0);

        DeleteDto deleteDto = DeleteDto.of(target.getId(), target.getPassword());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(deleteDto))
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private String toJson(CommentDto commentDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentDto);
    }

    private String toJson(DeleteDto deleteDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(deleteDto);
    }
}
