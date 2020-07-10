package com.example.sss.controller;


import com.example.sss.UserService.UserService;
import com.example.sss.model.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by beyondLi on 2017/6/19.
 */
@Api(tags = "一个测试的接口")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("test/cs")
    public User cs(@RequestBody String s) {
        System.out.println(s);
        User user = userService.cs();
        return user;
    }

}
