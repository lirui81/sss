package com.example.sss.controller;


import com.example.sss.model.domin.ObsFile;
import com.example.sss.model.domin.User;
import com.example.sss.model.utils.ObsPage;
import com.example.sss.service.FlileService.FileService;
import com.example.sss.service.FlileService.LogOperate;
import com.example.sss.service.ObjectService.BucketObjectService;
import com.example.sss.service.ObsService.ObsService;
import com.example.sss.service.UserService.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


@Api(tags = "文件管理接口")
@RequestMapping("/files")
@RestController
public class FilesController {
    @Autowired
    private FileService fileService;
    @Autowired
    private BucketObjectService bucketObjectService;
    @Autowired
    private LogOperate logOperate;

    @PostMapping("/upload")
    @ResponseBody
    public int uploadOne(@RequestParam("file") MultipartFile fileUpload,Integer id,String path) throws IOException {
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名  根据这个填类型
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        if (path==null){
            path="";
        }
        String absolutePath=path+fileName;
        double size=fileUpload.getSize();  //单位:b
        System.out.println("文件路径："+absolutePath);

        //将MultipartFile转换成inputStream
        InputStream inputStream = null;
        File file = null;
        file = File.createTempFile("temp", null);
        fileUpload.transferTo(file);
        inputStream = new FileInputStream(file);
        file.deleteOnExit();
        //上传obs
        bucketObjectService.uploadFile(id,inputStream,absolutePath);

        //数据库添加文件
        ObsFile obsFile=new ObsFile();
        obsFile.setUserId(id);
        obsFile.setType(fileService.getType(suffixName));
        if (size/1048576000>=1){
            obsFile.setSize(String.format("%.2f", size/1073741824) +" GB");
        }else if (size/1024000>=1){
            obsFile.setSize(String.format("%.2f", size/1048576) +" MB");
        }else if(size/1000>=1){
            obsFile.setSize(fileUpload.getSize()/1024 +" KB");
        }else {
            obsFile.setSize(fileUpload.getSize() +" B");
        }

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
        List<ObsFile> list=fileService.selectFileListByType(obsFile);
        return new ObsPage(list.size(),list);
    }

    /**
     * 根据路径获取下面的文件列表
     * @return
     */
    @PostMapping("/slectFileList")
    public ObsPage slectFileList(@RequestBody ObsFile obsFile){
        if (obsFile.getPath()==null){
            obsFile.setPath("");
        }
        //obsFile里包含创建人，文件夹路径
        System.out.println("文件路径："+obsFile.getPath());
        List<ObsFile> list=fileService.selectFileListByPath(obsFile);
        return new ObsPage(list.size(),list);
    }

    /**
     * 删除文件
     * @return
     */
    @PostMapping("/deleteFile")
    public int deleteFile(@RequestBody ObsFile obsFile) throws IOException {
        if("文件夹".equals(obsFile.getType())){
            //删obs
            bucketObjectService.DropFolder(obsFile.getUserId(),obsFile.getPath());
        }else{
            bucketObjectService.removeFile(obsFile.getUserId(),obsFile.getPath());
        }
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
    public int  rename(@RequestBody ObsFile obsFile) throws IOException {
        System.out.println(obsFile.getFileName());
        System.out.println(obsFile.getPath());
        //改obs
        bucketObjectService.rename(obsFile.getUserId(),obsFile.getFileName(),obsFile.getPath());
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
        List<ObsFile> list=fileService.selectFileListByName(obsFile);
        System.out.println(list.size());
        return new ObsPage(list.size(),list);
    }

    /**
     * 预览
     * @return
     */
    @PostMapping("/preview")
    public String preview(@RequestBody ObsFile obsFile ) throws IOException {
        return bucketObjectService.preview(obsFile.getUserId(),obsFile.getPath());
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
        fileService.copyFile(obsFile);
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
    public String addFloder(@RequestBody ObsFile obsFile ) throws IOException {
        //obs 移动  id、文件名
        bucketObjectService.newFolder(obsFile.getUserId(),obsFile.getPath());
        //数据库操作
//        if (!obsFile.getPath().endsWith("/"))
//            obsFile.setPath(obsFile.getPath()+'/');
        obsFile.setType("文件夹");
        fileService.addFile(obsFile);
        //添加log记录
        logOperate.addLog(obsFile);
        return "1";
    }

}
