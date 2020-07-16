package com.example.sss.service.FlileService;

import com.example.sss.dao.LogMapper;
import com.example.sss.model.domin.FileLog;
import com.example.sss.model.domin.ObsFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

/**
 * Title:
 * Description:
 *
 * @author： pop
 * @date： 2020/7/14 21:42
 * @vertion： V1.0.1
 */
@Transactional
@Service
public class LogOperateImpl implements LogOperate {
    @Autowired
    private LogMapper logMapper;

    //-------------------public---------------------

    /**
    * description:添加操作日志
    * @param file 操作文件信息
    */
    @Override
    public void addLog(ObsFile file){
        logMapper.addLog(new FileLog(file.getFileId(),file.getUserId(),new Date(),"添加文件"));
    }

    /**
     * description:删除操作日志
     * @param file 操作文件信息
     */
    @Override
    public void deleteLog(ObsFile file){
        logMapper.addLog(new FileLog(file.getFileId(),file.getUserId(),new Date(),"删除文件"));
    }

    /**
     * description:重命名操作日志
     * @param file 操作文件信息
     */
    @Override
    public void reNameLog(ObsFile file){
        logMapper.addLog(new FileLog(file.getFileId(),file.getUserId(),new Date(),"重命名文件"));
    }

    /**
     * description:复制操作日志
     * @param file 操作文件信息
     */
    @Override
    public void copyLog(ObsFile file){
        logMapper.addLog(new FileLog(file.getFileId(),file.getUserId(),new Date(),"复制文件"));
    }

    /**
     * description:移动操作日志
     * @param file 操作文件信息
     */
    @Override
    public void movLog(ObsFile file){
        logMapper.addLog(new FileLog(file.getFileId(),file.getUserId(),new Date(),"移动文件"));
    }

    /**
    * description:返回用户的所有操作的日志
    * @param userId 用户id
    * @return 用户的所有操作列表
    */
    @Override
    public List<FileLog> selectLogByUserId(Integer userId) {
        return logMapper.selectLogByUserId(userId);
    }
}
