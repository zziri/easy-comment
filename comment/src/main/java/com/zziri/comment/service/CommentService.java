package com.zziri.comment.service;

import com.zziri.comment.domain.Comment;
import com.zziri.comment.domain.dto.CommentDto;
import com.zziri.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void put(CommentDto commentDto) {
        commentRepository.save(Comment.fromDto(commentDto));
    }

    public List<Comment> getCommentsByUrl(String url) {
        return commentRepository.findByUrl(url);
    }

    public List<Comment> getCommentsAll() {
        return commentRepository.findAll();
    }
}
