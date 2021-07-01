package com.io.yy.job.task;

import com.io.yy.system.entity.SysMsg;
import com.io.yy.system.service.SysMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

/**
 * Hello World Controller
 *
 * @author zhaochao
 * @date 2019/12/25
 **/

@Slf4j
@Component("SysMsgRecordTask")
public class SysMsgRecordTask implements TaskService{

    @Autowired
    private SysMsgService sysMsgService;

    @Override
    public void run(String params) {
        try {
            SysMsg sysMsg = sysMsgService.getById(Long.parseLong(params));
            sysMsgService.publishMsg(sysMsg);
        } catch (Exception e) {
            System.out.println("消息记录生成失败!");
        }
    }
}
