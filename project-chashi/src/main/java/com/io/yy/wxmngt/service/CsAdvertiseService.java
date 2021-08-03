package com.io.yy.wxmngt.service;

import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.wxmngt.entity.CsAdvertise;
import com.io.yy.common.service.BaseService;
import com.io.yy.wxmngt.param.CsAdvertiseQueryParam;
import com.io.yy.wxmngt.vo.CsAdvertiseQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 广告设置 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-02
 */
public interface CsAdvertiseService extends BaseService<CsAdvertise> {

    /**
     * 保存
     *
     * @param csAdvertise
     * @return
     * @throws Exception
     */
    boolean saveCsAdvertise(CsAdvertise csAdvertise) throws Exception;

    /**
     * 修改
     *
     * @param csAdvertise
     * @return
     * @throws Exception
     */
    boolean updateCsAdvertise(CsAdvertise csAdvertise) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsAdvertise(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsAdvertises(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsAdvertiseQueryVo getCsAdvertiseById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csAdvertiseQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsAdvertiseQueryVo> getCsAdvertisePageList(CsAdvertiseQueryParam csAdvertiseQueryParam) throws Exception;

    /**
     * 通过ID更新status
     *
     * @param csAdvertiseQueryParam
     * @return
     * @throws Exception
     */
    public Boolean updateStatusById(CsAdvertiseQueryParam csAdvertiseQueryParam);
}
