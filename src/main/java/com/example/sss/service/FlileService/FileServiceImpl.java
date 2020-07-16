package com.example.sss.service.FlileService;

import com.example.sss.dao.FileMapper;
import com.example.sss.dao.LogMapper;
import com.example.sss.model.domin.FileLog;
import com.example.sss.model.domin.ObsFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Title:
 * Description:对数据库的文件进行操作
 *
 * @author： pop
 * @date： 2020/7/14 11:11
 * @vertion： V1.0.1
 */
@Transactional
@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private FileMapper fileMapper;

    //---------------------private--------------
    /**
    * description:判断是不是文件夹
    * @param path 路径
    * @return 是则返回true
    */
    private boolean isDir(String path){
        return path.endsWith("/");
    }

    /**
    * description:统计从index开始的 '/' 字符数量
    * @param str 被统计字符串
    * @param index 开始位置
    * @return '/' 的数量
    */
    private int count(String str,int index){
        int count=0;
        for(int i=index;i<str.length();i++){
            if (str.charAt(i)=='/'){
                ++count;
            }
        }
        return count;
    }

    /**
    * description:
    * @param list 文件列表
    * @param path 要对比的文件路径
    * @return 存在则返回true 不存在返回false
    */
    private boolean isExist(List<ObsFile> list,String path){
        for (ObsFile item:list) {
            if(item.getPath().equals(path)){
                return true;
            }
        }
        return false;
    }

    //---------------------public---------------

    /**
    * description:根据路径获取下面的文件列表
    * @param file 文件对象 里包含userId，文件夹路径
    * @return 该文件夹下的所有文件（不包括子文件夹里的文件）
    */
    @Override
    public List<ObsFile> selectFileListByPath(ObsFile file){
        if (file.getPath().isEmpty()||isDir(file.getPath())) {
            List<ObsFile> list = fileMapper.selectFileListByPath(file.getUserId(), file.getPath());
            for(int i=0;i<list.size();i++){
                int count=count(list.get(i).getPath(), file.getPath().length());
                //把path移除掉deleteFile
                if(isDir(list.get(i).getPath())&&count==0){
                    list.remove(list.get(i));
                    i--;
                }else{
                    //子文件夹下面的也移除掉
                    if (count!=0){
                        list.remove(list.get(i));
                        i--;
                    }
                }
            }
            return list;
        }
        else return null;
    }

    /**
    * description:根据类型获取文件列表
     * @param file 文件对象 包含用户id和文件类型
     * @return 该类型的所有文件
    */
    @Override
    public List<ObsFile> selectFileListByType(ObsFile file){
        return fileMapper.selectFileListByType(file.getUserId(), file.getType());
    }

    /**
    * description:根据提供的字全局搜索文件
    * @param file 文件信息，filename里面存放关键字
    * @return 搜索到的结果列表
    */
    public List<ObsFile> selectFileListByName(ObsFile file){
        return fileMapper.selectFileListByName(file.getUserId(), file.getFileName());
    }

    public String getType(String name){
        if (name.endsWith("/")){
            return "文件夹";
        }else if (name.endsWith("jpg")||name.endsWith("png")||name.endsWith("gif")){
            return "图片";
        }else if (name.endsWith("mp3")||name.endsWith("ape")||name.endsWith("wav")||name.endsWith("flac")){
            return "音乐";
        }else if(name.endsWith("doc")||name.endsWith("txt")||name.endsWith("docx")||name.endsWith("pdf")){
            return "文档";
        }else if (name.endsWith("mp4")||name.endsWith("avi")||name.endsWith("rmvb")||name.endsWith("mkv")){
            return "视频";
        }
        return "其他";
    }

    /**
    * description:向数据库添加文件信息
    * @param file 文件信息
    */
    @Override
    public void addFile(ObsFile file){
         fileMapper.addFile(file);
    }

    /**
    * description:复制文件
    * @param file 文件信息，原路径放在name、新路径放在path
    * @param dstPath 目标路径
    */
    @Override
    public void copyFile(ObsFile file,String dstPath){
        if(!isExist(fileMapper.selectFileByName(file.getUserId(), file.getFileName()), dstPath)){
//            file.setPath(dstPath+file.getFileName());
//            file.setMakeTime(new Date());
            fileMapper.addFile(file);
        }
    }

    /**
    * description:从数据库删除文件
    * @param file 被删除的文件
    */
    @Override
    public void deleteFile(ObsFile file){
        fileMapper.deleteFile(file.getPath());
    }

    /**
    * description:更改文件命
    * @param file 文件信息，原路径放在name、新路径放在path
    */
    @Override
    public void updateFileName(ObsFile file){
        int index=file.getPath().lastIndexOf('/');
        String name=file.getPath().substring(index+1);
        fileMapper.updateFileName(file.getFileId(),file.getPath(),name);
    }

    /**
    * description:更改文件路径，即移动文件
    * @param file 文件信息，新路径放在path
    */
    @Override
    public void updateFilePath(ObsFile file){
        fileMapper.updateFilePath(file.getFileId(), file.getPath());
    }
}
