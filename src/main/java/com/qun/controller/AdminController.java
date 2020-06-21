package com.qun.controller;

import com.qun.mapper.AdminMapper;
import com.qun.mapper.UserMapper;
import com.qun.pojo.Admin;
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
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/person")
    public String person(Model model, HttpSession session){
        int aid = (int) session.getAttribute("uid");
        Admin admin=adminMapper.getAdminByID(aid);
        model.addAttribute("admin",admin);
        return "admin/person";
    }

    @GetMapping("/alter/{id}")
    public String alter(@PathVariable("id") int id,Model model){
        Admin admin = adminMapper.getAdminByID(id);
        model.addAttribute("admin",admin);

        return "admin/alter";
    }

    @PostMapping("/alter")
    public String alter(Admin admin,Model model,HttpSession session){
        int flag=adminMapper.alterAdmin(admin);
        session.setAttribute("name",admin.getAname());

        return "admin/person";
    }


    //管理员查看所有用户功能
    @GetMapping("/users")
    public String list(Model model){
        List<User> users = adminMapper.queryUserList();
        model.addAttribute("users",users);
        return "admin/list";
    }

    @PostMapping("/name")
    public String queryUser(@RequestParam("name") String name,Model model){

        Set<User> set = new HashSet<>(adminMapper.roughQuery(name));

        String[] names = name.split("");

        for (String s : names) {
            set.addAll(adminMapper.roughQuery(s));
        }

        List<User> users = new ArrayList<>(set);

        Collections.sort(users);

        model.addAttribute("users",users);

        return "admin/list";
    }

    @PostMapping("/uid")
    public String queryUser(@RequestParam("uid") Integer uid,Model model){

        Set<User> set = new HashSet<>(adminMapper.roughQueryID(uid));

        String id = uid+"";

        String i1 = id.substring(0,id.length()-1);
        String i2 = id.substring(id.length()-1);

        set.addAll(adminMapper.roughQueryID(Integer.valueOf(i1)));
        set.addAll(adminMapper.roughQueryID(Integer.valueOf(i2)));

        List<User> users = new ArrayList<>(set);

        Collections.sort(users);

        model.addAttribute("users",users);

        return "admin/list";
    }

    @GetMapping("/alteruserinfo")
    public String alterUserInfo(Model model){

        User user = new User();

        model.addAttribute("user",user);

        return "admin/alteruser";
    }

    @GetMapping("/alteruser/{uid}")
    public String alterUser(@PathVariable("uid") Integer uid,Model model){

        if (uid!=null){
            User user = userMapper.getUserByID(uid);
            model.addAttribute("user",user);
        }

        return "admin/alteruser";
    }

    @PostMapping("/alteruser")
    public String alteruser(User user,Model model){
        int flag = userMapper.alterUser(user);
        if (flag==1){
            model.addAttribute("msg","修改成功");
        }else {
            model.addAttribute("msg","修改失败");
        }

        return "admin/alteruser";
    }


    @GetMapping("/password")
    public String password(){
        return "admin/password";
    }

    @PostMapping("/password")
    public String password(@RequestParam("new") String password,Model model,HttpSession session){

        int aid = (int) session.getAttribute("uid");

        int flag = adminMapper.alterPassword(aid,password);

        return "admin/password";
    }


    @GetMapping("/head")
    public String img(Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUserByID(uid);

        model.addAttribute("user",user);

        return "admin/upload";
    }


    @RequestMapping("/upload")
    public String one(MultipartFile file, RedirectAttributes redirectAttributes, Model model,
                      HttpServletRequest request,HttpSession session) throws IOException {
        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("msg","不能上传空文件！！！");
            return "upload";
        }

        redirectAttributes.addFlashAttribute("name",file.getOriginalFilename());

        //获取target/class里的文件
        String realPath = User.class.getClassLoader().getResource("./static/upload/").getPath();


        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf(".") + 1);

        int uid = (int) session.getAttribute("uid");
        String img = uid+"."+suffix;
        File webFile=new File(realPath,img);

        adminMapper.setImg(uid,img);

        file.transferTo(webFile);

        session.setAttribute("img",img);

        return "admin/upload";

    }

}
