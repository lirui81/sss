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

    /**
     * description:根据路径获取下面的文件列表
     * @param file 文件对象 里包含userId，文件夹路径
     * @return 该文件夹下的所有文件（不包括子文件夹里的文件）
     */
    List<ObsFile> selectFileListByPath(ObsFile file);

    /**
     * description:根据类型获取文件列表
     * @param file 文件对象 包含用户id和文件类型
     * @return 该类型的所有文件
     */
    List<ObsFile> selectFileListByType(ObsFile file);

    /**
     * description:根据提供的字全局搜索文件
     * @param file 文件信息，filename里面存放关键字
     * @return 搜索到的结果列表
     */
    List<ObsFile> selectFileListByName(ObsFile file);

    /**
    * description:通过文件名判断文件类型
    * @param name 文件名
    * @return 文件类型
    */
    String getType(String name);

    /**
     * description:复制文件
     * @param file 文件信息，原路径放在name、新路径放在path
     */
    void copyFile(ObsFile file);

    /**
     * description:向数据库添加文件信息
     * @param file 文件信息
     */
    void addFile(ObsFile file);

    /**
     * description:从数据库删除文件/文件夹
     * @param file 被删除的文件 包括文件id，用户id和path
     */
    void deleteFile(ObsFile file);

    /**
     * description:更改文件命
     * @param file 文件信息，fileId userId 原路径放在name、新路径放在path
     */
    void updateFileName(ObsFile file);

    /**
     * description:更改文件路径，即移动文件
     * @param file 更新的文件信息，fileId,  path里面存放的是目标路径
     */
    void updateFilePath(ObsFile file);
}
