package com.io.yy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.io.yy.util.PrintApplicationInfo;

/**
 * whyy-system 项目启动入口
 * @author kris
 * @since 2018-11-08
 */
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties
//@EnableAdminServer
@MapperScan({"com.io.yy.**.mapper"})
@ServletComponentScan
@SpringBootApplication
public class ProjectParentApplication {


    public static void main(String[] args) {
        // 启动
        ConfigurableApplicationContext context = SpringApplication.run(ProjectParentApplication.class, args);
        // 打印项目信息
        PrintApplicationInfo.print(context);
    }

}
