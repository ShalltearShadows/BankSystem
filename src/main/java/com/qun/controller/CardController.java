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
import com.qun.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.logging.Logger;

@Controller
@RequestMapping("/card")
public class CardController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CardMapper cardMapper;

    private static Logger logger = Logger.getLogger("log");


    @GetMapping("/add")
    public String add(){
        return "user/card/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam("pwd") String pwd,Model model, HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUCByID(uid);

        try {

            if (!pwd.equals(user.getUpwd())) {
                model.addAttribute("msg", "密码错误");
                return "user/card/add";
            } else if (user.getCards().size() >= 5) {
                model.addAttribute("msg", "您已持有5张银行卡，不能再申请银行卡了！");
                return "user/card/add";
            }

            model.addAttribute("smsg", "申请成功");
            cardMapper.addCard(uid, new Timestamp(System.currentTimeMillis()));

            User usercard = userMapper.getUCByID(uid);
            userMapper.alterCounts(usercard.getCardCounts(),uid);

            model.addAttribute("cards", usercard.getCards());
        }catch (NullPointerException e){
            logger.warning(e.getMessage());
        }
        return "user/amount/amount";
    }


    @GetMapping("/delete")
    public String delete(Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUCByID(uid);

        model.addAttribute("cards",user.getCards());

        return "user/card/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("pwd") String pwd, @RequestParam("cid") Long cid,
                         Model model,HttpSession session){

        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUCByID(uid);

        Card card = cardMapper.getCard(cid);


        if (!pwd.equals(user.getUpwd())){
            model.addAttribute("msg","密码错误");
            return "user/card/delete";
        }else if (user.getCards().size()<=0){
            model.addAttribute("msg","您的银行卡数量为0，不能再删除银行卡了！");
            return "user/card/delete";
        }else if (card.getAmount()>0){
            model.addAttribute("msg","您的银行卡余额大于0，请先转出全部金额");
            return "user/card/delete";
        }

        cardMapper.deleteCard(cid);
        model.addAttribute("smsg","删除成功");

        User usercard = userMapper.getUCByID(uid);
        userMapper.alterCounts(usercard.getCardCounts(),uid);

        model.addAttribute("cards",usercard.getCards());

        return "user/amount/amount";
    }
}