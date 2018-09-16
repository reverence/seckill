package com.my.seckill.dao;

import com.my.seckill.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    UserDTO queryByUserName(@Param("userName")String userName);
}
