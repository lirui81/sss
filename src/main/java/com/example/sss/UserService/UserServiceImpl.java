package com.example.sss.UserService;

import com.example.sss.dao.UserMapper;
import com.example.sss.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    //依赖注入
    @Autowired
    UserMapper userMapper;

    public User cs() {
        //调用dao层
        User user = userMapper.selectUserByName("admin");
        return user;
    }
}
