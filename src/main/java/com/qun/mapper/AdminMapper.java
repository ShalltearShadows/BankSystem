/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/21
 * @Time: 9:02
 */
package com.qun.mapper;

import com.qun.pojo.Admin;
import com.qun.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    @Cacheable(value = "admin",key = "#id")
    Admin checkLogin(@Param("aid") int aid,@Param("pwd") String pwd);
    @Cacheable(value = "admin",key = "#id")
    Admin getAdminByID(@Param("aid") int aid);
    @Cacheable(value = "userlist")
    List<User> queryUserList();
    @Cacheable(value = "userlist",key = "#name")
    List<User> roughQuery(@Param("name") String name);
    @Cacheable(value = "userlist",key = "#uid")
    List<User> roughQueryID(@Param("uid") int uid);
    @CacheEvict(value = {"admin"},allEntries = true)
    int addAdmin(Admin admin);
    @CacheEvict(value = {"admin"},allEntries = true)
    int alterAdmin(Admin admin);
    @CacheEvict(value = {"admin"},allEntries = true)
    int deleteAdmin(@Param("aid") int aid);
    @CacheEvict(value = {"admin"},allEntries = true)
    int alterPassword(@Param("aid") int aid,String pwd);
    @CacheEvict(value = {"admin"},allEntries = true)
    int setImg(@Param("aid") int aid,String img);
}
