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


    @GetMapping("/display")
    public String amount(Model model, HttpSession session){

        int uid = (int)session.getAttribute("uid");
        User user = userMapper.getUserByID(uid);
        model.addAttribute("cards",user.getCards());

        return "user/amount/amount";
    }

    @GetMapping("/deposit")
    public String depositTwo(){
        return "user/amount/deposit";
    }

    @GetMapping("/deposit/{cid}")
    public String deposit(@PathVariable("cid") String id,Model model){

        model.addAttribute("cid",id);

        return "user/amount/deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("cid") Long cid,@RequestParam("amount") double amount,Model model){

        Card card = cardMapper.getCard(cid);

        double flag = card.getAmount();

        card.setAmount(flag+amount);

        int res = cardMapper.updateCard(card);

        if (res!=1){
            model.addAttribute("存款失败","msg");
            return "user/amount/deposit";
        }

        return "redirect:/amount/display";
    }

    @GetMapping("/withdraw")
    public String withdrawTwo(){
        return "user/amount/withdraw";
    }

    @GetMapping("/withdraw/{cid}")
    public String withdraw(@PathVariable("cid") String id,Model model){

        model.addAttribute("cid",id);

        return "user/amount/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("cid") Long cid,@RequestParam("amount") double amount,Model model){
        Card card = cardMapper.getCard(cid);

        double flag = card.getAmount();
        int res=0;
        if (flag>=amount){
            card.setAmount(flag-amount);
            res=cardMapper.updateCard(card);
        }

        if (res!=1){
            model.addAttribute("取款失败","msg");
            return "user/amount/withdraw";
        }

        return "redirect:/amount/display";
    }

    @GetMapping("/transfer")
    public String transfer(Model model,HttpSession session){

        int uid = (int)session.getAttribute("uid");
        User user = userMapper.getUserByID(uid);
        model.addAttribute("cards",user.getCards());

        return "user/amount/transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("cid1") Long cid1,@RequestParam("cid2") Long cid2,
                                 @RequestParam("amount") double amount,Model model){

        Card card1 = cardMapper.getCard(cid1);
        Card card2 = cardMapper.getCard(cid2);
        double flag = card1.getAmount();
        int res=0;
        if (flag>=amount){
            card1.setAmount(flag-amount);
            res=cardMapper.updateCard(card1);
            double am = card2.getAmount();
            card2.setAmount(am+amount);
        }

        if (res!=1){
            model.addAttribute("转账失败","msg");
            return "user/amount/withdraw";
        }

        return "redirect:/amount/display";
    }


    @ExceptionHandler(NullPointerException.class)
    public String Null(){
        return "null";
    }
}
