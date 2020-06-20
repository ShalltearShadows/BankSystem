package com.qun.controller;

import com.qun.mapper.UserMapper;
import com.qun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/admin")
    public String black(){
        return "login";
    }


    @PostMapping("/user/login")
    public String login(@RequestParam("uid") int uid, @RequestParam("pwd") String pwd, Model model, HttpSession session){

        User user = userMapper.checkLogin(uid,pwd);

        if (user!=null){
            session.setAttribute("uid",uid);
            session.setAttribute("name",user.getUname());
            session.setAttribute("img",user.getImg());
            return "redirect:/main";
        }else {
            model.addAttribute("msg","用户名或密码错误");
            return "index";
        }
    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/index";
    }

}
