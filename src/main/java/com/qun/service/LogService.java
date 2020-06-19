/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/19
 * @Time: 10:51
 */
package com.qun.service;

import com.qun.pojo.Log;
import com.qun.utils.TimeUtil;
import org.thymeleaf.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

public class LogService {
    public static List<Log> queryLogsByCid(List<Log> logs, long cid, String t1,String t2){

        Timestamp d1 = TimeUtil.stringToStamp(t1);
        Timestamp d2 = TimeUtil.stringToStamp(t2);

        if (StringUtils.isEmpty(t1)){
            d1 = new Timestamp(0);
        }else if (StringUtils.isEmpty(t2)){
            d2 = new Timestamp(System.currentTimeMillis());
        }

        d2=TimeUtil.addOneDay(d2);

        //如果没有输入日期，就只按卡号查询
        if (StringUtils.isEmpty(t1+t2)){
            return logs;
        }else {
            for (int i = 0; i < logs.size(); i++) {
                if (logs.get(i).getDate().before(d1)||logs.get(i).getDate().after(d2)){
                    logs.remove(i);
                    i--;
                }

            }
            return logs;
        }
    }
}
