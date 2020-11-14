package com.youxiu326.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 * @Auther: lihui
 * @Date: 2020-11-14 14:01
 * @Description: 用户VO
 */
public class UserView implements Serializable {

    /**
     * 用户简单视图
     */
    public interface UserSimpleView{};

    /**
     * 用户详情视图
     */
    public interface UserDetailView extends UserSimpleView{}

    private Long id;

    private String username;

    private String userpassword;

    @JsonView(UserSimpleView.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
