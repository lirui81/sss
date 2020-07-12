package com.example.sss.service.ObsService;

import com.example.sss.model.domin.Obs;
import com.example.sss.model.domin.User;
import com.obs.services.exception.ObsException;
import com.obs.services.model.BucketQuota;
import com.obs.services.model.BucketStorageInfo;
import com.obs.services.model.HeaderResponse;

/**
 * Title:OBS服务器
 * Description:与华为的OBS连接，管理所拥有的全部OBS
 *
 * @author： pop
 * @date： 2020/7/12 19:20
 * @vertion： V1.0.1
 */
public class ObsServiceImpl implements ObsService {
    private static Obs obs;

    /**
    * description:设置桶的配额
    * @param bucketName 桶名
     * @param size 配额大小
     */
    private void setBucketQuota(String bucketName,int size){
        BucketQuota quota=new BucketQuota(size);
        obs.getObsClient().setBucketQuota(bucketName, quota);
    }

    private String getBucketName(User user){
        return "sss-"+user.getId();
    }

    /**
    * description:创建桶，需要与用户绑定
    * @param user 新创建的用户/或者仅仅需要他的主键ID？
    * @return 是否创建成功（貌似不用判断）
    */
    public boolean createBucket(User user){
        String bucketName=getBucketName(user);
        try{
            HeaderResponse response=obs.getObsClient().createBucket(bucketName);
            setBucketQuota(bucketName,1024*1024*1024);
            return true;
        }catch (ObsException e){
            System.out.println(e.getErrorCode());
            return false;
        }
    }

    /**
    * description:在删除用户时连带要把他的桶也给删了
    * @param user 要删除的用户
    */
    public void deleteBucket(User user){
        obs.getObsClient().deleteBucket(getBucketName(user));
    }

    /**
    * description:获取桶的存量信息，主要包括已使用的空间大小以及桶包含的对象个数
    * @param user 所属用户
    * @return 桶存量信息/或者两个int（原本是long类型的）
    */
    public BucketStorageInfo getBucketStorageInfo(User user){
        BucketStorageInfo storageInfo=obs.getObsClient().getBucketStorageInfo(getBucketName(user));
        int number= (int) storageInfo.getObjectNumber();
        int size= (int) storageInfo.getSize();
        return storageInfo;
    }
}
