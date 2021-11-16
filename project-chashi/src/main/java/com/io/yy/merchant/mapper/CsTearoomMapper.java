package com.io.yy.merchant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.entity.CsTearoom;
import com.io.yy.merchant.param.CsTearoomQueryParam;
import com.io.yy.merchant.vo.CsTearoomQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 茶室管理 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Repository
public interface CsTearoomMapper extends BaseMapper<CsTearoom> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsTearoomQueryVo getCsTearoomById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csTearoomQueryParam
     * @return
     */
    IPage<CsTearoomQueryVo> getCsTearoomPageList(@Param("page") Page page, @Param("param") CsTearoomQueryParam csTearoomQueryParam);

    /**
     * 更新状态
     *
     * @param csTearoomQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsTearoomQueryParam csTearoomQueryParam);

    /**
     * 更新营业状态
     *
     * @param csTearoomQueryParam
     * @return
     */
    Integer updateReleaseStatus(@Param("param") CsTearoomQueryParam csTearoomQueryParam);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csTearoomQueryParam
     * @return
     */
    IPage<CsTearoomQueryVo> getCsTearoomObjPageList(@Param("page") Page page, @Param("param") CsTearoomQueryParam csTearoomQueryParam);

    /**
     * 获取分页对象，包含已购数量
     *
     * @param page
     * @param csTearoomQueryParam
     * @return
     */
    IPage<CsTearoomQueryVo> getCsTearoomPageListForWx(@Param("page") Page page, @Param("param") CsTearoomQueryParam csTearoomQueryParam);

}
