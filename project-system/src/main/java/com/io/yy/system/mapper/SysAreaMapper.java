package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysArea;
import com.io.yy.system.param.SysAreaQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.vo.SysAreaLazyQueryVo;
import com.io.yy.system.vo.SysAreaQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 行政区划 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-04
 */
@Repository
public interface SysAreaMapper extends BaseMapper<SysArea> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysAreaQueryVo getSysAreaById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysAreaQueryParam
     * @return
     */
    IPage<SysAreaQueryVo> getSysAreaPageList(@Param("page") Page page, @Param("param") SysAreaQueryParam sysAreaQueryParam);

    /**
     * 精简行政区域树结构
     * @param sysAreaQueryParam
     * @return
     */
    List<SysMenuTreeQueryVo> getSysAreaSimplifyPageList(@Param("param") SysAreaQueryParam sysAreaQueryParam);


    /**
     * 根据菜单code修改是否末级节点
     *
     * @param sysMenuCode
     * @param value
     * @return
     */
    Integer updateTreeLeafById(@Param("sysAreaCode") String sysMenuCode, @Param("value") String value);

    /**
     * 修改行政区域的状态
     * @param sysDictTypeStatusQueryParam
     * @return
     */
    Integer updateStatusById(@Param("param") SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam);

    SysAreaQueryVo findByName(@Param("areaName") String areaName);

    IPage<SysAreaLazyQueryVo> getSysAreaPageListLazy(@Param("page") Page page, @Param("param") SysAreaQueryParam sysAreaQueryParam);

    /**
     * 根据区域类型 父级编码获取 区域
     * @param areaType
     * @param parentCode
     * @return
     */
    List<SysAreaQueryVo> getAreaByAreaType(@Param("areaType") String areaType,@Param("parentCode") String parentCode);
}
