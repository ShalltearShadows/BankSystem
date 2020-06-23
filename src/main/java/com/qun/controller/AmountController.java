/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/17
 * @Time: 20:05
 */
package com.qun.controller;

import com.qun.mapper.CardMapper;
import com.qun.mapper.LogMapper;
import com.qun.mapper.UserMapper;
import com.qun.pojo.Card;
import com.qun.pojo.Log;
import com.qun.pojo.User;
import com.qun.service.LogService;
import com.qun.utils.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/amount")
public class AmountController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private LogMapper logMapper;


    @GetMapping("/display")
    public String amount(Model model, HttpSession session){

        int uid = (int)session.getAttribute("uid");
        User user = userMapper.getUCByID(uid);
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

        Log log = LogService.depositLog(card.getUid(), cid, amount);
        logMapper.depositLog(log);

        if (res!=1){
            model.addAttribute("存款失败","msg");
            return "user/amount/deposit";
        }else {
            model.addAttribute("smsg","存款成功");
        }

        User user = userMapper.getUCByID(card.getUid());
        model.addAttribute("cards",user.getCards());

        return "user/amount/amount";
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
    public String withdraw(@RequestParam("cid") Long cid, @RequestParam("amount") double amount, Model model){
        Card card = cardMapper.getCard(cid);

        double flag = card.getAmount();
        int res=0;
        if (MathUtil.compare(flag,amount)>0){
            card.setAmount(flag-amount);
            res=cardMapper.updateCard(card);

            Log log = LogService.withdrawLog(card.getUid(), cid, amount);
            logMapper.withdrawLog(log);
            model.addAttribute("smsg","取款成功");
        }else {
            model.addAttribute("msg","余额不足");
        }

        User user = userMapper.getUCByID(card.getUid());
        model.addAttribute("cards",user.getCards());

        return "user/amount/amount";
    }

    @GetMapping("/transfer")
    public String transfer(Model model,HttpSession session){

        int uid = (int)session.getAttribute("uid");
        User user = userMapper.getUCByID(uid);
        model.addAttribute("cards",user.getCards());


        return "user/amount/transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam("cid1") Long cid1,@RequestParam("cid2") Long cid2,
                                 @RequestParam("amount") double amount,Model model,HttpSession session){

        Card card1 = cardMapper.getCard(cid1);
        Card card2 = cardMapper.getCard(cid2);
        double flag = card1.getAmount();
        int res=0;
        if (cid1==cid2){
            model.addAttribute("msg","同一张卡不能转账");
        }else if (MathUtil.compare(flag,amount)<0){
            model.addAttribute("msg","余额不足");
        }else {
            //更新转出卡
            card1.setAmount(flag-amount);
            res=cardMapper.updateCard(card1);

            //更新转入卡
            double am = card2.getAmount();
            card2.setAmount(am+amount);
            if (res==1){
                res=cardMapper.updateCard(card2);
            }

            //添加Card1日志
            Log log = LogService.tansferLog(card1.getUid(), cid1, cid2, amount);
            logMapper.transferLog(log);
            //若两张卡的卡主不同，添加Card2日志
            if (card1.getCid()==card2.getUid()){
                log.setUid(card2.getUid());
                logMapper.transferLog(log);
            }

            model.addAttribute("smsg","转账成功");
        }

        User user = userMapper.getUCByID(card1.getUid());
        model.addAttribute("cards",user.getCards());

        return "user/amount/amount";
    }

}
