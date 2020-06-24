/**
 * Created by IntelliJ IDEA.
 *
 * @Author: LiQun
 * @Date: 2020/6/16
 * @Time: 10:12
 */
package com.qun.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
public class User implements Serializable ,Comparable<User> {
    private int uid;
    @Length(min = 2,message = "{name}")
    private String uname;
    @Length(min = 6,message = "{password}")
    private String upwd;
    private String permission = "User";
    @Length(min = 11,max = 12,message = "{phone}")
    private String phone;
    private int gender;
    private int cardCounts;
    private String img;
    private List<Card> cards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uid == user.uid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public int compareTo(User user) {
        return this.uid-user.uid;
    }

}
