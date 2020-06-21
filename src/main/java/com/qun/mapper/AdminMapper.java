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
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminMapper {
    Admin checkLogin(int aid,String pwd);

    Admin getAdminByID(@Param("aid") int aid);

    int addAdmin(Admin admin);

    int alterAdmin(Admin admin);

    int deleteAdmin(@Param("aid") int aid);

    int alterPassword(@Param("aid") int aid,String pwd);

    int setImg(@Param("uid") int aid,String img);
}
