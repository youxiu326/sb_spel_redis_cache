package com.youxiu326.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.youxiu326.dto.UserView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lihui
 * @Date: 2020-11-14 14:05
 * @Description:
 */
@RestController
@RequestMapping("/jsonview/")
public class JsonViewController {

    @RequestMapping("/simple/user")
    @JsonView(UserView.UserSimpleView.class)
    public UserView simple(){

        UserView userView = new UserView();
        userView.setId(9L);
        userView.setUsername("hpc");
        userView.setUserpassword("123456");
        return userView;
    }

    @RequestMapping("/detail/user")
    @JsonView(UserView.UserDetailView.class)
    public UserView detail(){
        UserView userView = new UserView();
        userView.setId(9L);
        userView.setUsername("hpc");
        userView.setUserpassword("123456");
        return userView;
    }

}
