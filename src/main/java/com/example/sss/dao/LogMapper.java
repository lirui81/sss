package com.example.sss.dao;

import com.example.sss.model.domin.FileLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title:
 * Description:这是一个操作数据库obs_log表的mapper
 *
 * @author： pop
 * @date： 2020/7/14 21:27
 * @vertion： V1.0.1
 */
@Repository
public interface LogMapper {
    void addLog(FileLog log);
    List<FileLog> selectLogByUserId(Integer userId);
}
