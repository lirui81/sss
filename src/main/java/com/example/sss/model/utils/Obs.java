package com.example.sss.model.utils;

import com.obs.services.ObsClient;
import com.obs.services.ObsConfiguration;

import java.io.IOException;

/**
 * Title:OBS服务器
 * Description:服务器OBS的基本信息，内含与华为OBS连接的客户端
 *
 * @author： pop
 * @date： 2020/7/12 17:19
 * @vertion： V1.0.1
 */
public class Obs {
    //终端节点
    private static String endPoint="obs.cn-north-4.myhuaweicloud.com";
    //地区
    private static String bucketLoc="cn-north-4";
    private static String ak="EKULOGFICWHYOCXNSLX9";
    private static String sk="u2kLfLE26xhslSH7sgSSsxc7zbULHYrpl6AZWbor";
    //OBS客户端（ObsClient）是访问OBS服务的
    private static ObsClient obsClient;
    //private List<String> bucketName;可能需要放在user里面 或者用sss- + user主键来表示

    //初始化obs配置
    static {
        ObsConfiguration config = new ObsConfiguration();
        config.setSocketTimeout(30000);
        config.setConnectionTimeout(10000);
        config.setEndPoint(endPoint);
        obsClient = new ObsClient(ak, sk, config);
    }


    public static void closeClient() {
        if (obsClient != null) {
            try {
                obsClient.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static String getBucketLoc() {
        return bucketLoc;
    }

    public static void setBucketLoc(String bucketLoc) {
        Obs.bucketLoc = bucketLoc;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        Obs.endPoint = endPoint;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        Obs.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        Obs.sk = sk;
    }

    public ObsClient getObsClient() {
        return obsClient;
    }

    public void setObsClient(ObsClient obsClient) {
        Obs.obsClient = obsClient;
    }
}
