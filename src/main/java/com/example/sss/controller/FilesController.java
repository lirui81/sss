package com.example.sss.controller;


import com.example.sss.model.domin.ObsFile;
import com.example.sss.model.domin.User;
import com.example.sss.model.utils.ObsPage;
import com.example.sss.service.FlileService.FileService;
import com.example.sss.service.FlileService.LogOperate;
import com.example.sss.service.ObsService.ObsService;
import com.example.sss.service.UserService.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;


@Api(tags = "文件管理接口")
@RequestMapping("/files")
@RestController
public class FilesController {
    @Autowired
    private FileService fileService;
    @Autowired
    private ObsService obsService;
    @Autowired
    private LogOperate logOperate;
    /**
     *
     * 统一说明，log类的mapper之类的文件暂时还没有建
     * 有时间就建一下，只需要添加和查询
     * 没时间我们可以先放一放，暂时前端没有显示日志记录
     *
     */

    @PostMapping("/upload")
    @ResponseBody
    public int uploadOne(@RequestParam("file") MultipartFile fileUpload,Integer id,String path){
        //当前用户id
        System.out.println("创建者："+id);
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        System.out.println("文件名："+fileName);
        //获取文件后缀名  根据这个填类型
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件后缀名："+suffixName);
        System.out.println("文件路径："+path);
        if (!"null".equals(path)){
            path="";
        }
        //创建时间：new Date()
        //状态：1
        //路径：固定格式
        //path + [文件名]
        String absolutePath=path+fileName;
        long size=fileUpload.getSize()/1024;  //单位:kb
        System.out.println("文件大小："+size);

        //将MultipartFile转换成inputStream    obs上上传用inputStream（百度）

        //上传obs
        //obsService.上传

        //数据库添加文件
        ObsFile obsFile=new ObsFile();
        obsFile.setUserId(id);
        obsFile.setType(fileService.getType(suffixName));
        obsFile.setSize(Long.toString(size) +"KB");
        obsFile.setFileState(1);
        obsFile.setMakeTime(new Date());
        obsFile.setFileName(fileName);
        obsFile.setPath(absolutePath);
        fileService.addFile(obsFile);
        //添加log记录
        logOperate.addLog(obsFile);
        return 1;
    }

    /**
     * 从数据库查询文件列表
     * @return
     */
    @PostMapping("/slectFilesList")
    public ObsPage slectFilesList(@RequestBody ObsFile obsFile){
        //obsFile里包含创建人和类型，根据这个去查数据库
        System.out.println("从数据库查询文件列表");
        List<ObsFile> list=fileService.selectFileListByType(obsFile);
        System.out.println(list.size());
        return new ObsPage(list.size(),list);
    }

    /**
     * 根据路径获取下面的文件列表
     * @return
     */
    @PostMapping("/slectFileList")
    public ObsPage slectFileList(@RequestBody ObsFile obsFile){
        //obsFile里包含创建人，文件夹路径
        System.out.println("根据路径获取下面的文件列表");
        System.out.println("文件路径："+obsFile.getPath());
        List<ObsFile> list=fileService.selectFileListByPath(obsFile);
        System.out.println(list.size());
        return new ObsPage(list.size(),list);
    }

    /**
     * 删除文件
     * @return
     */
    @PostMapping("/deleteFile")
    public int  deleteFile(@RequestBody ObsFile obsFile){
        //obsFile里包含id,Obs路径
        //删obs
        //删数据库
        fileService.deleteFile(obsFile);
        //添加log记录
        logOperate.deleteLog(obsFile);
        return 1;
    }

    /**
     * 重命名文件
     * @return
     */
    @PostMapping("/rename")
    public int  rename(@RequestBody ObsFile obsFile){
        //obs 重命名  id、文件名  obsFile
        // 目的文件名 为了方便，我暂时放在 obsFile的path
        //改obs
        //改数据库
        fileService.updateFileName(obsFile);
        //添加log记录
        logOperate.reNameLog(obsFile);
        return 1;
    }
    /**
     * 全局搜素文件
     * @return
     */
    @PostMapping("/selectFile")
    public ObsPage  selectFile(@RequestBody ObsFile obsFile){
        //userid  filename
        //模糊查询文件名，路径
        List<ObsFile> list=fileService.selectFileListByName(obsFile);
        System.out.println(list.size());
        return new ObsPage(list.size(),list);
    }

    /**
     * 预览
     * @return
     */
    @PostMapping("/preview")
    public String preview(@RequestBody ObsFile obsFile ){
        //obs预览调用
        //obsFile  包含id、文件名（带后缀）
        return null;
    }
    /**
     * 复制
     * @return
     */
    @PostMapping("/copy")
    public String copy(@RequestBody ObsFile obsFile ){
        //obsFile：  id、文件名
        // 目的文件名 为了方便，我暂时放在 obsFile的path
        //1、文件名与目的文件名一样
            //修改文件名（名字后面加1、2、3...）  数据库查询该文件名 数量
            //数据库添加文件
            //obs 复制
        //2、文件名与目的文件名不一样
            //数据库添加文件
            //obs 复制
        //添加log记录
        logOperate.copyLog(obsFile);
        return null;
    }

    /**
     * 移动
     * @return
     */
    @PostMapping("/move")
    public String move(@RequestBody ObsFile obsFile ){
        //obs 移动  id、文件名  obsFile
        // 目的文件名 为了方便，我暂时放在 obsFile的path

        //修改数据库
        fileService.updateFilePath(obsFile);
        //添加log记录
        logOperate.movLog(obsFile);
        return null;
    }
    /**
     * 添加文件夹
     * @return
     */
    @PostMapping("/addFloder")
    public String addFloder(@RequestBody ObsFile obsFile ){
        //obs 移动  id、文件名

        //数据库操作
        if (!obsFile.getPath().endsWith("/"))
            obsFile.setPath(obsFile.getPath()+'/');
        obsFile.setType("文件夹");
        fileService.addFile(obsFile);
        //添加log记录
        selectFile(obsFile);
        return "1";
    }

}
