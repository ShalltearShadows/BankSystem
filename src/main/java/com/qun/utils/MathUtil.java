/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/22
 * @Time: 15:53
 */
package com.qun.utils;

import java.math.BigDecimal;

public class MathUtil {
    public static int compare(double double1, double double2)  {
        BigDecimal data1 = new BigDecimal(double1);
        BigDecimal data2 = new BigDecimal(double2);
        return data1.compareTo(data2);
    }
}
