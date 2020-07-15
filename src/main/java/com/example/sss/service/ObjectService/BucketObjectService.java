package com.example.sss.service.ObjectService;

import com.obs.services.exception.ObsException;
import com.obs.services.model.ObsObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface BucketObjectService {
    Integer uploadFile(Integer id,InputStream is, String objectKey) throws IOException;//上传
    List<ObsObject> getAllFileInfo(Integer id) throws IOException;//获取所有文件
    Boolean removeFile(Integer id,String objectKey) throws IOException;//删除
    String getFile(Integer id,String objectKey);//下载
    String preview(Integer id,String objectKey) throws IOException;//预览
    void move(Integer id,String objectKey,String desbjectKey) throws IOException;//重命名
    void rename(Integer id,String objectKey,String newbjectKey) throws IOException;
    void copy(Integer id,String objectKey,String desbjectKey)throws IOException;
    void newFolder(Integer id,String objectKey)throws IOException;
    void DropFolder(Integer id,String objectKey)throws IOException;
}
