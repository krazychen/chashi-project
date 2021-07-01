package com.io.yy.system.service;

import com.io.yy.system.entity.SysArea;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysAreaQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.vo.SysAreaLazyQueryVo;
import com.io.yy.system.vo.SysAreaQueryVo;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <pre>
 * 行政区划 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-04
 */
public interface SysAreaService extends BaseService<SysArea> {

    /**
     * 保存
     *
     * @param sysArea
     * @return
     * @throws Exception
     */
    boolean saveSysArea(SysArea sysArea) throws Exception;

    /**
     * 修改
     *
     * @param sysArea
     * @return
     * @throws Exception
     */
    boolean updateSysArea(SysArea sysArea) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysArea(String id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysAreas(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysAreaQueryVo getSysAreaById(String id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysAreaQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysAreaQueryVo> getSysAreaPageList(SysAreaQueryParam sysAreaQueryParam) throws Exception;

    /**
     * 精简的行政区域树结构
     * @param sysAreaQueryParam
     * @return
     */
    List<SysMenuTreeQueryVo> getSysAreaSimplifyPageList(SysAreaQueryParam sysAreaQueryParam);

    /**
     * redis中的省市区结构
     * @return
     */
    List<SysMenuTreeQueryVo> getSysAreaRidesSimplifyPageList();

    Boolean updateStatusById(SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam);

    /**
     * 导入行政区域
     * @param file
     * @return
     */
    Boolean importData(MultipartFile file);

    /**
     * 懒加载数据
     * @param sysAreaQueryParam
     * @return
     */
    Paging<SysAreaLazyQueryVo> getSysAreaPageListLazy(SysAreaQueryParam sysAreaQueryParam);


    /**
     * 省市区添加至redis中
     * @return
     */
    Boolean addAreaToRedis();


    /**
     * 根据类型 跟父节点 查询直属下级
     * @param areaType
     * @param parentCode
     * @return
     */
    List<SysAreaQueryVo> getAreaByAreaType(String areaType,String parentCode);
}
