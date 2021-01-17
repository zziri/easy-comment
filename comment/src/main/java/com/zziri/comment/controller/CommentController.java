package com.zziri.comment.controller;

import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.controller.dto.PatchDto;
import com.zziri.comment.controller.dto.PostDto;
import com.zziri.comment.controller.dto.ResponseDto;
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
        return commentService.put(postDto.getAuthor(), postDto.getPassword(), postDto.getContent(), url);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseDto> get(@RequestParam("url") String url) {
        return commentService.getCommentsByUrl(url);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto patch(@RequestBody PatchDto dto, @RequestParam("url") String url) {
        return commentService.modify(dto.getId(), dto.getPassword(), dto.getContent(), url);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseDto delete(@RequestBody DeleteDto deleteDto, @RequestParam("url") String url) {
        return commentService.delete(deleteDto.getId(), deleteDto.getPassword(), url);
    }
}
