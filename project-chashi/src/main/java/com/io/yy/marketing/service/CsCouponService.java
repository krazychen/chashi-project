package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsCoupon;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsCouponQueryParam;
import com.io.yy.marketing.vo.CsCouponQueryVo;
import com.io.yy.common.vo.Paging;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 优惠卷 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
public interface CsCouponService extends BaseService<CsCoupon> {

    /**
     * 保存
     *
     * @param csCoupon
     * @return
     * @throws Exception
     */
    boolean saveCsCoupon(CsCoupon csCoupon) throws Exception;

    /**
     * 修改
     *
     * @param csCoupon
     * @return
     * @throws Exception
     */
    boolean updateCsCoupon(CsCoupon csCoupon) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsCoupon(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsCoupons(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsCouponQueryVo getCsCouponById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csCouponQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsCouponQueryVo> getCsCouponPageList(CsCouponQueryParam csCouponQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csCouponQueryParam
     * @return
     */
     boolean updateStatus(CsCouponQueryParam csCouponQueryParam);

    /**
     * 获取新人优惠卷
     *
     * @return
     */
    List<CsCouponQueryVo> getCsCouponOfNewMember();
}
