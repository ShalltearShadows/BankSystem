/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/16
 * @Time: 10:50
 */
package com.qun.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class Card implements Serializable {
    private Long cid;
    private float amount;
    private Date date;
}
