package com.zziri.comment.service;

import com.zziri.comment.controller.dto.DeleteDto;
import com.zziri.comment.domain.Comment;
import com.zziri.comment.controller.dto.CommentDto;
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
        commentDto.setUrl(
                commentDto.getUrl()
                        .replace("https://", "")
                        .replace("http://", "")
        );
        return commentRepository.save(Comment.fromDto(commentDto));
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

    public Comment delete(DeleteDto deleteDto) {
        Comment comment = commentRepository.findByIdAndPassword(deleteDto.getId(), deleteDto.getPassword());

        comment.setDeleted(true);

        return commentRepository.save(comment);
    }
}
