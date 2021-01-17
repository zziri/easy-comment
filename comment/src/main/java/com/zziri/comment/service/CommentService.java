package com.zziri.comment.service;

import com.zziri.comment.controller.dto.*;
import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.Date;
import com.zziri.comment.exception.ParameterException;
import com.zziri.comment.exception.CommentNotFoundException;
import com.zziri.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public ResponseDto put(String author, String password, String content, String url) {
        if (author.trim().isEmpty() || password.trim().isEmpty() || url.trim().isEmpty() || content.trim().isEmpty()) {
            throw new ParameterException();
        }

        url = url
                .replace("https://", "")
                .replace("http://", "");

        Comment comment = new Comment(Date.of(LocalDateTime.now()), author, url, content, password);

        return commentRepository.save(comment).toResponseDto();
    }

    public void put(Comment comment) {
        comment.setDate(Date.of(LocalDateTime.now()));
        commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        Comment ret = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("id가 %d인 comment가 존재하지 않습니다", id))
        );
        return ret.isDeleted() ? null : ret;
    }

    public List<ResponseDto> getCommentsByUrl(String url) {
        return commentRepository.findByUrl(url).stream().map(Comment::toResponseDto).collect(Collectors.toList());
    }

    public List<Comment> getCommentsAll() {
        return commentRepository.findAll();
    }

    public void clear() {
        commentRepository.deleteAll();
    }

    public ResponseDto modify(Long id, String password, String content, String url) {
        Comment comment = commentRepository.findByIdAndPasswordAndUrl(id, password, url);

        if (comment == null)
            throw new CommentNotFoundException();

        comment.setContent(content);
        comment.setDate(Date.of(LocalDateTime.now()));

        return commentRepository.save(comment).toResponseDto();
    }

    public ResponseDto delete(Long id, String password, String url) {
        Comment comment = commentRepository.findByIdAndPasswordAndUrl(id, password, url);
        comment.setDeleted(true);
        commentRepository.save(comment);
        return null;
    }
}
