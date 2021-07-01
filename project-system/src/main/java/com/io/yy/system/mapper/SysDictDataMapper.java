package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysDictData;
import com.io.yy.system.param.SysDictDataQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.vo.SysDictDataQueryVo;
import com.io.yy.system.vo.SysDictDataRedisVo;
import com.io.yy.system.vo.SysDictDataRemarksVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 字典数据表 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Repository
public interface SysDictDataMapper extends BaseMapper<SysDictData> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysDictDataQueryVo getSysDictDataById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysDictDataQueryParam
     * @return
     */
    IPage<SysDictDataQueryVo> getSysDictDataPageList(@Param("page") Page page, @Param("param") SysDictDataQueryParam sysDictDataQueryParam);

    /**
     * 根据菜单code修改是否末级节点
     *
     * @param dictCode
     * @param value
     * @return
     */
    Integer updateTreeLeafById(@Param("dictCode") String dictCode, @Param("value") String value);

    /**
     * 获取简捷的字典树形结构
     * @return
     */
    List<SysMenuTreeQueryVo> getSysDictDataSimplifyPageList(@Param("param") SysDictDataQueryParam sysDictDataQueryParam);

    /**
     * 根据类型和标签获取条数
     * @param dictType
     * @param dictLabel
     * @return
     */
    Integer getSysDictDataCountByDictTypeAndDictLabel(@Param("dictType") String dictType, @Param("dictLabel") String dictLabel, @Param("dictCode") String dictCode);

    /**
     * 根据类型和值获取条数
     * @param dictType
     * @param dictValue
     * @return
     */
    Integer getSysDictDataCountByDictTypeAndDictValue(@Param("dictType") String dictType, @Param("dictValue") String dictValue, @Param("dictCode") String dictCode);

    Integer updateStatusById(@Param("param") SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam);

    /**
     * 获取所有数据字典数据
     *
     * @return
     */
    List<SysDictDataRedisVo> getAllSysDictData();

    SysDictData findDictCode(@Param("name") String name);

    List<SysDictDataRedisVo> getSysDictDataRedisVoByDictType(String dictType);

    List<SysDictDataRemarksVo> getDictDataByType(String type);
}
