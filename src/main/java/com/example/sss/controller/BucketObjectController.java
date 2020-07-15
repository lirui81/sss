package com.example.sss.controller;


import com.example.sss.model.domin.BucketObject;
import com.example.sss.service.ObjectService.BucketObjectService;
import com.example.sss.service.ObsService.ObsService;
import com.obs.services.model.ObsObject;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Api(tags = "桶对象接口")
@RequestMapping("/BucketObject")
@RestController
@Transactional
@Service

public class BucketObjectController {
    @Autowired
    private BucketObjectService bucketObjectService;

    @RequestMapping(value="/uploadFile",method= RequestMethod.POST)
    public Integer testuploadFile(Integer id, InputStream is, String objectKey) throws IOException{
        return bucketObjectService.uploadFile(id,is,objectKey);
    }

    @RequestMapping(value="/getAllFileInfo",method= RequestMethod.POST)
    public List<ObsObject> testgetAllFileInfo(Integer id) throws IOException{
        return bucketObjectService.getAllFileInfo(id);
    }

    @RequestMapping(value="/removeFile",method= RequestMethod.POST)
    public Boolean testremoveFile(Integer id,String objectKey) throws IOException{
        return bucketObjectService.removeFile(id,objectKey);
    }

    @RequestMapping(value="/getFile",method= RequestMethod.POST)
    public String testgetFile(Integer id,String objectKey){
        return bucketObjectService.getFile(id,objectKey);
    }

    @RequestMapping(value="/preview",method= RequestMethod.POST)
    public String testpreview(Integer id,String objectKey) throws IOException{
        return bucketObjectService.preview(id,objectKey);
    }

    @RequestMapping(value="/move",method= RequestMethod.POST)
    public void testmove(Integer id,String objectKey,String newobjectKey) throws IOException{
        bucketObjectService.move(id,objectKey,newobjectKey);
    }

    @RequestMapping(value="/rename",method= RequestMethod.POST)
    public void testRename(Integer id,String objectKey,String newobjectKey) throws IOException{
        bucketObjectService.rename(id,objectKey,newobjectKey);
    }

    @RequestMapping(value="/copy",method= RequestMethod.POST)
    public void testcopy(Integer id,String objectKey,String desbjectKey)throws IOException{
        bucketObjectService.copy(id,objectKey,desbjectKey);
    }

    @RequestMapping(value="/newFolder",method= RequestMethod.POST)
    public void testnewFolder(Integer id,String objectKey)throws IOException{
        bucketObjectService.newFolder(id,objectKey);
    }

    @RequestMapping(value="/dropFolder",method= RequestMethod.POST)
    public void testDropFolder(Integer id,String objectKey)throws IOException{
        bucketObjectService.DropFolder(id,objectKey);
    }
}