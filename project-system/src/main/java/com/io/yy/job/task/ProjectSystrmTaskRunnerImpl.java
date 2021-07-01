package com.io.yy.job.task;

import cn.hutool.core.collection.CollUtil;
import com.io.yy.system.service.SysConfigService;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.service.SysDictDataService;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.util.ConfigDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 统一修改学员作业状态
 *
 * @author chenPengFei
 * @date 2020/3/23
 **/
@Component
public class ProjectSystrmTaskRunnerImpl implements ApplicationRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 启动后每小时执行一次
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化字典
        Map dictDataCacheMap = sysDictDataService.getAllSysDictData();
        sysDictDataRedisService.cacheSysDictDataInfo(dictDataCacheMap);
        // 在登陆判断是否有缓存参数配置，无则缓存（系统启动时加载参数配置缓存，在这边加只是为了以防没有加载）
        ConfigDataUtil.deleteAllSysConfigData();
        List<SysConfigDataRedisVo> sysConfigDataList = sysConfigService.getAllSysConfigData();
        if (CollUtil.isNotEmpty(sysConfigDataList)) {
            ConfigDataUtil.cacheSysDictDataInfo(sysConfigDataList);
        }
    }
}
