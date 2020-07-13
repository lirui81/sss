package com.example.sss.service.UserService;

import com.example.sss.dao.UserMapper;
import com.example.sss.model.domin.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService{
    //依赖注入
    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user) {
        User user1 = new User();
        user = userMapper.login(user);
        if(user==null){
            user1.setStaus(false);
        }else{
            user1=user;
            user1.setStaus(true);
        }
        return user1;
    }

    @Override
    public List<User> selectUsers(User user) {

        List<User> users = userMapper.selectUsersList(user);
        return users;
    }
    @Override
    public void addUser(User user) {
       userMapper.addUser(user);
    }

}
