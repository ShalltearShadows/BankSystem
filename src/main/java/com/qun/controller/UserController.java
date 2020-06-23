package com.qun.controller;

import com.qun.mapper.UserMapper;
import com.qun.pojo.User;
import lombok.extern.slf4j.Slf4j;
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
import java.util.logging.Logger;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    private static Logger logger = Logger.getLogger("log");


    @GetMapping("/person")
    public String person(Model model, HttpSession session){
        int uid = (int) session.getAttribute("uid");
        User user=userMapper.getUser(uid);
        model.addAttribute("user",user);
        return "user/person";
    }


    @GetMapping("/alter/{id}")
    public String alter(@PathVariable("id") Integer id,Model model){
        User user = userMapper.getUser(id);
        model.addAttribute("user",user);

        return "user/alter";
    }

    @PostMapping("/alter")
    public String alter(User user,Model model,HttpSession session){
        int flag=userMapper.alterUser(user);
        session.setAttribute("name",user.getUname());
        if (flag==1){
            model.addAttribute("smsg","修改成功");
        }else {
            model.addAttribute("msg","修改失败");
        }
        return "user/person";
    }

    @GetMapping("/password")
    public String password(){
        return "user/password";
    }

    @PostMapping("/password")
    public String password(@RequestParam("old") String old,@RequestParam("new") String password,
                           @RequestParam("new2") String new2, Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUser(uid);

        if (!user.getUpwd().equals(old)){
            model.addAttribute("msg","旧密码错误");
        }else if (!password.equals(new2)){
            model.addAttribute("msg","密码不一致");
        }else {
            userMapper.alterPassword(uid,password);
            model.addAttribute("smsg","修改成功");
        }

        return "user/password";
    }


    @GetMapping("/head")
    public String img(){
        return "user/upload";
    }


    @PostMapping("/upload")
    public String one(MultipartFile file,Model model, HttpSession session) throws IOException {
        if(file.isEmpty()){
            model.addAttribute("msg","不能上传空文件！！！");
            return "user/upload";
        }

        //获取target/class里的文件
        String realPath = User.class.getClassLoader().getResource("./static/upload/").getPath();
        String nativePath="D:\\Java\\BankSystem\\src\\main\\resources\\static\\upload";

        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf(".") + 1);

        int uid = (int) session.getAttribute("uid");
        String img = uid+"."+suffix;
        File webFile=new File(realPath,img);

        userMapper.setImg(uid,img);

        file.transferTo(webFile);
        session.setAttribute("img",img);

        return "user/upload";

    }

}
