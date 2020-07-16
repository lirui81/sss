package com.example.sss.dao;

import com.example.sss.model.domin.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public interface UserMapper {
    User selectUserByName(String name);
    User login(User user);
    List<User> selectUsersList(User user);
    void addUser(User user);
    void updateState(User user);
    void uppsw(User user);
    void deleteUserById(Integer id);

    void upemail(User user);

    void forgetpwd(User user);

    void upphone(User user);
}
