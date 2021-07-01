package com.io.yy.util;

import cn.hutool.core.collection.CollUtil;
import com.io.yy.system.service.SysConfigService;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.service.SysDictDataService;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/13
 **/
@Component
@Slf4j
public class RedisAsyncUtil {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    @Async
    public void cacheConfigDataInfo(SysConfigDataRedisVo sysConfigDataRedisVo) {
        List<SysConfigDataRedisVo> sysConfigDataList = CollUtil.newArrayList();
        sysConfigDataList.add(sysConfigDataRedisVo);
        ConfigDataUtil.cacheSysDictDataInfo(sysConfigDataList);
    }

    @Async
    public void cacheSysDictDataInfo(){
        Map dictDataCacheMap = sysDictDataService.getAllSysDictData();
        sysDictDataRedisService.cacheSysDictDataInfo(dictDataCacheMap);
    }
}
