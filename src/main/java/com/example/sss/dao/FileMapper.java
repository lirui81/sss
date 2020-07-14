package com.example.sss.dao;

import com.example.sss.model.domin.ObsFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:
 * Description:这是一个操作数据库obs_file表的mapper
 *
 * @author： pop
 * @date： 2020/7/14 13:46
 * @vertion： V1.0.1
 */
@Repository
public interface FileMapper {
    List<ObsFile> selectFileListByPath(Integer userId, String path);
    List<ObsFile> selectFileListByType(Integer userId, String type);
    void addFile(ObsFile file);
    void deleteFile(Integer userId,String path);
    void updateFileName(Integer userId,String path,String newPath, String newName);
    void updateFilePath(Integer userId,String oldPath,String newPath);
}
