package com.io.yy.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.io.yy.shiro.util.LoginUtil;
import com.io.yy.util.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ...." );
//        this.setFieldValByName("operator", "Jerry", metaObject);//版本号3.0.6以及之前的版本
        if(HttpServletRequestUtil.getRequest() != null){
            this.strictInsertFill(metaObject,"createBy", String.class, String.valueOf(LoginUtil.getUserId()));
            this.strictInsertFill(metaObject,"updateBy", String.class,  String.valueOf(LoginUtil.getUserId()));
//            this.strictInsertFill(metaObject,"corpCode", String.class,  String.valueOf(LoginUtil.getOfficeCode()));
//            this.strictInsertFill(metaObject,"corpName", String.class,  String.valueOf(LoginUtil.getOfficeName()));
        }else{
            //处理当调度任务读取不到seesion时，没有消息等创建者的问题
            this.strictInsertFill(metaObject,"createBy", String.class, String.valueOf(1));
            this.strictInsertFill(metaObject,"updateBy", String.class,  String.valueOf(1));
        }
        this.strictInsertFill(metaObject,"createTime", Date.class,  new Date());//@since 快照：3.0.7.2-SNAPSHOT， @since 正式版暂未发布3.0.7
        this.strictInsertFill(metaObject,"updateTime", Date.class,  new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ...." );
//        this.setFieldValByName("operator", "Tom", metaObject);
        if(HttpServletRequestUtil.getRequest() != null){
            this.strictInsertFill(metaObject,"updateBy", String.class,  String.valueOf(LoginUtil.getUserId()) );
        }
        this.strictInsertFill(metaObject,"updateTime", Date.class, new Date());
    }
}