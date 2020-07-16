package com.example.sss.service.UserService;

import com.example.sss.model.domin.User;
import io.swagger.models.auth.In;

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
    int addUser(User user);
    /**
     * 修改用户状态
     * @param user
     * @return User
     *
     */
    void changeState(User user);
    /**
     * 修改用户密码
     * @param user
     * @return User
     *
     */
    void uppsw(User user);
    /**
     * 删除用户
     * @param id
     * @return User
     *
     */
    void deleteUser(Integer id);

    void upemail(User user);

    void forgetpwd(User user);

    void upphone(User user);
}
