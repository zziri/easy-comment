package com.zziri.comment.controller;

import com.zziri.comment.domain.dto.CommentDto;
import com.zziri.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void post(@RequestBody CommentDto commentDto) {
        commentService.put(commentDto);
    }
}
