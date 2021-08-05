package com.io.yy.wxmngt.service;

import com.io.yy.wxmngt.entity.CsFacilities;
import com.io.yy.common.service.BaseService;
import com.io.yy.wxmngt.param.CsFacilitiesQueryParam;
import com.io.yy.wxmngt.vo.CsFacilitiesQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 服务设施管理 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
public interface CsFacilitiesService extends BaseService<CsFacilities> {

    /**
     * 保存
     *
     * @param csFacilities
     * @return
     * @throws Exception
     */
    boolean saveCsFacilities(CsFacilities csFacilities) throws Exception;

    /**
     * 修改
     *
     * @param csFacilities
     * @return
     * @throws Exception
     */
    boolean updateCsFacilities(CsFacilities csFacilities) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsFacilities(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsFacilitiess(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsFacilitiesQueryVo getCsFacilitiesById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csFacilitiesQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsFacilitiesQueryVo> getCsFacilitiesPageList(CsFacilitiesQueryParam csFacilitiesQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csFacilitiesQueryParam
     * @return
     */
     boolean updateStatus(CsFacilitiesQueryParam csFacilitiesQueryParam);


}
