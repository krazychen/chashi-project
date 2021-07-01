package com.io.yy.system.service;

import com.io.yy.system.vo.SysDictDataRedisVo;

import java.util.List;
import java.util.Map;

/**
 * 字典数据Redis缓存操作服务
 *
 * @author kris
 * @date 2019-12-05
 **/
public interface SysDictDataRedisService {

    /**
     * 缓存字典信息
     *
     * @param sysDictDataMap
     */
    void cacheSysDictDataInfo(Map<String, List<SysDictDataRedisVo>> sysDictDataMap);


    /**
     * 刷新字典信息
     * @param dictType
     * @param sysDictDataQueryVoList
     */
    void refreshSysDictDataInfo(String dictType, List<SysDictDataRedisVo> sysDictDataQueryVoList);

    /**
     * 通过字典类型，从缓存中获取字典数据
     *
     * @param dictType
     * @return
     */
    List<SysDictDataRedisVo> getSysDictDataVo(String dictType);

    /**
     * 通过字典类型和字典标签，从缓存中获取字典值
     *
     * @param dictType
     * @param dictLabel
     * @return
     */
     String getSysDictDataValue(String dictType, String dictLabel);

    /**
     * 删除字典类型的Redis缓存
     *
     */
    void deleteSysDictData(String dictType);

    /**
     * 判断字典类型在redis中是否存在
     *
     * @param dictType
     * @return
     */
    boolean exists(String dictType);

}
