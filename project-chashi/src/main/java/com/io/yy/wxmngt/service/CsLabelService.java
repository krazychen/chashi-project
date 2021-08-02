package com.io.yy.wxmngt.service;

import com.io.yy.wxmngt.entity.CsLabel;
import com.io.yy.common.service.BaseService;
import com.io.yy.wxmngt.param.CsLabelQueryParam;
import com.io.yy.wxmngt.vo.CsLabelQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 标签管理 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-07-08
 */
public interface CsLabelService extends BaseService<CsLabel> {

    /**
     * 保存
     *
     * @param csLabel
     * @return
     * @throws Exception
     */
    boolean saveCsLabel(CsLabel csLabel) throws Exception;

    /**
     * 修改
     *
     * @param csLabel
     * @return
     * @throws Exception
     */
    boolean updateCsLabel(CsLabel csLabel) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsLabel(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsLabels(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsLabelQueryVo getCsLabelById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csLabelQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsLabelQueryVo> getCsLabelPageList(CsLabelQueryParam csLabelQueryParam) throws Exception;

}
