package com.io.yy.util;

import com.io.yy.constant.CommonRedisKey;
import com.io.yy.system.vo.SysDictDataRedisVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author kris
 * @date 2019-12-06
 */
@Component
@Slf4j
public class DictUtil {

    private static DictUtil dictUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将当前对象赋值给静态对象,调用spring组件: redisCacheUtil.redisTemplate.xxx()
     */
    @PostConstruct
    public void init(){
        dictUtil = this;
    }

    /**
     * 通过字典类型，从缓存中获取字典数据
     *
     * @param dictType
     * @return
     */
    public static List<SysDictDataRedisVo> getSysDictDataVo(String dictType) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = (Map<String, List<SysDictDataRedisVo>>) dictUtil.redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
        List<SysDictDataRedisVo> sysDictDataQueryVoList = (List<SysDictDataRedisVo>)sysDictDataMap.get(dictType);
        return sysDictDataQueryVoList;
    }

    /**
     * 从缓存中获取全量字典数据map
     *
     * @return
     */
    public static Map<String, List<SysDictDataRedisVo>> getAllSysDictData() {
        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = (Map<String, List<SysDictDataRedisVo>>) dictUtil.redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
        return sysDictDataMap;
    }

    /**
     * 从缓存中删除全量字典数据map
     *
     * @return
     */
    public static void deleteAllSysDictData() {
         dictUtil.redisTemplate.opsForHash().delete(CommonRedisKey.DICT_DATA_KEY);
    }

    /**
     * 从缓存中删除某个字典类型数字缓存
     *
     * @return
     */
    public static void deleteSysDictData(String dictType) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        Map<String, List<SysDictDataRedisVo>> sysDictDataMap =  DictUtil.getAllSysDictData();
        sysDictDataMap.remove(dictType);
        dictUtil.redisTemplate.opsForHash().putAll(CommonRedisKey.DICT_DATA_KEY, sysDictDataMap);
    }

    /**
     * 根据字典类型和字典数据列表刷新字典信息
     *
     * @param dictType
     * @param sysDictDataQueryVoList
     */
    public static void refreshSysDictDataInfo(String dictType,List<SysDictDataRedisVo> sysDictDataQueryVoList) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        if (sysDictDataQueryVoList ==null || sysDictDataQueryVoList.size()==0) {
            throw new IllegalArgumentException("字典数据不能为空");
        }
        Map<String, List<SysDictDataRedisVo>> sysDictDataMap =  DictUtil.getAllSysDictData();
        sysDictDataMap.put(dictType,sysDictDataQueryVoList);
        dictUtil.redisTemplate.opsForHash().putAll(CommonRedisKey.DICT_DATA_KEY, sysDictDataMap);
    }
}
