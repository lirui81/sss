package com.example.sss.service.ObsService;

import com.example.sss.model.domin.User;
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
    boolean createBucket(User user);
    void deleteBucket(User user);
    BucketStorageInfo getBucketStorageInfo(User user);
}
