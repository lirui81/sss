package com.example.sss.dao;

import com.example.sss.model.domin.ObsFile;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
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
    List<ObsFile> selectFileListByPath(@Param("userId") Integer userId,@Param("path") String path);
    List<ObsFile> selectFileListByType(@Param("userId") Integer userId,@Param("type") String type);
    List<ObsFile> selectFileListByName(@Param("userId") Integer userId,@Param("name") String name);
    ObsFile getFileByPath(@Param("userId") Integer userId,@Param("path") String path);
    ObsFile getFileByFileId(Integer id);
    void addFile(ObsFile file);
    void deleteFile(Integer id);
    void deleteDir(@Param("userId") Integer userId,@Param("path") String path);
    void updateFileName(@Param("id") Integer id,@Param("newPath") String newPath,@Param("newName") String newName);
    void updateFilePath(@Param("id") Integer id,@Param("newPath") String newPath);
}
