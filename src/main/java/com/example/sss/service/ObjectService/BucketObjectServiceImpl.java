package com.example.sss.service.ObjectService;

import com.example.sss.model.domin.BucketObject;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import com.obs.services.model.fs.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Transactional
@Service
public class BucketObjectServiceImpl implements BucketObjectService{

    static BucketObject bucketObject=new BucketObject();

    private String getBucketName(Integer id){
        return "sss-"+id.toString();
    }

    @Override
    public Integer uploadFile(Integer id,InputStream is, String objectName) throws IOException {
        ObsClient obsClient = bucketObject.getInstance();
        //同名文件可能被覆盖
        String bucketName=getBucketName(id);
        PutObjectResult result = null;
        result=obsClient.putObject(bucketName, objectName, is);
        obsClient.setObjectAcl(bucketName,objectName,AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);

        obsClient.close();
        return result.getStatusCode();
    }

    @Override
    public List<ObsObject> getAllFileInfo(Integer id) throws IOException {
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        ObjectListing objectList = obsClient.listObjects(bucketName);
        List<ObsObject> list = objectList.getObjects();
        obsClient.close();
        return list;
    }

    @Override
    public Boolean removeFile(Integer id,String objectKey) throws IOException {
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        boolean exist = obsClient.doesObjectExist(bucketName, objectKey);
        DeleteObjectResult result = null;
        if (exist) {
            result = obsClient.deleteObject(bucketName, objectKey);
        }
        obsClient.close();
        return result.isDeleteMarker();//是否可以被标记为删除
    }

    @Override
    public String getFile(Integer id,String objectKey) {
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);

        boolean exist = obsClient.doesObjectExist(bucketName, objectKey);
        if (exist) {
//            obsClient.getObject(bucketName,objectKey);
//            ObsObject object = obsClient.getObject(bucketName, objectKey);
            String url="https://"+bucketName+"."+bucketObject.getEndPoint()+"/"+objectKey+"?response-content-disposition=attachment";
            return url;
        }
        return null;
    }

    @Override
    public String preview(Integer id,String objectKey) throws IOException {
        ObsClient obsClient = bucketObject.getInstance();
        //300有效时间
        String bucketName=getBucketName(id);
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, 300);
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        obsClient.close();
        return response.getSignedUrl();
    }

    @Override
    public void move(Integer id,String objectKey,String desobjectKey) throws IOException {
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        obsClient.deleteObject(bucketName, objectKey);
        obsClient.copyObject(bucketName,objectKey,bucketName,desobjectKey);
        obsClient.close();
    }

    @Override
    public void rename(Integer id,String objectKey,String newbjectKey) throws IOException {
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        obsClient.copyObject(bucketName,objectKey,bucketName,newbjectKey);
        obsClient.deleteObject(bucketName, objectKey);
        obsClient.setObjectAcl(bucketName,newbjectKey,AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);
        obsClient.close();
    }

    @Override
    public void copy(Integer id,String objectKey,String desobjectKey)throws IOException{
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        //obsClient.deleteObject(bucketName, objectKey);
        obsClient.copyObject(bucketName,objectKey,bucketName,desobjectKey);
        obsClient.setObjectAcl(bucketName,desobjectKey,AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);
        obsClient.close();
    }

    @Override
    public void newFolder(Integer id,String objectKey)throws IOException{
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        NewFolderRequest newFolderRequest=new NewFolderRequest(bucketName,objectKey);
        obsClient.newFolder(newFolderRequest);

    }

    @Override
    public void DropFolder(Integer id,String objectKey)throws IOException{
        ObsClient obsClient = bucketObject.getInstance();
        String bucketName=getBucketName(id);
        DropFolderRequest dropFolderRequest=new DropFolderRequest(bucketName,objectKey);
        obsClient.dropFolder(dropFolderRequest);
    }
}
