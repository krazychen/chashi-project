package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.system.entity.SysOffice;
import com.io.yy.system.param.SysOfficeQueryParam;
import com.io.yy.system.vo.SysOfficeQueryVo;
import com.io.yy.system.vo.SysOfficeTreeQueryVo;
import com.io.yy.system.vo.SysOfficeUserTreeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * 组织机构表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-23
 */
@Repository
public interface SysOfficeMapper extends BaseMapper<SysOffice> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysOfficeQueryVo getSysOfficeById(String id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysOfficeQueryParam
     * @return
     */
    IPage<SysOfficeQueryVo> getSysOfficePageList(@Param("page") Page page, @Param("param") SysOfficeQueryParam sysOfficeQueryParam);

    List<SysOfficeQueryVo> findChildNode(String id);

    List<SysOfficeTreeQueryVo> getParentCodeTree(SysOfficeQueryParam sysOfficeQueryParam);

    Integer updateTreeLeafById(@Param("officeCode") String officeCode, @Param("value") String value);

    List<SysOfficeUserTreeQueryVo> getParentCodeUserTree(SysOfficeQueryParam sysOfficeQueryParam);
}
