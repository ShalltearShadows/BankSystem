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
    Admin checkLogin(@Param("aid") int aid,@Param("pwd") String pwd);

    Admin getAdminByID(@Param("aid") int aid);

    List<User> queryUserList();

    List<User> roughQuery(@Param("name") String name);

    List<User> roughQueryID(@Param("uid") int uid);

    int addAdmin(Admin admin);

    int alterAdmin(Admin admin);

    int deleteAdmin(@Param("aid") int aid);

    int alterPassword(@Param("aid") int aid,String pwd);

    int setImg(@Param("aid") int aid,String img);
}
