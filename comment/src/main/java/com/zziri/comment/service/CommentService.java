package com.zziri.comment.service;

import com.zziri.comment.controller.dto.CommentDto;
import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.controller.dto.PostDto;
import com.zziri.comment.controller.dto.ResponseDto;
import com.zziri.comment.domain.Comment;
import com.zziri.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public ResponseDto put(PostDto postDto, String url) {
        url = url
                .replace("https://", "")
                .replace("http://", "");

        // To Do : dto 거치지 않고 바로 보내는 방법 고민

        CommentDto commentDto = CommentDto.of(LocalDateTime.now(), postDto.getAuthor(), url, postDto.getContent(), postDto.getPassword());

        return commentRepository.save(Comment.fromDto(commentDto)).getResponseDto();
    }

    public void put(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        Comment ret = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("id가 %d인 comment가 존재하지 않습니다", id))
        );
        return ret.isDeleted() ? null : ret;
    }

    public List<Comment> getCommentsByUrl(String url) {
        return commentRepository.findByUrl(
                url
                        .replace("http://", "")
                        .replace("https://", "")
        );
    }

    public List<Comment> getCommentsAll() {
        return commentRepository.findAll();
    }

    public void clear() {
        commentRepository.deleteAll();
    }

    public Comment modify(Long id, String content) {
        Comment comment = this.getCommentById(id);
        if (comment == null) {
            return null;
        }

        comment.setContent(content);

        return commentRepository.save(comment);
    }

    public Comment delete(DeleteDto deleteDto, String url) {
        Comment comment = commentRepository.findByIdAndPasswordAndUrl(deleteDto.getId(), deleteDto.getPassword(), url);

        comment.setDeleted(true);

        return commentRepository.save(comment);
    }
}
