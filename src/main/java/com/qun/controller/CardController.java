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
    public String add(@RequestParam("pwd") String pwd,Model model, HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUCByID(uid);
        if (!pwd.equals(user.getUpwd())){
            model.addAttribute("msg","密码错误");
            return "user/card/add";
        }

        if (user.getCards().size()>=5){
            model.addAttribute("msg","您已持有5张银行卡，不能再申请银行卡了！");
            return "user/card/add";
        }

        model.addAttribute("smsg","申请成功");
        cardMapper.addCard(uid);

        model.addAttribute("cards",user.getCards());

        return "user/amount/amount";
    }


    @GetMapping("/delete")
    public String delete(Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUser(uid);

        model.addAttribute("cards",user.getCards());

        return "user/card/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("pwd") String pwd, @RequestParam("cid") long cid,
                         Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUCByID(uid);

        if (!pwd.equals(user.getUpwd())){
            model.addAttribute("msg","密码错误");
            return "user/card/delete";
        }

        if (user.getCards().size()<=0){
            model.addAttribute("msg","您的银行卡数量为0，不能再删除银行卡了！");
            return "user/card/delete";
        }

        model.addAttribute("smsg","删除成功");

        int flag = cardMapper.deleteCard(cid);

        if (flag==1){
            model.addAttribute("msg","服务器错误，删除失败");
            return "user/card/delete";
        }

        model.addAttribute("cards",user.getCards());

        return "user/amount/amount";
    }
}