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
    private Integer id;

    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    private String operation;
    private Integer userId;

    public FileLog(){ }

    public FileLog(Integer fileId, Integer userId, Date time, String operation) {
        this.fileId = fileId;
        this.time = time;
        this.operation = operation;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
