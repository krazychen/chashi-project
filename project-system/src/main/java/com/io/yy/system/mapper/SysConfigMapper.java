package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.system.entity.SysConfig;
import com.io.yy.system.param.SysConfigQueryParam;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.system.vo.SysConfigQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 参数配置表 Mapper 接口
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-02
 */
@Repository
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysConfigQueryVo getSysConfigById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysConfigQueryParam
     * @return
     */
    IPage<SysConfigQueryVo> getSysConfigPageList(@Param("page") Page page, @Param("param") SysConfigQueryParam sysConfigQueryParam);


    /**
     * 获取所有系统配置数据
     *
     * @return
     */
    List<SysConfigDataRedisVo> getAllSysConfigData();

    /**
     * 获取配置集合
     * @param idList
     * @return
     */
    List<SysConfigDataRedisVo> getConfigListByLists(@Param("idList") List<String> idList);
}
