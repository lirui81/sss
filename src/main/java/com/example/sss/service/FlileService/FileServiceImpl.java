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


    //---------------------public---------------

    /**
    * description:根据路径获取下面的文件列表
    * @param file 文件对象 里包含userId，文件夹路径
    * @return 该文件夹下的所有文件（不包括子文件夹里的文件）
    */
    @Override
    public List<ObsFile> selectFileListByPath(ObsFile file){
        if (file.getPath().isEmpty()||file.getPath().endsWith("/")) {
            List<ObsFile> list = fileMapper.selectFileListByPath(file.getUserId(), file.getPath());
            for(int i=0;i<list.size();i++){
                int count=count(list.get(i).getPath(), file.getPath().length());
                //把path移除掉deleteFile
                if(list.get(i).getPath().endsWith("/")&&count==0){
                    list.remove(list.get(i));
                    i--;
                }else{
                    //子文件夹下面的也移除掉
                    if (count!=0){
                        list.remove(list.get(i));
                        i--;
                    }
                }
    //        if (file.getPath().isEmpty()){
    //            if (count!=0){
    //                list.remove(list.get(i));
    //                i--;
    //            }
    //        }else{
    //            //非根目录    把path移除掉deleteFile
    //            if("文件夹".equals(list.get(i).getType())&&count==0){
    //                list.remove(list.get(i));
    //                i--;
    //            }else{
    //                //子文件夹下面的也移除掉
    //                if (count!=1){
    //                    list.remove(list.get(i));
    //                    i--;
    //                }
    //            }
    //        }
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
        if (name.endsWith(".jpg")||name.endsWith(".png")||name.endsWith(".gif")){
            return "图片";
        }else if (name.endsWith(".mp3")||name.endsWith(".ape")||name.endsWith(".wav")||name.endsWith(".flac")){
            return "音乐";
        }else if(name.endsWith(".doc")||name.endsWith(".txt")||name.endsWith(".docx")||name.endsWith(".pdf")){
            return "文档";
        }else if (name.endsWith(".mp4")||name.endsWith(".avi")||name.endsWith(".rmvb")||name.endsWith(".mkv")){
            return "视频";
        }
        return "其他";
    }

    /**
    * description:向数据库添加文件信息  同名则覆盖
    * @param file 文件信息
    */
    @Override
    public void addFile(ObsFile file){
        ObsFile dstFile=fileMapper.getFileByPath(file.getUserId(), file.getPath());
        //存在同名文件需要删掉
        if(dstFile!=null){
            fileMapper.deleteFile(dstFile.getFileId());
        }
        fileMapper.addFile(file);
    }

    /**
    * description:复制文件 同名则覆盖
    * @param file 文件信息，原路径放在name、新路径放在path
    */
    @Override
    public void copyFile(ObsFile file){
        ObsFile srcFile=fileMapper.getFileByFileId(file.getFileId());
        srcFile.setPath(file.getPath());
        srcFile.setMakeTime(new Date());
        ObsFile dstFile=fileMapper.getFileByPath(file.getUserId(), file.getPath());
        //新路径存在同名文件就需要删掉他
        if(dstFile!=null){
            fileMapper.deleteFile(dstFile.getFileId());
        }
        fileMapper.addFile(srcFile);
    }

    /**
    * description:从数据库删除文件/文件夹
    * @param file 被删除的文件 包括文件id，用户id和path
    */
    @Override
    public void deleteFile(ObsFile file){
        if (isDir(file.getPath())||"文件夹".equals(file.getType())){
            //文件夹根据所属用户和文件夹路径 将路径下的东西全删了
            fileMapper.deleteDir(file.getUserId(), file.getPath());
        }else {
            //文件直接根据文件id删除
            System.out.println("文件的ID："+file.getFileId());
            fileMapper.deleteFile(file.getFileId());
        }
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
    * description:更改文件路径，即移动文件 同名则覆盖
    * @param file 文件信息，新路径放在path
    */
    @Override
    public void updateFilePath(ObsFile file){
        ObsFile dstFile=fileMapper.getFileByPath(file.getUserId(), file.getPath());
        //新路径存在同名文件就需要删掉他
        if(dstFile!=null){
            fileMapper.deleteFile(dstFile.getFileId());
        }
        fileMapper.updateFilePath(file.getFileId(), file.getPath());
    }
}
