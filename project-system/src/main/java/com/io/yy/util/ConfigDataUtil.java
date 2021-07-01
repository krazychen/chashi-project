package com.io.yy.util;

import com.io.yy.constant.CommonRedisKey;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kris
 * @date 2020-02-03
 */
@Component
@Slf4j
public class ConfigDataUtil {

    private static ConfigDataUtil configDataUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将当前对象赋值给静态对象,调用spring组件: redisCacheUtil.redisTemplate.xxx()
     */
    @PostConstruct
    public void init() {
        configDataUtil = this;
    }

    /**
     * 缓存参数配置信息
     *
     * @param sysConfigDataRedisVoList
     */
    public static void cacheSysDictDataInfo(List<SysConfigDataRedisVo> sysConfigDataRedisVoList) {
        if (sysConfigDataRedisVoList == null) {
            throw new IllegalArgumentException("参数配置信息不能为空");
        }

        // Redis过期时间默认1天
        Duration expireDuration = Duration.ofSeconds(86400);

        configDataUtil.redisTemplate.opsForList().rightPushAll(CommonRedisKey.CONFIG_DATA_KEY, sysConfigDataRedisVoList);
    }

    /**
     * 通过配置key，从缓存中获取配置数据
     *
     * @param configKey
     * @return
     */
    public static SysConfigDataRedisVo getSysConfigDataVo(String configKey) {
        if (StringUtils.isBlank(configKey)) {
            throw new IllegalArgumentException("参数键值不能为空");
        }
        List<SysConfigDataRedisVo> sysConfigDataList = (List<SysConfigDataRedisVo>) configDataUtil.redisTemplate.opsForList().range(CommonRedisKey.CONFIG_DATA_KEY, 0, -1);
        List<SysConfigDataRedisVo> findSysConfigDataList = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals(configKey)).collect(Collectors.toList());
        if (findSysConfigDataList.size() > 0) {
            return findSysConfigDataList.get(0);
        }
        return null;
    }

    /**
     * 从缓存中获取全量配置数据list
     *
     * @return
     */
    public static List<SysConfigDataRedisVo> getAllSysConfigData() {
        List<SysConfigDataRedisVo> sysConfigDataList = (List<SysConfigDataRedisVo>) configDataUtil.redisTemplate.opsForList().range(CommonRedisKey.CONFIG_DATA_KEY, 0, -1);
        return sysConfigDataList;
    }

    /**
     * 从缓存中删除全量配置数据list
     *
     * @return
     */
    public static void deleteAllSysConfigData() {
        configDataUtil.redisTemplate.delete(CommonRedisKey.CONFIG_DATA_KEY);
    }

    /**
     * 从缓存中删除某个参数key的缓存
     *
     * @return
     */
    public static void deleteSysConfigData(String configKey) {
        if (StringUtils.isBlank(configKey)) {
            throw new IllegalArgumentException("参数键值不能为空");
        }
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();
        for (int i = 0; i < sysConfigDataList.size(); i++) {
            if (sysConfigDataList.get(i).getConfigKey().equals(configKey)) {
                configDataUtil.redisTemplate.opsForList().remove(CommonRedisKey.CONFIG_DATA_KEY, 0, sysConfigDataList.get(i));
            }
        }
/*        sysConfigDataList.removeIf(item -> item.getConfigKey().equals(configKey));
        configDataUtil.redisTemplate.opsForList().rightPushAll(CommonRedisKey.CONFIG_DATA_KEY, sysConfigDataList);*/
    }

    /**
     * 根据字典类型和字典数据列表刷新字典信息
     *
     * @param configKey
     * @param sysConfigDataRedisVo
     */
    public static void refreshSysConfigDataInfo(String configKey, SysConfigDataRedisVo sysConfigDataRedisVo) {
        if (StringUtils.isBlank(configKey)) {
            throw new IllegalArgumentException("参数键值不能为空");
        }
        if (sysConfigDataRedisVo == null) {
            throw new IllegalArgumentException("参数值不能为空");
        }
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();
        for (int i = 0; i < sysConfigDataList.size(); i++) {
            if (sysConfigDataList.get(i).getConfigKey().equals(configKey)) {
                configDataUtil.redisTemplate.opsForList().set(CommonRedisKey.CONFIG_DATA_KEY, i, sysConfigDataRedisVo);
            }
        }
/*        sysConfigDataList.removeIf(item -> item.getConfigKey().equals(configKey));
        sysConfigDataList.add(sysConfigDataRedisVo);
        configDataUtil.redisTemplate.opsForList().rightPushAll(CommonRedisKey.CONFIG_DATA_KEY, sysConfigDataList);*/
    }

    /**
     * 判断参数配置在redis中是否存在
     *
     * @param configKey
     * @return
     */
    public static boolean exists(String configKey) {
        if (StringUtils.isBlank(configKey)) {
            throw new IllegalArgumentException("参数键值不能为空");
        }
        List<SysConfigDataRedisVo> sysConfigDataList = (List<SysConfigDataRedisVo>) configDataUtil.redisTemplate.opsForList().range(CommonRedisKey.CONFIG_DATA_KEY, 0, -1);
        List<SysConfigDataRedisVo> findSysConfigDataList = sysConfigDataList.stream().filter(item -> item.getConfigKey().equals(configKey)).collect(Collectors.toList());
        if (findSysConfigDataList.size() > 0) {
            return true;
        }
        return false;
    }
}
