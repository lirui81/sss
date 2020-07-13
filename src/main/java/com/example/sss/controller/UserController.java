package com.example.sss.controller;


import com.example.sss.service.UserService.UserService;
import com.example.sss.model.domin.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户个人信息接口")
@RequestMapping("/person")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/test")
    public User cs() {
        User user = userService.cs();
        return user;
    }

    /**
     * @param
     * @return
     */
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public User login(@RequestBody User user) {
        User user1 = userService.login(user);
        return user1;
    }


}
