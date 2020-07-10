package com.example.sss.dao;

import com.example.sss.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by beyondLi on 2017/6/19.
 */
@Repository
public interface UserMapper {
    User selectUserByName(String name);
}
