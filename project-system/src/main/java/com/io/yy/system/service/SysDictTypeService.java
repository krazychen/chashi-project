package com.io.yy.system.service;

import com.io.yy.system.entity.SysDictType;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysDictTypeQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.vo.SysDictTypeQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 字典类型表 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
public interface SysDictTypeService extends BaseService<SysDictType> {

    /**
     * 保存
     *
     * @param sysDictType
     * @return
     * @throws Exception
     */
    boolean saveSysDictType(SysDictType sysDictType) throws Exception;

    /**
     * 修改
     *
     * @param sysDictType
     * @return
     * @throws Exception
     */
    boolean updateSysDictType(SysDictType sysDictType) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysDictType(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysDictTypes(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysDictTypeQueryVo getSysDictTypeById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysDictTypeQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysDictTypeQueryVo> getSysDictTypePageList(SysDictTypeQueryParam sysDictTypeQueryParam) throws Exception;

    /**
     * 根据字典类型id更改字典的状态
     * @param sysDictTypeStatusQueryParam
     * @return
     */
    Boolean updateStatusById(SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam);
}
