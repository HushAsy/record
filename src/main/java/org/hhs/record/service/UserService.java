package org.hhs.record.service;

import org.hhs.record.dao.mapper.BaseMapper;
import org.hhs.record.dao.mapper.UserMapper;
import org.hhs.record.dao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends BaseService<User>{
    @Autowired
    private UserMapper userMapper;

    public BaseMapper getMapper() {
        return userMapper;
    }
}
