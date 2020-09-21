package com.sox.service;

import com.sox.mapper.UserMapper;
import com.sox.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getAccountByUsername(String username) {
        return userMapper.getAccountByUsername(username);
    }

    @Override
    public int insertAccount(User user) {
        return userMapper.insertAccount(user);
    }

    @Override
    public int updateAccount(User user) {
        return userMapper.updateAccount(user);
    }

    @Override
    public int updatePassword(Map<String,String> map) {
        return userMapper.updatePassword(map);
    }

    @Override
    public int selectSubToID(Map<String,String> map) {
        return userMapper.selectSubToID(map);
    }

    @Override
    public List<User> selectSubByID(Map<String, String> map) {
        return userMapper.selectSubByID(map);
    }

    @Override
    public int deleteSubscriptionPair(Map<String, String> map) {
        return userMapper.deleteSubscriptionPair(map);
    }

    @Override
    public int insertSubscriptionPair(Map<String, String> map) {
        return userMapper.insertSubscriptionPair(map);
    }
}
