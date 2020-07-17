package com.example.sss;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@MapperScan("com.example.sss.dao")//与dao层的@Mapper二选一写上即可(主要作用是扫包)
public class SssApplication {

    public static void main(String[] args) {
        SpringApplication.run(SssApplication.class, args);
    }

}
