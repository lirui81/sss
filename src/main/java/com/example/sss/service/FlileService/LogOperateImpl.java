package com.example.sss.service.FlileService;

import com.example.sss.dao.LogMapper;
import com.example.sss.model.domin.FileLog;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Title:
 * Description:
 *
 * @author： pop
 * @date： 2020/7/14 21:42
 * @vertion： V1.0.1
 */
public class LogOperateImpl implements LogOperate {
    @Autowired
    private LogMapper logMapper;

    //-------------------public---------------------

    /**
    * description:添加操作日志
    * @param log 日志
    */
    @Override
    public void addLog(FileLog log) {
        logMapper.addLog(log);
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
