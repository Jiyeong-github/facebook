package com.koreait.facebook.user;

import com.koreait.facebook.common.EmailService;
import com.koreait.facebook.common.MyFileUtils;
import com.koreait.facebook.common.MySecurityUtils;
import com.koreait.facebook.security.IAuthentificationFacade;
import com.koreait.facebook.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
    @Autowired
    private IAuthentificationFacade auth;

    @Autowired
    private MyFileUtils myFileUtils;

    @Autowired
    private EmailService email;

    @Autowired
    private MySecurityUtils secUtils;

    @Autowired
    private UserMapper mapper;

    public int join(UserEntity param) {
        String authCd = secUtils.getRandomDigit(5);

        //비밀번호 암호화
        String hashedPw = BCrypt.hashpw(param.getPw(), BCrypt.gensalt());
        param.setPw(hashedPw);
        param.setAuthCd(authCd);
        int result = mapper.join(param);

        if (result == 1) { //메일 쏘기!! (id, authcd값을 메일로 쏜다.)
            String subject = "[얼굴책] 인증메일입니다.";
            String txt = String.format("<a href=\"http://localhost:8090/user/auth?email=%s&authCd=%s\">인증하기</a>"
                    , param.getEmail(), authCd);
            email.sendMimeMessage(param.getEmail(), subject, txt);
        }
        return result;
    }

    //이메일 인증 처리
    public int auth(UserEntity param) {
        return mapper.auth(param);
    }

    //로그인
    public String login(UserEntity param) {
        UserEntity loginUser = mapper.selUser(param);
        return "";
    }

    public void profileImg(MultipartFile[] imgArr) {
        int iuser = auth.getLoginUserPk();
        System.out.println("iuser:" + iuser);
        String target = "/profile" + iuser;

        for (MultipartFile img : imgArr) {
            String saveFileNm = myFileUtils.transferTo(img, target);
        }
    }
}