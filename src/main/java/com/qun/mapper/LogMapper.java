/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/18
 * @Time: 16:26
 */

package com.qun.mapper;

import com.qun.pojo.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LogMapper {
    List<Log> queryAll(@Param("uid") int uid);
    List<Log> queryByCid(@Param("cid") long cid);
    int addLog(Log log);
}
