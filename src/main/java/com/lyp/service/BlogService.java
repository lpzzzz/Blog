package com.lyp.service;

import com.lyp.domain.Blog;
import com.lyp.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    /**
     * 根据id1查询
     *
     * @param id
     * @return
     */
    Blog getBlog(Long id);

    /**
     * 获取并转换
     * @param id
     * @return
     */
    Blog getAndConvert(Long id);

    /**
     * 根据条件分页查询
     *
     * @param pageable
     * @param blog
     * @return
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listPage(Pageable pageable);

    Page<Blog> listPage(String Query , Pageable pageable);

    /**
     * 保存博客
     *
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);


    /**
     * 修改Blog
     *
     * @param id
     * @param blog
     * @return
     */
    Blog updateBlog(Long id, Blog blog);


    /**
     * 根据id进行删除
     *
     * @param id
     */
    void deleteBlog(Long id);


    /**
     * 推荐博客列表
     * @param size
     * @return
     */
    List<Blog> listRecommendBlogTop (Integer size);


    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
