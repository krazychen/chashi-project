package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysDictType;
import com.io.yy.system.param.SysDictTypeQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.vo.SysDictTypeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 字典类型表 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Repository
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysDictTypeQueryVo getSysDictTypeById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysDictTypeQueryParam
     * @return
     */
    IPage<SysDictTypeQueryVo> getSysDictTypePageList(@Param("page") Page page, @Param("param") SysDictTypeQueryParam sysDictTypeQueryParam);


    /**
     * 修改是校验是字典类型是否存在
     * @param dictType
     * @param id
     * @return
     */
    Integer getSysDictTypeCountByDictType(@Param("dictType") String dictType, @Param("id") String id);

    /**
     * 根据字典类型id更改字典的状态
     * @param sysDictTypeStatusQueryParam
     * @return
     */
    Integer updateStatusById(@Param("param") SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam);

    List<String> SysDictTypesByidList(@Param("idList") List<String> idList);
}
