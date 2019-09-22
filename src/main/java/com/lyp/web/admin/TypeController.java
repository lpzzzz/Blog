package com.lyp.web.admin;


import com.lyp.domain.Type;
import com.lyp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;


    /**
     * 分页展示
     *  @PageableDefault(size = 每页显示条数,sort = {"根据什么进行排序"},direction =排序方式)
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String list(@PageableDefault(size = 3,sort = {"id"},direction =
            Sort.Direction.DESC) Pageable pageable
            ,Model model) { // Pageable 参数根据前端的参数封装到 Pageable 实体中
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 跳转新增分类的方法
     */
    @GetMapping("/types/input")
    public String input (Model model) {
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }


    /**
     * 新增分类
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String postSave(@Valid Type type, BindingResult result ,RedirectAttributes attributes){

        // 判断数据库中是否已经存在该分类
        Type ByNameType = typeService.findByName(type.getName());

        if (ByNameType != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/type-input";
        }

        Type t = typeService.saveType(type);
        if (t != null){ // 新增是否成功提示
            attributes.addFlashAttribute("message","新增成功");
        } else {
            attributes.addFlashAttribute("message","新增失败");
            System.out.println("分类中的提示信息" + attributes.getFlashAttributes());
        }

        return "redirect:/admin/types";  // 重定向之后才能拿到数据
    }

    /**
     * 将需要修改的值设置到编辑页面上
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editType (@PathVariable Long id , Model model) {
        model.addAttribute("type",typeService.getType(id));
        return "admin/type-input";
    }


    @PostMapping("/types/{id}")
    public String postUpdate(@Valid Type type, BindingResult result ,@PathVariable Long id ,
                             RedirectAttributes attributes){

        // 判断数据库中是否已经存在该分类
        Type ByNameType = typeService.findByName(type.getName());

        if (ByNameType != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }

        if (result.hasErrors()){
            return "admin/type-input";
        }

        Type t = typeService.updateType(id,type);
        if (t != null){ // 新增是否成功提示
            attributes.addFlashAttribute("message","修改成功");
        } else {
            attributes.addFlashAttribute("message","修改失败");
            System.out.println("分类中的提示信息" + attributes.getFlashAttributes());
        }

        return "redirect:/admin/types";  // 重定向之后才能拿到数据
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id , RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message" , "删除成功!");
        return "redirect:/admin/types";
    }
}
