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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LogMapper {
    @Cacheable(value = "log",key = "#uid")
    List<Log> queryAll(@Param("uid") int uid);
    @Cacheable(value = "log",key = "#uid")
    List<Log> queryByCid(@Param("cid") long cid);
    @CacheEvict(value = {"admin"},allEntries = true)
    int transferLog(Log log);
    @CacheEvict(value = {"admin"},allEntries = true)
    int depositLog(Log log);
    @CacheEvict(value = {"admin"},allEntries = true)
    int withdrawLog(Log log);
}
