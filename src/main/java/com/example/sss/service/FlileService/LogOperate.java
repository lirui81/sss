package com.example.sss.service.FlileService;

import com.example.sss.model.domin.FileLog;

import java.util.List;

/**
 * Title:
 * Description:文件操作日志接口
 *
 * @author： pop
 * @date： 2020/7/14 21:41
 * @vertion： V1.0.1
 */
public interface LogOperate {
    void addLog(FileLog log);
    List<FileLog> selectLogByUserId(Integer userId);
}
