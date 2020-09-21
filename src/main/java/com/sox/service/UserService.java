package com.sox.service;

import com.sox.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getAccountByUsername(String username);
    int insertAccount(User user);
    int updateAccount(User user);
    int updatePassword(Map<String,String> map);
    int selectSubToID(Map<String,String> map);
    List<User> selectSubByID(Map<String,String> map);
    int deleteSubscriptionPair(Map<String,String> map);
    int insertSubscriptionPair(Map<String,String> map);
}
