/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/17
 * @Time: 20:05
 */
package com.qun.controller;

import com.qun.mapper.UserMapper;
import com.qun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/amount")
public class AmountController {
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/display")
    public String amount(Model model, HttpSession session){

        int uid = (int)session.getAttribute("uid");
        User user = userMapper.getUserByID(uid);
        model.addAttribute("cards",user.getCards());

        return "user/amount";
    }
}
