/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/16
 * @Time: 10:12
 */
package com.qun.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {
    private int uid;
    private String uname;
    private String upwd;
    private String phone;
    private int gender;
    private int cardCounts;
    private String img;
    private List<Card> cards;
}
