package com.zziri.comment.service;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.CommentDto;
import com.zziri.comment.domain.dto.Date;
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

    public Comment put(CommentDto commentDto) {
        commentDto.setDate(LocalDateTime.now());
        return commentRepository.save(Comment.fromDto(commentDto));
    }

    public void put(Comment comment) {
        comment.setDate(Date.of(LocalDateTime.now()));
        commentRepository.save(comment);
    }

    public List<Comment> getCommentsByUrl(String url) {
        return commentRepository.findByUrl(url);
    }

    public List<Comment> getCommentsAll() {
        return commentRepository.findAll();
    }

    public void clear() {
        commentRepository.deleteAll();
    }

    public Comment modify(Long id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("id가 %d인 comment가 존재하지 않습니다", id))
        );

        comment.setContent(content);

        return commentRepository.save(comment);
    }
}
