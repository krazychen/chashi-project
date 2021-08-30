package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.param.CsMembercardConsumQueryParam;
import com.io.yy.marketing.vo.CsMembercardConsumQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 会员卡消费记录 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-24
 */
@Repository
public interface CsMembercardConsumMapper extends BaseMapper<CsMembercardConsum> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsMembercardConsumQueryVo getCsMembercardConsumById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMembercardConsumQueryParam
     * @return
     */
    IPage<CsMembercardConsumQueryVo> getCsMembercardConsumPageList(@Param("page") Page page, @Param("param") CsMembercardConsumQueryParam csMembercardConsumQueryParam);

    /**
     * 更新状态
     *
     * @param csMembercardConsumQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsMembercardConsumQueryParam csMembercardConsumQueryParam);


}
