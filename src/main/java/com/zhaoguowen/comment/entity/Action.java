package com.zhaoguowen.comment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 系统表-操作表，权限拦截用，存放系统里全部动作。
 * </p>
 *
 * @author zhaoguowen
 * @since 2018-11-19
 */
@TableName("sys_action")
@ToString
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 动作名称
     */
    @TableField("name")
    private String name;

    /**
     * 动作地址
     */
    @TableField("url")
    private String url;

    @TableField("menu_id")
    private Integer menuId;

    @TableField("method")
    private String method;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


}
