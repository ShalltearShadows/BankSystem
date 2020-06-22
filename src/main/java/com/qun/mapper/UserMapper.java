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

    User getUCByID(@Param("uid") int id);

    User checkLogin(@Param("uid") int id,String pwd);
    
    int addUser(User user);

    int alterUser(User user);

    int deleteUser(@Param("uid") int id);

    int alterPassword(@Param("uid") int uid,String pwd);

    int setImg(@Param("uid") int uid,String img);

    User getUser(@Param("uid") int uid);

}
