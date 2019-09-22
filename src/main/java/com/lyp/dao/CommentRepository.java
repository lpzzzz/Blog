package com.lyp.dao;

import com.lyp.domain.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> ,JpaSpecificationExecutor<Comment> {


    /**
     * 根据id进行查询
     * @param blogId
     * @param sort
     * @return
     */
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
