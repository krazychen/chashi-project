package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.system.entity.SysUserOffice;
import com.io.yy.system.param.SysUserOfficeQueryParam;
import com.io.yy.system.vo.SysUserOfficeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * 用户和机构关联表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
@Repository
public interface SysUserOfficeMapper extends BaseMapper<SysUserOffice> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysUserOfficeQueryVo getSysUserOfficeById(Long id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysUserOfficeQueryParam
     * @return
     */
    IPage<SysUserOfficeQueryVo> getSysUserOfficePageList(@Param("page") Page page, @Param("param") SysUserOfficeQueryParam sysUserOfficeQueryParam);

    void deleteByUser(List<Long> id);
}
