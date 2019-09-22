package com.lyp.web.admin;


import com.lyp.domain.Blog;
import com.lyp.domain.User;
import com.lyp.service.BlogService;
import com.lyp.service.TagService;
import com.lyp.service.TypeService;
import com.lyp.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 博客列表的分页查询
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable ,
                        BlogQuery blogQuery , Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        return LIST;
    }


    /**
     * 搜索
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable ,
                        BlogQuery blog , Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs::blogList"; // 返回的是一个片段
    }

    private void setTypeAndTag(Model model) {
        //初始化分类
        model.addAttribute("types",typeService.listType());
        // 初始化分类
        model.addAttribute("tags",tagService.listTag());
    }


    /**
     * 新增页面
     * @param model
     * @return
     */
    @GetMapping("/blog/input")
    public String input (Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @GetMapping("/blog/{id}/input")
    public String editInput (@PathVariable Long id , Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }

    /**
     * 发布与修改共用一个模板
     */

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));

        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b != null) {
            attributes.addFlashAttribute("message","发布成功!");
        } else {
            attributes.addFlashAttribute("message","发布失败!");
        }


        return REDIRECT_LIST;
    }


    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blog/{id}/delete")
    public String delete(@PathVariable Long id , RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功!");
        return REDIRECT_LIST;
    }

}
