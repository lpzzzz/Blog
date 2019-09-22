package com.lyp.web;



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
import org.springframework.web.bind.annotation.RequestParam;

/**
 * index的控制器
 */

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;



    @GetMapping("/")
    public String index (@PageableDefault(size = 6,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable ,
                         BlogQuery blog , Model model) {
        // 拿到分页数据放到model中
        model.addAttribute("page",blogService.listPage(pageable));

        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags" , tagService.listTagTop(10));
        model.addAttribute("recommendBlogs" , blogService.listRecommendBlogTop(3));
        System.out.println("--------------index--------------");
        return "index";
    }



    @PostMapping("/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable ,
                         BlogQuery blog , @RequestParam String  query, Model model){

        model.addAttribute("page",blogService.listPage("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";

    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable  Long id , Model model) {
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommendBlogTop(3));
        return "_fragments :: newblogList";
    }
}
