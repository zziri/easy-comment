package com.zziri.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zziri.comment.controller.dto.CommentDto;
import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.controller.dto.PostDto;
import com.zziri.comment.domain.Comment;
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
        Comment comment = getComment();
        PostDto postDto = PostDto.of(comment.getAuthor(), comment.getPassword(), comment.getContent());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(postDto))
                .param("url", comment.getUrl()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.author").value(postDto.getAuthor()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));
    }

    @Test
    void getComments() throws Exception {
        Comment input = getComment();
        commentService.put(input);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/comment")
                        .param("url", input.getUrl()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].author").value(input.getAuthor()))
                .andExpect(jsonPath("$.[0].content").value(input.getContent()))
                .andExpect(jsonPath("$.[0].url").value(input.getUrl()));
    }

    @Test
    void modifyComment() throws Exception {
        Comment comment = getComment();

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
        Comment origin = getComment();

        commentService.put(origin);

        Comment target = commentService.getCommentsAll().get(0);

        DeleteDto deleteDto = DeleteDto.of(target.getId(), target.getPassword());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(deleteDto))
                        .param("url", target.getUrl())
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private String toJson(CommentDto dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    private String toJson(DeleteDto dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    private String toJson(PostDto dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    private Comment getComment() {
        return new Comment("author", "comment.zziri.com", "Hello World", "asdlfjzocvklsdjfi12312390sdf");
    }
}
