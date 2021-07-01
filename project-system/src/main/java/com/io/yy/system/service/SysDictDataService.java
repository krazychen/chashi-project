package com.io.yy.system.service;

import com.io.yy.system.entity.SysDictData;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysDictDataQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.vo.SysDictDataQueryVo;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SysDictDataRedisVo;
import com.io.yy.system.vo.SysDictDataRemarksVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;

import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 字典数据表 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
public interface SysDictDataService extends BaseService<SysDictData> {

    /**
     * 保存
     *
     * @param sysDictData
     * @return
     * @throws Exception
     */
    boolean saveSysDictData(SysDictData sysDictData) throws Exception;

    /**
     * 修改
     *
     * @param sysDictData
     * @return
     * @throws Exception
     */
    boolean updateSysDictData(SysDictData sysDictData) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysDictData(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysDictDatas(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysDictDataQueryVo getSysDictDataById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysDictDataQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysDictDataQueryVo> getSysDictDataPageList(SysDictDataQueryParam sysDictDataQueryParam) throws Exception;

    /**
     * 获取字典的树形结构
     * @return
     */
    List<SysMenuTreeQueryVo> getSysMenuSimplifyPageList(SysDictDataQueryParam sysDictDataQueryParam);

    Boolean updateStatusById(SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam);

    /**
     * 获取所有数据字典，并以字典类型为key形成map
     * @return
     */
    Map<String,List<SysDictDataRedisVo>> getAllSysDictData();

    List<SysDictDataRemarksVo> getDictDataByType(String type);
}
