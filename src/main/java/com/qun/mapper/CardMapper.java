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

@Mapper
@Repository
public interface CardMapper {
    Card getCard(@Param("cid") long cid);
    int deleteCard(@Param("cid") long cid);
    int updateCard(Card card);
    int addCard(int uid);
}
