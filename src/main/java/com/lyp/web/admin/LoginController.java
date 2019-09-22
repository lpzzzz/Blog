package com.lyp.web.admin;

import com.lyp.domain.User;
import com.lyp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录的控制器
 */

@Controller
@RequestMapping("admin")
public class LoginController {

    @Autowired
    private UserService userService;


    /**
     * 跳转到登录页面的方法
     * @return
     */
    @GetMapping
    public String loginPage() {
        return "admin/login";
    }


    /**
     * 登录的方法
     * @param username
     * @param password
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session,
                        RedirectAttributes redirectAttributes){
        User user = userService.checkUser(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user",user); // 设置到session中
            session.getAttribute("user");
            System.out.println("===="+user+"======");
            return "admin/index";
        } else {
            // 重定向到登录页面
            redirectAttributes.addFlashAttribute("message","用户名或密码错误");
            System.out.println(redirectAttributes.getFlashAttributes()+" ======================= ");
            return "redirect:/admin";
        }

    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session) {
        // 登出就将session中的数据清空

        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
