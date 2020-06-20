package com.qun.controller;

import com.qun.mapper.UserMapper;
import com.qun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    //TODO 管理员查看所有用户功能
    @RequestMapping("/users")
    public String list(Model model){
        Collection<User> users = userMapper.queryUserList();
        model.addAttribute("users",users);
        return "list";
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
    public String alter(User user,Model model,HttpSession session){
        int flag=userMapper.alterUser(user);
        session.setAttribute("name",user.getUname());

        return "user/person";

    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){

        userMapper.deleteUser(id);

        return "redirect:/emps";
    }

    @GetMapping("/password")
    public String password(){
        return "user/password";
    }

    @PostMapping("/password")
    public String password(@RequestParam("new") String password,Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        int flag = userMapper.alterPassword(uid,password);

        return "user/password";
    }


    @GetMapping("/head")
    public String img(Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUserByID(uid);

        model.addAttribute("user",user);

        return "user/upload";
    }


    @RequestMapping("/upload")
    public String one(MultipartFile file, RedirectAttributes redirectAttributes, Model model,
                      HttpServletRequest request,HttpSession session) throws IOException {
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("msg","不能上传空文件！！！");
            return "upload";
        }

        redirectAttributes.addFlashAttribute("name",file.getOriginalFilename());

        String realPath="D:\\Java\\BankSystem\\src\\main\\resources\\static\\upload";
        //获取target/class里的文件
        //String realPath = User.class.getClassLoader().getResource("./static/upload/").getPath();


        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf(".") + 1);

        int uid = (int) session.getAttribute("uid");
        String img = uid+"."+suffix;
        File destFile=new File(realPath,img);

        userMapper.setImg(uid,img);

        file.transferTo(destFile);

        return "user/upload";

    }

}
