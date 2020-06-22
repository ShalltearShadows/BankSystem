/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/18
 * @Time: 16:22
 */
package com.qun.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Log implements Serializable {
    private long lid;
    private int uid;
    private Long cid1;
    private Long cid2;
    private double amount;
    private String operation;
    private Timestamp date;
}
