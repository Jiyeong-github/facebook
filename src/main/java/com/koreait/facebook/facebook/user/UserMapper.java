package com.koreait.facebook.facebook.user;

import com.koreait.facebook.facebook.user.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int join(UserEntity param);
}
