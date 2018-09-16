package com.my.seckill.service;

import com.my.seckill.dao.UserDao;
import com.my.seckill.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tufei on 2018/9/11.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public boolean login(String userName, String password) {
        UserDTO userDTO = userDao.queryByUserName(userName);
        if(null != userDTO && userDTO.getPassword().equalsIgnoreCase(password)){
            return true;
        }
        return false;
    }
}
