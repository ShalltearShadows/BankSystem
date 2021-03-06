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
import java.text.DecimalFormat;

@Data
public class Card implements Serializable {
    private long cid;
    private int uid;
    private double amount;
    private Timestamp date;
    private DecimalFormat df = new DecimalFormat("#.00");

    public void setAmount(double amount) {
        this.amount = Double.parseDouble(df.format(amount));
    }
}
