package com.zziri.comment.controller;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.CommentDto;
import com.zziri.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/comment")
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment post(@RequestBody CommentDto commentDto) {
        return commentService.put(commentDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> get(String url) {
        return commentService.getCommentsByUrl(url);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Comment patch(@RequestParam("id") Long id, @RequestParam(value = "content") String content) {
        return commentService.modify(id, content);
    }
}
