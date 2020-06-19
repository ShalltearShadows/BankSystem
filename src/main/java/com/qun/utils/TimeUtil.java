/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/19
 * @Time: 10:40
 */
package com.qun.utils;

import org.thymeleaf.util.StringUtils;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimeUtil {
    public static Timestamp stringToStamp(String date){
        //如果没输入日期，做处理
        if (StringUtils.isEmpty(date)){
            date="0-0-0";
        }
        Timestamp time = Timestamp.valueOf(date+" 0:0:0");
        return time;
    }

    public static Timestamp addOneDay(Timestamp day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.add(Calendar.DATE,1);  //  加一  天
        //c.add(Calendar.MONTH,1); //  加一个月
        //c.add(Calendar.YEAR,1); //  加一  年
        return new Timestamp(calendar.getTimeInMillis());
    }
}
