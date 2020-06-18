/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/18
 * @Time: 14:38
 */
package com.qun.controller;

import com.qun.mapper.CardMapper;
import com.qun.mapper.UserMapper;
import com.qun.pojo.Card;
import com.qun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CardMapper cardMapper;


    @GetMapping("/add")
    public String add(){
        return "user/card/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam("pwd") String pwd, HttpSession session){
        //TODO 验证密码

        int uid = (int) session.getAttribute("uid");

        //TODO 检查卡数

        cardMapper.addCard(uid);

        return "redirect:/amount/display";
    }


    @GetMapping("/delete")
    public String delete(Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUserByID(uid);

        model.addAttribute("cards",user.getCards());

        return "user/card/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("pwd") String pwd, @RequestParam("cid") long cid){
        //TODO 验证密码

        //TODO 验证卡号
        int flag = cardMapper.deleteCard(cid);

        if (flag==1){

        }

        return "redirect:/amount/display";
    }

}
