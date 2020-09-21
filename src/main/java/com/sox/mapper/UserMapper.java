package com.sox.mapper;


import com.sox.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    User getAccountByUsername(String username);
    int insertAccount(User user);
    int updateAccount(User user);
    int updatePassword(Map<String,String> map);

    @Select("select count(*) from blog.subscription where subFromID = #{subFromID} and subToID = #{subToID}")
    int selectSubToID(Map<String,String> map);

    List<User> selectSubByID(Map<String,String> map);

    @Insert("insert into blog.subscription(subFromID,subToID) values(#{subFromID},#{subToID})")
    int insertSubscriptionPair(Map<String,String> map);

    @Delete("delete from blog.subscription where subFromID = #{subFromID} and subToID = #{subToID}")
    int deleteSubscriptionPair(Map<String,String> map);
}
