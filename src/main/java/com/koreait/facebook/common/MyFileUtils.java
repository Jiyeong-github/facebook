package com.koreait.facebook.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class MyFileUtils {
    @Value("${spring.servlet.multipart.location}")
    private String uploadImagePath; //사진 경로

    //폴더 만들기
    public void makeFolders(String path) {
        File folder = new File(path); //path에 target 값이 들어옴
        folder.mkdirs();
    }

    //저장 경로 만들기
    public String getSavePath(String path) {
        return uploadImagePath + "/" + path;
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm() {
        return UUID.randomUUID().toString();
    }

    //랜덤 파일명(+확장자) 만들기
    public String getRandomFileNm(String originFileNm) {
        return getRandomFileNm() + "." + getExt(originFileNm);
    }

    //랜덤 파일명 만들기
    public String getRandomFileNm(MultipartFile file) {
        return getRandomFileNm(file.getOriginalFilename());
    }

    //확장자 얻기
    public String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf(".") + 1);
    }

    //파일 저장&랜덤 파일명 리턴           target = "profile/10"
    public String transferTo(MultipartFile mf, String target){
        String fileNm = getRandomFileNm(mf);
        String basePath = getSavePath(target); //이미지를 저장할 절대경로값 생성
        makeFolders(basePath); //폴더를 만들어 준다
        File file = new File(basePath, fileNm);

        try{
            mf.transferTo(file);
            return fileNm;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
