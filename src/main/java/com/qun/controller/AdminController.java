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

        if (flag==1){
            model.addAttribute("smsg","修改成功");
        }else {
            model.addAttribute("msg","修改失败");
        }

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

        set.addAll(adminMapper.roughQueryID(Integer.valueOf(i1)));

        List<User> users = new ArrayList<>(set);

        Collections.sort(users);

        model.addAttribute("users",users);

        return "admin/list";
    }

    /**
     * 去修改用户信息页面
     * @param model
     * @return
     */
    @GetMapping("/alteruserinfo")
    public String alterUserInfo(Model model){

        User user = new User();

        user.setGender(3);
        model.addAttribute("user",user);

        return "admin/alteruser";
    }

    @GetMapping("/alteruser/{uid}")
    public String alterUser(@PathVariable("uid") Integer uid,Model model){

        if (uid!=null){
            User user = userMapper.getUser(uid);
            model.addAttribute("user",user);
        }

        return "admin/alteruser";
    }

    /**
     * 修改用户信息
     * @param user
     * @param model
     * @return
     */
    @PostMapping("/alteruser")
    public String alteruser(User user,Model model){
        User u = userMapper.getUser(user.getUid());
        int flag = userMapper.alterUser(user);
        if (u==null){
            model.addAttribute("msg","账号不存在");
        }else if (flag==1){
            model.addAttribute("smsg","修改成功");
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
    public String password(@RequestParam("old") String old,@RequestParam("new") String password,
                           @RequestParam("new2") String new2, Model model,HttpSession session){

        int aid = (int) session.getAttribute("uid");

        Admin admin = adminMapper.getAdminByID(aid);

        if (!admin.getApwd().equals(old)){
            model.addAttribute("msg","旧密码错误");
        }else if (!password.equals(new2)){
            model.addAttribute("msg","密码不一致");
        }else {
            adminMapper.alterPassword(aid,password);
            model.addAttribute("smsg","修改成功");
        }

        return "admin/password";
    }


    @GetMapping("/head")
    public String img(HttpSession session){
        return "admin/upload";
    }


    @RequestMapping("/upload")
    public String one(MultipartFile file,Model model,HttpSession session) throws IOException {
        if(file.isEmpty()){
            model.addAttribute("msg","不能上传空文件！！！");
            return "upload";
        }

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

    @PostMapping("/register")
    public String register(Admin admin,@RequestParam("sex") String sex,HttpSession session){

        if (sex.indexOf("男")!=-1){
            admin.setGender(1);
        }else {
            admin.setGender(0);
        }
        adminMapper.addAdmin(admin);
        session.setAttribute("uid",admin.getAid());
        session.setAttribute("name",admin.getAname());
        session.setAttribute("img","0.jpg");
        session.setAttribute("permission","Administrator");
        return "admin/home";
    }

    @GetMapping("/delete")
    public String delete(){
        return "admin/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("pwd1") String pwd1,@RequestParam("pwd2") String pwd2,
                         Model model,HttpSession session){

        int aid = (int) session.getAttribute("uid");
        Admin admin = adminMapper.getAdminByID(aid);

        if (!admin.getApwd().equals(pwd1)){
            model.addAttribute("msg","密码不正确");
            return "admin/delete";
        }else if (!pwd1.equals(pwd2)){
            model.addAttribute("msg","两次输入的密码不一致");
            return "admin/delete";
        }else {
            adminMapper.deleteAdmin(aid);
            model.addAttribute("smsg","注销成功");
            session.invalidate();
            return "index";
        }
    }

}