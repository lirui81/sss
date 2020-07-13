package com.example.sss.service.ObsService;

import com.example.sss.model.domin.User;
import com.obs.services.model.BucketMetadataInfoResult;
import com.obs.services.model.BucketStorageInfo;

/**
 * Title:
 * Description:OBS服务器接口
 *
 * @author： pop
 * @date： 2020/7/12 19:09
 * @vertion： V1.0.1
 */
public interface ObsService {
    void createBucket(Integer id);
    void deleteBucket(Integer id);
    BucketStorageInfo getBucketStorageInfo(Integer id);
    BucketMetadataInfoResult getBucketMetadata(Integer id);
}