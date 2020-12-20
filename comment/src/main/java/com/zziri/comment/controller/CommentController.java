package com.zziri.comment.controller;

import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.controller.dto.PostDto;
import com.zziri.comment.controller.dto.ResponseDto;
import com.zziri.comment.domain.Comment;
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
    public ResponseDto post(@RequestBody PostDto postDto, @RequestParam("url") String url) {
        return commentService.put(postDto, url);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseDto> get(@RequestParam("url") String url) {
        return commentService.getCommentsByUrl(url);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public Comment patch(@RequestParam("id") Long id, @RequestParam(value = "content") String content) {
        return commentService.modify(id, content);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto delete(@RequestBody DeleteDto deleteDto, @RequestParam("url") String url) {
        return commentService.delete(deleteDto, url);
    }
}
