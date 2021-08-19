package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsRechargeConsumQueryParam;
import com.io.yy.marketing.vo.CsRechargeConsumQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 充值消费 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
public interface CsRechargeConsumService extends BaseService<CsRechargeConsum> {

    /**
     * 保存
     *
     * @param csRechargeConsum
     * @return
     * @throws Exception
     */
    boolean saveCsRechargeConsum(CsRechargeConsum csRechargeConsum) throws Exception;

    /**
     * 修改
     *
     * @param csRechargeConsum
     * @return
     * @throws Exception
     */
    boolean updateCsRechargeConsum(CsRechargeConsum csRechargeConsum) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsRechargeConsum(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsRechargeConsums(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsRechargeConsumQueryVo getCsRechargeConsumById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csRechargeConsumQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsRechargeConsumQueryVo> getCsRechargeConsumPageList(CsRechargeConsumQueryParam csRechargeConsumQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csRechargeConsumQueryParam
     * @return
     */
     boolean updateStatus(CsRechargeConsumQueryParam csRechargeConsumQueryParam);


}
