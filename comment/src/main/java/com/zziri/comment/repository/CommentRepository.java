package com.zziri.comment.repository;


import com.zziri.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUrl(String url);

    Comment findByIdAndPassword(Long id, String password);

    Comment findByIdAndPasswordAndUrl(Long id, String password, String url);
}
