package com.example.sss.model.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Title:
 * Description:文件操作日志
 *
 * @author： pop
 * @date： 2020/7/14 21:23
 * @vertion： V1.0.1
 */
public class FileLog {
    private Integer fileId;
    private Integer logId;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;
    private String operateType;
    private Integer userId;

    public FileLog(){ }

    public FileLog(Integer fileId, Date operateTime, String operateType, Integer userId) {
        this.fileId = fileId;
        this.operateTime = operateTime;
        this.operateType = operateType;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
