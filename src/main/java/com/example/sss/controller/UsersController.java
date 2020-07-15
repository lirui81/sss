package com.example.sss.controller;


import com.example.sss.model.domin.User;
import com.example.sss.model.utils.ObsPage;
import com.example.sss.service.UserService.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(tags = "用户管理接口")
@RequestMapping("/users")
@RestController
public class UsersController {
    @Autowired
    private UserService userService;


    /**
     * 查询用户列表
     * @param user
     * @return
     */
    @RequestMapping(value="/selectUserList",method= RequestMethod.POST)
    public ObsPage selectUserList(@RequestBody User user) {
        ObsPage user1 = new ObsPage();
        user1.setData(userService.selectUsers(user));
        user1.setTotal(user1.getData().size());
        return user1;
    }
    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
    /**
     * 修改用户状态
     * @param user
     * @return
     */
    @RequestMapping(value="/changeState",method= RequestMethod.POST)
    public int changeState(@RequestBody User user) {
        userService.changeState(user);
        return 1;
    }
    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteUser",method= RequestMethod.POST)
    public int deleteUser(@RequestBody Integer id) {
        userService.deleteUser(id);
        return 1;
    }


}
