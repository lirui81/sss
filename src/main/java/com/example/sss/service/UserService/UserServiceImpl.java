package com.example.sss.service.UserService;

import com.example.sss.dao.UserMapper;
import com.example.sss.model.domin.User;
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


}
