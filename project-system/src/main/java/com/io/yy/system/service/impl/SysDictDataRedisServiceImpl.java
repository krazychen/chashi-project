package com.io.yy.system.service.impl;

import com.io.yy.constant.CommonRedisKey;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.vo.SysDictDataRedisVo;
import com.io.yy.util.DictUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 字典数据Redis缓存操作服务
 *
 * @author kris
 * @date 2019-12-05
 **/
@Service
public class SysDictDataRedisServiceImpl implements SysDictDataRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存字典信息
     *
     * @param sysDictDataMap
     */
    @Override
    public void cacheSysDictDataInfo(Map<String, List<SysDictDataRedisVo>> sysDictDataMap) {
        if (sysDictDataMap == null) {
            throw new IllegalArgumentException("数据字典对象不能为空");
        }

        // Redis过期时间默认1天
        Duration expireDuration = Duration.ofSeconds(86400);

        redisTemplate.opsForHash().putAll(CommonRedisKey.DICT_DATA_KEY, sysDictDataMap);
    }

    /**
     * 刷新字典信息
     *
     * @param dictType
     * @param sysDictDataQueryVoList
     */
    @Override
    public void refreshSysDictDataInfo(String dictType,List<SysDictDataRedisVo> sysDictDataQueryVoList) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        if (sysDictDataQueryVoList ==null || sysDictDataQueryVoList.size()==0) {
            throw new IllegalArgumentException("字典数据不能为空");
        }
        Map<String, List<SysDictDataRedisVo>> sysDictDataMap =  DictUtil.getAllSysDictData();
//        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = (Map<String, List<SysDictDataRedisVo>>) redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
//        sysDictDataMap.put(dictType,sysDictDataQueryVoList);
        sysDictDataMap.put(dictType,sysDictDataQueryVoList);
        redisTemplate.opsForHash().putAll(CommonRedisKey.DICT_DATA_KEY, sysDictDataMap);
    }

    /**
     * 通过字典类型，从缓存中获取字典数据
     *
     * @param dictType
     * @return
     */
    @Override
    public List<SysDictDataRedisVo> getSysDictDataVo(String dictType) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        List<SysDictDataRedisVo> sysDictDataQueryVoList = DictUtil.getSysDictDataVo(dictType);
//        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = (Map<String, List<SysDictDataRedisVo>>) redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
//        List<SysDictDataRedisVo> sysDictDataQueryVoList = (List<SysDictDataRedisVo>)sysDictDataMap.get(dictType);
        return sysDictDataQueryVoList;
    }

    /**
     * 通过字典类型和字典标签，从缓存中获取字典值
     *
     * @param dictType
     * @param dictLabel
     * @return
     */
    @Override
    public String getSysDictDataValue(String dictType, String dictLabel) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        if (StringUtils.isBlank(dictLabel)) {
            throw new IllegalArgumentException("字典标签不能为空");
        }
        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = (Map<String, List<SysDictDataRedisVo>>) redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
        List<SysDictDataRedisVo> sysDictDataQueryVoList = (List<SysDictDataRedisVo>)sysDictDataMap.get(dictType);
        String dictValue = null;
        for(int i=0; i< sysDictDataQueryVoList.size();i++){
            if(sysDictDataQueryVoList.get(i).getDictLabel().equals(dictLabel)){
                dictValue=sysDictDataQueryVoList.get(i).getDictValue();
                break;
            }
        }
        return dictValue;
    }

    /**
     * 删除字典类型的Redis缓存
     *
     */
    @Override
    public void deleteSysDictData(String dictType) {
        redisTemplate.opsForHash().delete(CommonRedisKey.DICT_DATA_KEY,dictType);
    }

    /**
     * 判断字典类型在redis中是否存在
     *
     * @param dictType
     * @return
     */
    @Override
    public boolean exists(String dictType) {
        if (StringUtils.isBlank(dictType)) {
            throw new IllegalArgumentException("字典类型不能为空");
        }
        List<SysDictDataRedisVo> sysDictDataQueryVoList = DictUtil.getSysDictDataVo(dictType);
//        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = (Map<String, List<SysDictDataRedisVo>>) redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
//        List<SysDictDataRedisVo> sysDictDataQueryVoList = (List<SysDictDataRedisVo>)sysDictDataMap.get(dictType);
        if(sysDictDataQueryVoList == null){
            return false;
        }
        return true;
    }

}
