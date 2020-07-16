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

    /**
     * @param
     * @return
     */
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public User login(@RequestBody User user) {
        User user1 = userService.login(user);
        return user1;
    }
    /**
     * @param
     * @return
     */
    @RequestMapping(value="/uppsw",method= RequestMethod.POST)
    public int uppsw(@RequestBody User user) {
        userService.uppsw(user);
        return 1;
    }
    @RequestMapping(value="/upemail",method= RequestMethod.POST)
    public int upemail(@RequestBody User user) {
        userService.upemail(user);
        return 1;
    }
    @RequestMapping(value="/forgetpwd",method= RequestMethod.POST)
    public int forgetpwd(@RequestBody User user) {
        userService.forgetpwd(user);
        return 1;
    }
    @RequestMapping(value="/upphone",method= RequestMethod.POST)
    public int upphone(@RequestBody User user) {
        userService.upphone(user);
        return 1;
    }
}
