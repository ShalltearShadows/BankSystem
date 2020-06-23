package com.qun.controller;

import com.qun.mapper.AdminMapper;
import com.qun.mapper.UserMapper;
import com.qun.pojo.Admin;
import com.qun.pojo.User;
import com.qun.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/admin")
    public String adlogin(){
        return "admin";
    }


    @PostMapping("/user/login")
    public String login(@RequestParam("uid") int uid, @RequestParam("pwd") String pwd, Model model, HttpSession session){

        User user = userMapper.checkLogin(uid,pwd);

        if (user!=null){
            session.setAttribute("uid",uid);
            session.setAttribute("name",user.getUname());
            session.setAttribute("img",user.getImg()==null?"0.jpg":user.getImg());
            session.setAttribute("permission",user.getPermission());
            return "redirect:/home";
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

    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam("aid") int aid,@RequestParam("pwd") String pwd,Model model,HttpSession session){

        Admin admin = adminMapper.checkLogin(aid,pwd);

        if (admin!=null){
            session.setAttribute("uid",aid);
            session.setAttribute("name",admin.getAname());
            session.setAttribute("img",admin.getImg()==null?"0.jpg":admin.getImg());
            session.setAttribute("permission",admin.getPermission());
            return "admin/home";
        }else {
            model.addAttribute("msg","用户名或密码错误");
            return "admin";
        }
    }

    @GetMapping("/user/register")
    public String register(){
        return "register";
    }

    @PostMapping("/user/register")
    public String register(User user,HttpSession session){

        userMapper.addUser(user);

        session.setAttribute("uid",user.getUid());
        session.setAttribute("name",user.getUname());
        session.setAttribute("img","0.jpg");
        session.setAttribute("permission","User");

        return "redirect:/home";
    }

    @GetMapping("/admin/adduser")
    public String adduser(){
        return "admin/adduser";
    }


    @PostMapping("/admin/adduser")
    public String adduser(User user,Model model){
        int flag = userMapper.addUser(user);

        if (flag==1){
            model.addAttribute("smsg","添加成功");
        }else {
            model.addAttribute("msg","添加失败");
        }

        return "admin/adduser";
    }

}
