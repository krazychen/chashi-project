package com.io.yy.merchant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.entity.CsMemberCard;
import com.io.yy.merchant.param.CsMemberCardQueryParam;
import com.io.yy.merchant.vo.CsMemberCardQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 会员卡 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-09
 */
@Repository
public interface CsMemberCardMapper extends BaseMapper<CsMemberCard> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsMemberCardQueryVo getCsMemberCardById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMemberCardQueryParam
     * @return
     */
    IPage<CsMemberCardQueryVo> getCsMemberCardPageList(@Param("page") Page page, @Param("param") CsMemberCardQueryParam csMemberCardQueryParam);

    /**
     * 更新状态
     *
     * @param csMemberCardQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsMemberCardQueryParam csMemberCardQueryParam);


}
