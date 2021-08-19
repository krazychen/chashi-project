package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsRechargeSetting;
import com.io.yy.marketing.param.CsRechargeSettingQueryParam;
import com.io.yy.marketing.vo.CsRechargeSettingQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 充值设置 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Repository
public interface CsRechargeSettingMapper extends BaseMapper<CsRechargeSetting> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsRechargeSettingQueryVo getCsRechargeSettingById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csRechargeSettingQueryParam
     * @return
     */
    IPage<CsRechargeSettingQueryVo> getCsRechargeSettingPageList(@Param("page") Page page, @Param("param") CsRechargeSettingQueryParam csRechargeSettingQueryParam);

    /**
     * 更新状态
     *
     * @param csRechargeSettingQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsRechargeSettingQueryParam csRechargeSettingQueryParam);


}
