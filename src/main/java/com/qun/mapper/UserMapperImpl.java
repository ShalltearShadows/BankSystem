/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/25
 * @Time: 9:09
 */
package com.qun.mapper;


import com.qun.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl {

    @Autowired
    private UserMapper userMapper;

    @Cacheable(value = "userlogin",key = "#id")
    public User checkLogin(int id, String pwd){
        User user = userMapper.checkLogin(id, pwd);
        return user;
    }

}
