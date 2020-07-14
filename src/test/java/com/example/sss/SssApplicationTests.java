package com.example.sss;

import com.example.sss.model.domin.FileLog;
import com.example.sss.model.domin.ObsFile;
import com.example.sss.service.FlileService.FileService;
import com.example.sss.service.FlileService.LogOperate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.List;

@SpringBootTest
class SssApplicationTests {

    @Autowired
    private FileService fileService;
    @Autowired
    private LogOperate logOperate;

    @Test
    void contextLoads() {
    }

    @Test
    void FileServiceTest(){
        ObsFile file=new ObsFile();
        String path="https://sss-123.obs.cn-north-4.myhuaweicloud.com/myfile.doc";
        file.setPath(path);
        file.setFileName("myfile.doc");
        file.setFileState(1);
        file.setMakeTime(new Date(2016));
        file.setSize("50MB");
        file.setType("文档");
        file.setUserId(1);
        System.out.println("-----开始测试：--------");
        fileService.addFile(file);
        logOperate.addLog(new FileLog(file.getFileId(),
                file.getMakeTimeTime(),
                "添加文件"+file.getFileName(),
                file.getUserId()
        ));
        file.setPath("https://sss-123.obs.cn-north-4.myhuaweicloud.com/mkdir/");
        file.setFileName("mkdir");
        file.setFileState(1);
        file.setMakeTime(new Date(2018));
        file.setSize("0KB");
        file.setType("文件夹");
        file.setUserId(1);
        fileService.addFile(file);
        logOperate.addLog(new FileLog(file.getFileId(),
                file.getMakeTimeTime(),
                "添加文件"+file.getFileName(),
                file.getUserId()
        ));
        String newPath="https://sss-123.obs.cn-north-4.myhuaweicloud.com/mkdir/myfile.doc";
        fileService.updateFilePath(1, path, newPath);
        logOperate.addLog(new FileLog(file.getFileId(),
                file.getMakeTimeTime(),
                "移动文件"+file.getFileName(),
                file.getUserId()
        ));
        fileService.updateFileName(1,newPath,"新的文件.doc" );
        logOperate.addLog(new FileLog(file.getFileId(),
                file.getMakeTimeTime(),
                "修改文件名"+file.getFileName(),
                file.getUserId()
        ));
        System.out.println("根据路径查询：");
        List<ObsFile> obsFiles=fileService.selectFileListByPath(1, "https://sss-123.obs.cn-north-4.myhuaweicloud.com/");
        for(ObsFile item:obsFiles){
            System.out.println(item.getPath());
        }
        System.out.println("根据类型查询：");
        obsFiles=fileService.selectFileListByType(1, "文档");
        for(ObsFile item:obsFiles){
            System.out.println(item.getPath());
        }
        fileService.deleteFile(1, "https://sss-123.obs.cn-north-4.myhuaweicloud.com/mkdir/新的文件.doc");
        logOperate.addLog(new FileLog(file.getFileId(),
                new Date(20220),
                "删除文件"+file.getFileName(),
                file.getUserId()
        ));
    }
}
