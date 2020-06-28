/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/18
 * @Time: 9:55
 */
package com.qun.mapper;

import com.qun.pojo.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Mapper
@Repository
public interface CardMapper {
    @Cacheable(value = "card",key = "#cid")
    Card getCard(@Param("cid") Long cid);
    @CacheEvict(value = {"card"},allEntries = true)
    int deleteCard(@Param("cid") Long cid);
    @CacheEvict(value = {"card"},allEntries = true)
    int updateCard(Card card);
    @CacheEvict(value = {"card"},allEntries = true)
    int addCard(int uid, Timestamp date);
}
