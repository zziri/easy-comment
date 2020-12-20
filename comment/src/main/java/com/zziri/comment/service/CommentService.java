package com.zziri.comment.service;

import com.zziri.comment.controller.dto.*;
import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.Date;
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

    public ResponseDto put(PostDto postDto, String url) {
        url = url
                .replace("https://", "")
                .replace("http://", "");

        Comment comment = new Comment(Date.of(LocalDateTime.now()), postDto.getAuthor(), url, postDto.getContent(), postDto.getPassword());

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

    public ResponseDto modify(PatchDto patchDto, String url) {
        Comment comment = commentRepository.findByIdAndPasswordAndUrl(patchDto.getId(), patchDto.getPassword(), url);

        comment.setContent(patchDto.getContent());
        comment.setDate(Date.of(LocalDateTime.now()));

        return commentRepository.save(comment).toResponseDto();
    }

    public ResponseDto delete(DeleteDto deleteDto, String url) {
        Comment comment = commentRepository.findByIdAndPasswordAndUrl(deleteDto.getId(), deleteDto.getPassword(), url);
        comment.setDeleted(true);
        commentRepository.save(comment);
        return null;
    }
}
