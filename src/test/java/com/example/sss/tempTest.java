package com.example.sss;


import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

/**
 * Title:
 * Description:临时简单测试用的
 *
 * @author： pop
 * @date： 2020/7/14 19:24
 * @vertion： V1.0.1
 */
public class tempTest {
    private boolean isDir(String path){
        return path.endsWith("/");
    }

    public void updateFileName(Integer userId,String path,String newName){
        String[] result =path.split("/");
        String newPath = "";
        if (path.startsWith("https")){
            newPath = "https:/";
        }else{
            newPath = "http:/";
        }
        result[result.length-1]=newName;
        for (int i=2;i<result.length;i++){
            newPath +='/'+result[i];
        }
        if (isDir(path)){
            newPath+='/';
        }
        System.out.println(newPath);
    }

    public static void main(String[] args) {
        tempTest t=new tempTest();
        t.updateFileName(666, "https://sss-123.obs.cn-north-4.myhuaweicloud.com/myfile.doc/"
                , "newname.doc");
    }
}
