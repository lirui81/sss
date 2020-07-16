package com.example.sss;

import com.example.sss.model.domin.FileLog;
import com.example.sss.model.domin.ObsFile;
import com.example.sss.service.FlileService.FileService;
import com.example.sss.service.FlileService.LogOperate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.List;

@SpringBootTest
class SssApplicationTests {

    @Autowired
    private FileService fileService;
    @Autowired
    private LogOperate logOperate;

    @Test
    void contextLoads() {
    }

}
