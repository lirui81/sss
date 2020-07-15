package com.example.sss.controller;


import com.example.sss.model.domin.ObsFile;
import com.example.sss.model.domin.User;
import com.example.sss.model.utils.ObsPage;
import com.example.sss.service.FlileService.FileService;
import com.example.sss.service.ObsService.ObsService;
import com.example.sss.service.UserService.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Api(tags = "文件管理接口")
@RequestMapping("/files")
@RestController
public class FilesController {
    @Autowired
    private FileService fileService;
    @Autowired
    private ObsService obsService;

    @PostMapping("/upload")
    @ResponseBody
    public int uploadOne(@RequestParam("file") MultipartFile fileUpload,Integer id){
        //当前用户id
        System.out.println("创建者："+id);
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        System.out.println("文件名："+fileName);
        //获取文件后缀名  根据这个填类型
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀名："+suffixName);
        //创建时间：new Date()
        //状态：1
        //路径：固定格式吧，用户的id上面有
        long size=fileUpload.getSize()/1024;  //单位:kb
        System.out.println("文件大小："+size);

        //将MultipartFile转换成inputStream    obs上上传用inputStream（百度）

        //上传obs
        //obsService.上传

        //数据库添加文件
        //fileService.addFile();

        //添加log记录
        return 1;
    }

    /**
     * 从数据库查询文件列表
     * @return
     */
    @PostMapping("/slectFilesList")
    public ObsPage slectFilesList(@RequestBody ObsFile obsFile){
        //obsFile里包含创建人和类型，根据这个去查数据库
        ObsPage obsPage =new ObsPage();
        return obsPage;
    }

    /**
     * 删除文件
     * @return
     */
    @PostMapping("/deleteFile")
    public int  deleteFile(@RequestBody ObsFile obsFile){
        //obsFile里包含id,Obs路径
        //删数据库
        //删obs
        //添加log记录
        return 1;
    }
    /**
     * 重命名文件
     * @return
     */
    @PostMapping("/rename")
    public int  rename(@RequestBody ObsFile obsFile){
        //obsFile里包含id,Obs路径,名称
        //改数据库
        //改obs
        //添加log记录
        return 1;
    }
    /**
     * 全局搜素文件
     * @return
     */
    @PostMapping("/selectFile")
    public ObsPage  selectFile(@RequestBody String s){
        //s是字符串
        //模糊查询文件名，路径
        ObsPage obsPage =new ObsPage();
        return obsPage;
    }
    /**
     * 预览暂时等待刘磊完成
     * @return
     */
}
