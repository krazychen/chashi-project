package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsCouponReleased;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.vo.CsCouponReleasedQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 优惠卷发放/领取记录 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
@Repository
public interface CsCouponReleasedMapper extends BaseMapper<CsCouponReleased> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsCouponReleasedQueryVo getCsCouponReleasedById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csCouponReleasedQueryParam
     * @return
     */
    IPage<CsCouponReleasedQueryVo> getCsCouponReleasedPageList(@Param("page") Page page, @Param("param") CsCouponReleasedQueryParam csCouponReleasedQueryParam);

    /**
     * 更新状态
     *
     * @param csCouponReleasedQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsCouponReleasedQueryParam csCouponReleasedQueryParam);


}
