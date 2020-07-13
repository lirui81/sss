package com.example.sss.service.ObsService;

import com.example.sss.model.domin.Obs;
import com.example.sss.model.domin.User;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;

import java.util.List;

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
        return "sss-"+user.getId().toString();
    }

    /**
    * description:判断桶是否存在
    * @param bucketName 桶名
    * @return 存在返回true，不存在返回false
    */
    private boolean headBucket(String bucketName) throws ObsException{
        return obs.getObsClient().headBucket(bucketName);
    }

    /**
    * description:创建桶，需要与用户绑定
    * @param user 新创建的用户/或者仅仅需要他的主键ID？
    * @throws ObsException obs异常
    */
    public void createBucket(User user) throws ObsException{
        String bucketName=getBucketName(user);
        HeaderResponse response=obs.getObsClient().createBucket(bucketName);
        setBucketQuota(bucketName,1024*1024*1024);
    }

    /**
    * description:获取到的桶列表将按照桶名字典顺序排列。
     *          设置ListBucketsRequest.setQueryLocation参数为true后，可在列举桶时查询桶的区域位置。
    * @return 桶列表
    */
    private List<ObsBucket> bucketList(){
        ListBucketsRequest request=new ListBucketsRequest();
        request.setQueryLocation(true);
        return obs.getObsClient().listBuckets(request);
    }

    /**
    * description:在删除用户时连带要把他的桶也给删了
    * @param user 要删除的用户
     * @throws ObsException obs异常
    */
    public void deleteBucket(User user) throws ObsException{
        String bucketName =getBucketName(user);
        if(headBucket(bucketName)) {
            obs.getObsClient().deleteBucket(bucketName);
        }
    }

    /**
    * description:获取桶的存量信息，主要包括已使用的空间大小以及桶包含的对象个数
    * @param user 所属用户
    * @return 桶存量信息/或者两个int（原本是long类型的）
    */
    public BucketStorageInfo getBucketStorageInfo(User user){
        //BucketStorageInfo storageInfo=obs.getObsClient().getBucketStorageInfo(getBucketName(user));
        //int number= (int) storageInfo.getObjectNumber();
        //int size= (int) storageInfo.getSize();
        return obs.getObsClient().getBucketStorageInfo(getBucketName(user));
    }

    /**
    * description:获取存储桶元数据
    * @param user 要查询桶的用户
    * @return 桶元数据
    */
    public BucketMetadataInfoResult getBucketMetadata(User user){
        BucketMetadataInfoRequest request=new BucketMetadataInfoRequest(getBucketName(user));
        request.setOrigin("http://www.a.com");//不懂意思
        return obs.getObsClient().getBucketMetadata(request);
    }

}
