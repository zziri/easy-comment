package com.zziri.comment.repository;


import com.zziri.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUrl(String url);
    @Query(value = "select * from comment where comment.id = id and comment.hash_code = hashCode", nativeQuery = true)
    Comment findByIdHashCode(@Param("id") Long id, @Param("hashCode") String hashCode);
}
