package com.zhaoguowen.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;


/**
 * <p>
 * <p>
 * </p>
 *
 * @author zhaoguowen
 * @since 2018-11-22
 */
public class LiveUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "LiveUser{" +
                "id=" + id +
                ", username=" + username +
                ", nickname=" + nickname +
                "}";
    }
}
