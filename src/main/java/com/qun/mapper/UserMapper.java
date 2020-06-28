package com.qun.mapper;

import com.qun.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个Mapper表示这是一个mybatis的Mapper类，第二种方式是在application类加@Mapperscan
//在 Spring 程序中，Mybatis 需要找到对应的 mapper，在编译的时候动态生成代理类，所以我们需要在接口上添加 @Mapper 注解。
//@Repository 也可以使用@Component，效果都是一样的，只是为了声明为bean

@Mapper
@Repository
public interface UserMapper {

    @Cacheable(value = "usercard",key = "#id")
    User getUCByID(@Param("uid") int id);
    @Cacheable(value = "userlogin",key = "#id")
    User checkLogin(@Param("uid") int id,String pwd);
    @Cacheable(value = "user",key = "#uid")
    User getUser(@Param("uid") int uid);
    
    int addUser(User user);
    @CacheEvict(value = {"usercard","userlogin","user"},allEntries = true)
    int alterUser(User user);
    @CacheEvict(value = {"usercard","userlogin","user"},allEntries = true)
    int deleteUser(@Param("uid") int id);
    @CacheEvict(value = {"usercard","userlogin","user"},allEntries = true)
    int alterPassword(@Param("uid") int uid,String pwd);
    @CacheEvict(value = {"usercard","userlogin","user"},allEntries = true)
    int setImg(@Param("uid") int uid,String img);
    @CacheEvict(value = {"usercard","userlogin","user"},allEntries = true)
    int alterCounts(int counts,int uid);
}
