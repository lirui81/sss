package com.example.sss.service.UserService;

import com.example.sss.dao.UserMapper;
import com.example.sss.model.domin.User;
import com.example.sss.service.ObsService.ObsService;
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
    @Autowired
    ObsService obsService;

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
    //添加用户
    @Override
    @Transactional
    public int addUser(User user) {
        User user1=new User();
        user1.setUserId(user.getUserId());
        //判断用户名是否重复
        List<User> users =userMapper.selectUsersList(user1);
        if(users.size()<1){
            try{
                userMapper.addUser(user);
                //为用户创建桶
                obsService.createBucket(user.getId());
                return 1;
            }catch(Exception e){
                e.printStackTrace();
                return 0;
            }
        }else{
            return 2;
        }
    }

    @Override
    public void changeState(User user) {
        userMapper.updateState(user);
    }

    @Override
    public void uppsw(User user) {
        userMapper.uppsw(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUserById(id);
        //删除该用户的桶
        obsService.deleteBucket(id);
    }

    @Override
    public void upemail(User user) {
        userMapper.upemail(user);
    }

    @Override
    public void forgetpwd(User user) {
        userMapper.forgetpwd(user);
    }

    @Override
    public void upphone(User user) {
        userMapper.upphone(user);
    }

}
