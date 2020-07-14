package com.example.sss.controller;

import com.example.sss.service.ObsService.ObsService;
import com.example.sss.service.ObsService.ObsServiceImpl;
import com.obs.services.model.ObsBucket;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Api(tags = "Obs桶接口")
@RequestMapping("/ObsBucket")
@RestController
@Transactional
@Service
public class ObsBucketController {
    @Autowired
    private ObsService obsService;
    /**
     * @param
     * @return
     */
    @RequestMapping(value="/CreateObsBucket",method= RequestMethod.POST)
    public void testcreateBucket(@RequestBody Integer id) throws IOException {
        obsService.createBucket(id);
    }

    @RequestMapping(value="/RemoveBucket",method= RequestMethod.POST)
    public void testdeleteBucket(@RequestBody Integer id) throws IOException {
        obsService.deleteBucket(id);
    }
}
