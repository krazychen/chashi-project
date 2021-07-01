package com.io.yy;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * project  项目启动入口
 * @author kris
 * @since 2020-05-21
 */
@EnableConfigurationProperties
@EnableAdminServer
@SpringBootApplication
public class ProjectBootAdminApplication {

    public static void main(String[] args) {
        // 启动
        ConfigurableApplicationContext context = SpringApplication.run(ProjectBootAdminApplication.class, args);
//        // 打印项目信息
//        PrintApplicationInfo.print(context);
    }

}
