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
import java.sql.Timestamp;

@Data
public class Card implements Serializable {
    private long cid;
    private double amount;
    private Timestamp date;
}
