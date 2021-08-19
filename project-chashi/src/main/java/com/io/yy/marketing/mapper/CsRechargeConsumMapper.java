package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.param.CsRechargeConsumQueryParam;
import com.io.yy.marketing.vo.CsRechargeConsumQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 充值消费 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Repository
public interface CsRechargeConsumMapper extends BaseMapper<CsRechargeConsum> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsRechargeConsumQueryVo getCsRechargeConsumById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csRechargeConsumQueryParam
     * @return
     */
    IPage<CsRechargeConsumQueryVo> getCsRechargeConsumPageList(@Param("page") Page page, @Param("param") CsRechargeConsumQueryParam csRechargeConsumQueryParam);

    /**
     * 更新状态
     *
     * @param csRechargeConsumQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsRechargeConsumQueryParam csRechargeConsumQueryParam);


}
