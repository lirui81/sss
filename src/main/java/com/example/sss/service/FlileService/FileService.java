package com.example.sss.service.FlileService;

import com.example.sss.model.domin.ObsFile;

import java.util.List;

/**
 * Title:
 * Description:文件操作，对文件进行增删改查的时候需要用到
 *
 * @author： pop
 * @date： 2020/7/13 22:19
 * @vertion： V1.0.1
 */
public interface FileService {
    List<ObsFile> selectFileListByPath(Integer userId, String path);
    void addFile(ObsFile file);
    void deleteFile(Integer userId,String path);
    void updateFileName(Integer userId,String path,String newName);
    void updateFilePath(Integer userId,String oldPath,String newPath);
}
