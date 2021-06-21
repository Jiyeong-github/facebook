package com.koreait.facebook.facebook.user;

import com.koreait.facebook.facebook.user.model.UserEntity;
import org.apache.catalina.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

    public int join(UserEntity param){
        //비밀번호 암호화
        String hashedPw = BCrypt.hashpw(param.getPw(),BCrypt.gensalt());
        param.setPw(hashedPw);
        return mapper.join(param);
    }

    public int login(UserEntity param){
        
    return 0;
    }

}
