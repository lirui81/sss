package com.example.sss.dao;

import com.example.sss.model.domin.User;
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
}
