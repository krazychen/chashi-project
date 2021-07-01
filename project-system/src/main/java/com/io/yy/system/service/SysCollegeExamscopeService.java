package com.io.yy.system.service;

import com.io.yy.system.entity.SysCollegeExamscope;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysCollegeExamscopeQueryParam;
import com.io.yy.system.vo.CollegeExamscopeVo;
import com.io.yy.system.vo.SysCollegeExamscopeQueryVo;
import com.io.yy.common.vo.Paging;

import java.util.List;


/**
 * <pre>
 * 考试范围表 服务类
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-14
 */
public interface SysCollegeExamscopeService extends BaseService<SysCollegeExamscope> {

    /**
     * 保存
     *
     * @param sysCollegeExamscope
     * @return
     * @throws Exception
     */
    boolean saveSysCollegeExamscope(SysCollegeExamscope sysCollegeExamscope) throws Exception;

    /**
     * 修改
     *
     * @param sysCollegeExamscope
     * @return
     * @throws Exception
     */
    boolean updateSysCollegeExamscope(SysCollegeExamscope sysCollegeExamscope) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysCollegeExamscope(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysCollegeExamscopes(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysCollegeExamscopeQueryVo getSysCollegeExamscopeById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysCollegeExamscopeQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysCollegeExamscopeQueryVo> getSysCollegeExamscopePageList(SysCollegeExamscopeQueryParam sysCollegeExamscopeQueryParam) throws Exception;

    /**
     * 自定义返回
     */
    List<CollegeExamscopeVo> listAll(Long collegeId);

    List<CollegeExamscopeVo> getSysCollegeExamscope()throws Exception;

    boolean deleteCollegeIds(List<String> collegeIds);
}
