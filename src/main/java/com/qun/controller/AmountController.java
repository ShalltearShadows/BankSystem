/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/17
 * @Time: 20:05
 */
package com.qun.controller;

import com.qun.mapper.CardMapper;
import com.qun.mapper.UserMapper;
import com.qun.pojo.Card;
import com.qun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/amount")
public class AmountController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CardMapper cardMapper;

    private HttpSession session;

    @GetMapping("/display")
    public String amount(Model model, HttpSession session){

        int uid = (int)session.getAttribute("uid");
        User user = userMapper.getUserByID(uid);
        model.addAttribute("cards",user.getCards());

        return "user/amount";
    }

    @GetMapping("/deposit/{cid}")
    public String deposit(@PathVariable("cid") String id,Model model){

        model.addAttribute("cid",id);

        return "user/deposit";
    }

    @PostMapping("/deposit")
    public String depositAmount(@RequestParam("cid") Long cid,@RequestParam("amount") double amount,Model model){

        Card card = cardMapper.getCard(cid);

        double flag = card.getAmount();

        card.setAmount(flag+amount);

        int res = cardMapper.updateCard(card);

        if (res!=1){
            model.addAttribute("存款失败","msg");
            return "user/deposit";
        }

        return "redirect:/main";
    }

    @GetMapping("/withdraw/{cid}")
    public String withdraw(@PathVariable("cid") String id,Model model){

        model.addAttribute("cid",id);

        return "user/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdrawAmount(@RequestParam("cid") Long cid,@RequestParam("amount") double amount,Model model){
        Card card = cardMapper.getCard(cid);

        double flag = card.getAmount();
        int res=0;
        if (flag>=amount){
            card.setAmount(flag-amount);
            res=cardMapper.updateCard(card);
        }

        if (res!=1){
            model.addAttribute("取款失败","msg");
            return "user/withdraw";
        }

        return "redirect:/main";
    }
}
