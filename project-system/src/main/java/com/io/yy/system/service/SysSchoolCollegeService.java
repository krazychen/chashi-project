package com.io.yy.system.service;

import com.io.yy.system.entity.SysSchoolCollege;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysSchoolCollegeQueryParam;
import com.io.yy.system.vo.SysSchoolCollegeQueryVo;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SchoolCollegeListQueryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <pre>
 * 学校专业表 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
public interface SysSchoolCollegeService extends BaseService<SysSchoolCollege> {

    /**
     * 保存
     *
     * @param sysSchoolCollege
     * @return
     * @throws Exception
     */
    boolean saveSysSchoolCollege(SysSchoolCollege sysSchoolCollege) throws Exception;

    /**
     * 修改
     *
     * @param sysSchoolCollege
     * @return
     * @throws Exception
     */
    boolean updateSysSchoolCollege(SysSchoolCollege sysSchoolCollege) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysSchoolCollege(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysSchoolColleges(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysSchoolCollegeQueryVo getSysSchoolCollegeById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysSchoolCollegeQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysSchoolCollegeQueryVo> getSysSchoolCollegePageList(SysSchoolCollegeQueryParam sysSchoolCollegeQueryParam) throws Exception;

    List<SchoolCollegeListQueryVo> getSchooCollegelList(Long schoolId)throws Exception;

    /**
     * 导入专业
     * @param file
     * @return
     */
    Boolean importData(MultipartFile file, String schoolId);

    /**
     * 获取简单的 专业名称
     * @param schoolId
     * @return
     */
    List<SchoolCollegeListQueryVo> getSimpleCollegeListBySchoolId(Long schoolId) throws Exception;

    /**
     * 根据学校查找院校 去重
     * @param schoolId
     * @return
     */
    List<SchoolCollegeListQueryVo> getSimpleFacultyListBySchoolId(Long schoolId) throws Exception;

    /**
     * 根据 学校id 院校代码 获取专业
     * @param schoolId
     * @param facultyCode
     * @return
     */
    List<SchoolCollegeListQueryVo> getSimpleCollegeListBySchoolIdAndFacultyCode(Long schoolId,String facultyCode) throws Exception;


}
