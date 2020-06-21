/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/16
 * @Time: 10:10
 */
package com.qun.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Admin implements Serializable {
    private int aid;
    private String apwd;
    private String aname;
    private int phone;
    private int gender;
    private String img;
}
