/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/18
 * @Time: 16:32
 */
package com.qun.controller;

import com.qun.mapper.CardMapper;
import com.qun.mapper.LogMapper;
import com.qun.mapper.UserMapper;
import com.qun.pojo.Log;
import com.qun.pojo.User;
import com.qun.service.LogService;
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
import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/all")
    public String all(Model model, HttpSession session){

        int uid = (int) session.getAttribute("uid");

        List<Log> logs = logMapper.queryAll(uid);

        model.addAttribute("logs",logs);

        return "user/log/all";
    }

    @GetMapping("/query")
    public String query(Model model, HttpSession session){
        int uid = (int) session.getAttribute("uid");

        User user = userMapper.getUserByID(uid);

        model.addAttribute("cards",user.getCards());

        return "user/log/query";
    }

    @PostMapping("/query")
    public String query(@RequestParam("cid") long cid, Model model,HttpSession session,
                        @RequestParam("date1") String date1,@RequestParam("date2") String date2){

        int uid = (int) session.getAttribute("uid");

        //获取条件查询的logs
        List<Log> logs = LogService.queryLogsByCid(logMapper.queryByCid(cid),cid,date1,date2);

        model.addAttribute("logs",logs);

        return "/user/log/query";
    }
}