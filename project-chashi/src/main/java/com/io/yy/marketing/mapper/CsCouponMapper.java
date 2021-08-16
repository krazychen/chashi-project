package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsCoupon;
import com.io.yy.marketing.param.CsCouponQueryParam;
import com.io.yy.marketing.vo.CsCouponQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 优惠卷 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
@Repository
public interface CsCouponMapper extends BaseMapper<CsCoupon> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsCouponQueryVo getCsCouponById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csCouponQueryParam
     * @return
     */
    IPage<CsCouponQueryVo> getCsCouponPageList(@Param("page") Page page, @Param("param") CsCouponQueryParam csCouponQueryParam);

    /**
     * 更新状态
     *
     * @param csCouponQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsCouponQueryParam csCouponQueryParam);

    /**
     * 获取新人优惠卷
     *
     * @return
     */
    List<CsCouponQueryVo> getCsCouponOfNewMember();

    /**
     * 获取领卷中心优惠卷
     *
     * @return
     */
    List<CsCouponQueryVo> getCsCouponListOfCouponCenter();
}
