package com.io.yy.util;

import com.io.yy.constant.CommonRedisKey;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 行政区划缓存工具类
 *
 * @author chenPengFei
 * @date 2019/12/27
 **/
@Component
@Slf4j
public class AreaUtil {

    private static AreaUtil areaUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将当前对象赋值给静态对象,调用spring组件: redisCacheUtil.redisTemplate.xxx()
     */
    @PostConstruct
    public void init(){
        areaUtil = this;
    }

    /**
     * 获取行政区划的list集合
     *
     * @return
     */
    public static List<SysMenuTreeQueryVo> getAllSysArea() {
        List<SysMenuTreeQueryVo> listSysArea = (List<SysMenuTreeQueryVo>) areaUtil.redisTemplate.opsForValue().get(CommonRedisKey.SYS_AREA);
        return listSysArea;
    }

    /**
     * 删除缓存正的行政区划信息
     *
     * @return
     */
    public static void deleteSysArea() {
        areaUtil.redisTemplate.delete(CommonRedisKey.SYS_AREA);
    }

    /**
     * 刷新行政区划
     * @param listSysArea
     */
    public static void refreshSysAreaInfo(List<SysMenuTreeQueryVo> listSysArea) {
        areaUtil.redisTemplate.opsForValue().set(CommonRedisKey.SYS_AREA, listSysArea);
    }

    /**
     * 添加行政区划信息
     * @param listSysArea
     */
    public static void cacheSysDictDataInfo(List<SysMenuTreeQueryVo> listSysArea) {
        if (listSysArea != null && listSysArea.size()>0) {
            // Redis过期时间默认1天
            Duration expireDuration = Duration.ofSeconds(86400);
            areaUtil.redisTemplate.opsForValue().set(CommonRedisKey.SYS_AREA, listSysArea);
        }
    }


}
