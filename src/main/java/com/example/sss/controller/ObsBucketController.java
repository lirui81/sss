package com.example.sss.controller;

import com.example.sss.model.util.ObsBucketOperation;
import com.obs.services.model.ObsBucket;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api(tags = "Obs桶接口")
@RequestMapping("/ObsBucket")
@RestController

public class ObsBucketController {

    private ObsBucketOperation obsBucketOperation=new ObsBucketOperation();
    /**
     * @param
     * @return
     */
    @RequestMapping(value="/CreateObsBucket",method= RequestMethod.POST)
    public int testCreateBucket(@RequestBody String bucketName) throws IOException {
        return obsBucketOperation.createBucket(bucketName);
    }

    @RequestMapping(value="/GetAllBucket",method= RequestMethod.POST)
    public List<ObsBucket> getAllBucket() throws IOException {
        return obsBucketOperation.getAllBucket();
    }

    @RequestMapping(value="/RemoveBucket",method= RequestMethod.POST)
    public int removeBucket(@RequestBody String bucketName) throws IOException {
        return obsBucketOperation.removeBucket(bucketName);
    }
}
