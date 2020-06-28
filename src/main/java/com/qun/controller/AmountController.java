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


    /**
     * 展示用户银行卡信息
     * @param model 存储返回给用户的数据
     * @param session 会话
     * @return 用户页面
     */
    @GetMapping("/display")
    public String amount(Model model, HttpSession session){
        //获取用户账号
        int uid = (int)session.getAttribute("uid");
        //查询用户及银行卡信息
        User user = userMapper.getUCByID(uid);
        //将查询到的数据添加到model
        model.addAttribute("cards",user.getCards());
        //跳转到显示页面
        return "user/amount/amount";
    }

    /**
     * 跳转到存款页面
     * @return 存款页面
     */
    @GetMapping("/deposit")
    public String depositTwo(){
        return "user/amount/deposit";
    }

    /**
     * 跳转到存款页面
     * @param id 用户id
     * @param model
     * @return
     */
    @GetMapping("/deposit/{cid}")
    public String deposit(@PathVariable("cid") String id,Model model){
        model.addAttribute("cid",id);
        return "user/amount/deposit";
    }
    /**
     * 处理用户的存款请求
     * @param cid 银行卡号
     * @param amount 存款金额
     * @param session 会话
     * @param model 存储提示信息的对象
     * @return 成功或失败需要跳转的页面
     */
    @PostMapping("/deposit")
    public String deposit(@RequestParam("cid") Long cid,@RequestParam("amount")
            double amount,HttpSession session,Model model){
        //获取用户银行卡
        Card card = cardMapper.getCard(cid);
        //获取用户账户
        int uid = (int) session.getAttribute("uid");
        //如果银行卡错误，则返回失败信息
        if (card==null||card.getUid()!=uid){
            model.addAttribute("msg","卡号错误");
            return "user/amount/deposit";
        }
        //银行卡余额
        double flag = card.getAmount();
        //向银行卡存款
        card.setAmount(flag+amount);
        //将数据库的银行卡信息更新
        int res = cardMapper.updateCard(card);
        //添加日志记录
        Log log = LogService.depositLog(card.getUid(), cid, amount);
        logMapper.depositLog(log);
        //返回成功信息
        model.addAttribute("smsg","存款成功");
        //添加用户信息到model
        User user = userMapper.getUCByID(card.getUid());
        model.addAttribute("cards",user.getCards());
        //页面跳转
        return "user/amount/amount";
    }

    /**
     * 调转到取款页面
     * @return 取款页面
     */
    @GetMapping("/withdraw")
    public String withdrawTwo(){
        return "user/amount/withdraw";
    }

    @GetMapping("/withdraw/{cid}")
    public String withdraw(@PathVariable("cid") String id,Model model){

        model.addAttribute("cid",id);

        return "user/amount/withdraw";
    }

    /**
     * 处理用户的取款请求
     * @param cid 银行卡号
     * @param amount 取款金额
     * @param model 存储数据的对象
     * @return 成功或者失败需要跳转的页面
     */
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("cid") Long cid, @RequestParam("amount")
            double amount, Model model){
        //查询银行卡信息
        Card card = cardMapper.getCard(cid);
        if (card==null){
            model.addAttribute("msg","卡号不存在");
            return "user/amount/withdraw";
        }
        //获取银行卡余额
        double flag = card.getAmount();
        //检查银行卡余额是否充足
        if (MathUtil.compare(flag,amount)>0){
            card.setAmount(flag-amount);//充足，则取款
            cardMapper.updateCard(card);//从数据库更新银行卡信息
            Log log = LogService.withdrawLog(card.getUid(), cid, amount);//
            logMapper.withdrawLog(log);//记录日志
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
