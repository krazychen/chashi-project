package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsCouponReleased;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.vo.CsCouponReleasedQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 优惠卷发放/领取记录 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
public interface CsCouponReleasedService extends BaseService<CsCouponReleased> {

    /**
     * 保存
     *
     * @param csCouponReleased
     * @return
     * @throws Exception
     */
    boolean saveCsCouponReleased(CsCouponReleased csCouponReleased) throws Exception;

    /**
     * 修改
     *
     * @param csCouponReleased
     * @return
     * @throws Exception
     */
    boolean updateCsCouponReleased(CsCouponReleased csCouponReleased) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsCouponReleased(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsCouponReleaseds(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsCouponReleasedQueryVo getCsCouponReleasedById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csCouponReleasedQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsCouponReleasedQueryVo> getCsCouponReleasedPageList(CsCouponReleasedQueryParam csCouponReleasedQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csCouponReleasedQueryParam
     * @return
     */
     boolean updateStatus(CsCouponReleasedQueryParam csCouponReleasedQueryParam);

    /**
     * 为新人发放优惠卷更新状态
     *
     * @param csCouponReleased
     * @return
     */
     boolean releaseForNewMember(CsCouponReleased csCouponReleased) throws Exception;
}
