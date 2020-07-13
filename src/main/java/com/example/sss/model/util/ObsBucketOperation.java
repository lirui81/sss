package com.example.sss.model.util;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ObsBucketOperation {
    @Value("${obs.endPoint}")
    private static String endPoint="obs.cn-south-1.myhuaweicloud.com";

    @Value("${obs.ak}")
    private static String ak="0FIECP5NDHYDJ65CSVFW";

    @Value("${obs.sk}")
    private static String sk="JKNsM3NHlJxZGjdBOMDEWPtjZTA8CPyeeyyuxDZ1";

    private static final String bucketLocation = "cn-south-1";

    public static ObsClient getObsClient() {
        return new ObsClient(ak, sk, endPoint);
    }

    public int createBucket(String bucketName) throws IOException {
//        System.out.println(endPoint);
//        System.out.println(ak);
//        System.out.println(sk);
        ObsClient obsClient = getObsClient();
        ObsBucket obsBucket = new ObsBucket();
        obsBucket.setBucketName(bucketName);
        //设置桶的访问权限为公共读，默认是私有写
        obsBucket.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        //设置桶的存储类型为归档存储
        obsBucket.setBucketStorageClass(StorageClassEnum.STANDARD);
        //设置桶区域位置
        obsBucket.setLocation(bucketLocation);
        //创建桶
        try {
            //创建成功
            HeaderResponse response = obsClient.createBucket(obsBucket);
            System.out.println(response.getRequestId());
            return 1;
        } catch (ObsException e) {
            //创建失败
            System.out.println("HTTP Code:" + e.getResponseCode());
            System.out.println("Error Code:" + e.getErrorCode());
            System.out.println("Error Message:" + e.getErrorMessage());
            System.out.println("Request ID:" + e.getErrorRequestId());
            System.out.println("Host ID:" + e.getErrorHostId());
            System.out.println();
            return 0;
        } finally {
            obsClient.close();
        }
    }

    public List<ObsBucket> getAllBucket() throws IOException {
        //创建ObsClient实例
        ObsClient obsClient = getObsClient();
        //获取列表
        ListBucketsRequest request = new ListBucketsRequest();
        request.setQueryLocation(true);
        List<ObsBucket> buckets = obsClient.listBuckets(request);
        for (ObsBucket bucket : buckets) {
            System.out.println("Bucket Name:" + bucket.getBucketName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Create Date:" + bucket.getCreationDate());
            System.out.println("Location:" + bucket.getLocation());
            System.out.println();
        }

        obsClient.close();
        return buckets;
    }

    public int removeBucket(String bucketName) throws IOException {
        ObsClient obsClient = getObsClient();
        boolean exist = obsClient.headBucket(bucketName);
        if (exist) {
            obsClient.deleteBucket(bucketName);
            obsClient.close();
            return 1;
        } else {
            System.out.println("Not exist:" + bucketName);
            return 0;
        }
    }
}
