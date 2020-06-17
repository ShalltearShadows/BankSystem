package com.qun.mapper;

import com.qun.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个Mapper表示这是一个mybatis的Mapper类，第二种方式是在application类加@Mapperscan
@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User getUserByID(@Param("uid") int id);

    int checkLogin(int id,String pwd);
    
    int addUser(User user);

    int alterUser(User user);

    int deleteUser(int id);

}
