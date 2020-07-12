package com.example.sss.dao;

import com.example.sss.model.domin.User;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface UserMapper {
    User selectUserByName(String name);
    User login(User user);
}
