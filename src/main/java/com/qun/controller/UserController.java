package com.qun.controller;

import com.qun.mapper.UserMapper;
import com.qun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private HttpSession session;


    @RequestMapping("/users")
    public String list(Model model){
        Collection<User> users = userMapper.queryUserList();
        model.addAttribute("users",users);
        return "user/list";
    }

    @GetMapping("/person")
    public String person(Model model, HttpSession session){
        int uid = (int) session.getAttribute("uid");
        User user=userMapper.getUserByID(uid);
        model.addAttribute("user",user);
        return "user/person";
    }


    @GetMapping("/alter/{id}")
    public String alter(@PathVariable("id") Integer id,Model model){
        User user = userMapper.getUserByID(id);
        model.addAttribute("user",user);

        return "user/alter";
    }

    @PostMapping("/alter")
    public String alter(User user,Model model){
        int flag=userMapper.alterUser(user);

        return "user/person";

    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){

        userMapper.deleteUser(id);

        return "redirect:/emps";
    }



}
