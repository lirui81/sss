package com.example.sss.model.util;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class BucketObjectOperation {
    //从配置文件中读取授权信息
    @Value("${obs.endPoint}")
    private static String endPoint="obs.cn-south-1.myhuaweicloud.com";

    @Value("${obs.ak}")
    private static String ak="0FIECP5NDHYDJ65CSVFW";

    @Value("${obs.sk}")
    private static String sk="JKNsM3NHlJxZGjdBOMDEWPtjZTA8CPyeeyyuxDZ1";

//    private static final String bucketLocation = "cn-south-1";

    private String bucketName="obsBucketName";

    //获取OBS客户端实例
    public ObsClient getInstance() {
        return new ObsClient(ak, sk, endPoint);
    }

    //上传文件
    public Integer uploadFile(InputStream is, String objectKey) throws IOException {
        ObsClient obsClient = getInstance();
        Boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
        PutObjectResult result = null;
        //同名文件可能被覆盖
        result = obsClient.putObject(bucketName, objectKey, is);
        //同名文件不会被覆盖
//        if (flag) {
//            return 0;
//        } else {
//            result = obsClient.putObject(bucketName, objectKey, is);
//        }
        obsClient.close();
        return result.getStatusCode();
    }

    public List<ObsObject> getAllFileInfo() throws IOException {
        ObsClient obsClient = getInstance();
        ObjectListing objectList = obsClient.listObjects(bucketName);
        List<ObsObject> list = objectList.getObjects();
        obsClient.close();
        return list;
    }

    public Boolean removeFile(String objectKey) throws IOException {
        ObsClient obsClient = getInstance();
        boolean exist = obsClient.doesObjectExist(bucketName, objectKey);
        DeleteObjectResult result = null;
        if (exist) {
            result = obsClient.deleteObject(bucketName, objectKey);
        }
        obsClient.close();
        return result.isDeleteMarker();//是否可以被标记为删除
    }

    //获取文件对象-下载
    public ObsObject getFile(String objectKey) {
        ObsClient obsClient = getInstance();
        boolean exist = obsClient.doesObjectExist(bucketName, objectKey);
        if (exist) {
            ObsObject object = obsClient.getObject(bucketName, objectKey);
            return object;
        }
        return null;
    }

    /**
     * 如果是流式文件，返回的链接可以在浏览器预览
     * 如果是非流式文件，返回的链接可以在浏览器里下载文件
     * @param objectKey
     * @return
     * @throws IOException
     */
    //预览授权访问-支持流式文件
    public String preview(String objectKey) throws IOException {
        ObsClient obsClient = getInstance();
        //300有效时间
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, 300);
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        obsClient.close();
        return response.getSignedUrl();
    }
}
