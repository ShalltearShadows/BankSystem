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
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Mapper
@Repository
public interface CardMapper {
    Card getCard(@Param("cid") Long cid);
    int deleteCard(@Param("cid") Long cid);
    int updateCard(Card card);
    int addCard(int uid, Timestamp date);
}
