package com.example.sss.service.ObsService;

import com.obs.services.model.BucketMetadataInfoResult;
import com.obs.services.model.BucketStorageInfo;
import com.obs.services.model.ObsBucket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.io.IOException;

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
