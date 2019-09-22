package com.lyp.service;

import com.lyp.domain.Comment;

import java.util.List;

public interface CommentsService {

    /**
     * 获取评论列表
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
