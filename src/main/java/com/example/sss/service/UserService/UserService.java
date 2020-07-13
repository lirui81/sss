package com.example.sss.service.UserService;

import com.example.sss.model.domin.User;

import java.util.List;

public interface UserService {
    User login(User user);
    /**
     * 用户列表查询
     * @param user
     * @return User
     *
     */
    List<User> selectUsers(User user);
    /**
     * 添加用户
     * @param user
     * @return User
     *
     */
    void addUser(User user);
}
