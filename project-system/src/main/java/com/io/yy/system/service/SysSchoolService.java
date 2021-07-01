package com.io.yy.system.service;

import com.io.yy.job.param.CheckSchoolCodeParam;
import com.io.yy.system.entity.SysSchool;
import com.io.yy.common.service.BaseService;
import com.io.yy.system.param.SysSchoolParam;
import com.io.yy.system.param.SysSchoolQueryParam;
import com.io.yy.system.vo.SysSchoolQueryVo;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SchoolListQueryVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <pre>
 * 学校表 服务类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
public interface SysSchoolService extends BaseService<SysSchool> {

    /**
     * 保存
     *
     * @param sysSchool
     * @return
     * @throws Exception
     */
    boolean saveSysSchool(SysSchool sysSchool) throws Exception;

    /**
     * 修改
     *
     * @param sysSchool
     * @return
     * @throws Exception
     */
    boolean updateSysSchool(SysSchool sysSchool) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysSchool(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteSysSchools(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    SysSchoolQueryVo getSysSchoolById(Long id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param sysSchoolQueryParam
     * @return
     * @throws Exception
     */
    Paging<SysSchoolQueryVo> getSysSchoolPageList(SysSchoolQueryParam sysSchoolQueryParam) throws Exception;

    List<SchoolListQueryVo> getSchoolList()throws Exception;
    /**
     * 导入学校
     * @param file
     * @return
     */
    Boolean importData(MultipartFile file);

    /**
     * 根据省份id获取省份里的学校
     * @param sysSchoolParam
     * @return
     */
    List<SchoolListQueryVo> getSchoolListByCode(SysSchoolParam sysSchoolParam);

    /**
     * 校验学校代码是否存在
     * @param checkSchoolCodeParam
     * @return
     */
    Boolean checkSchoolCodeValid(CheckSchoolCodeParam checkSchoolCodeParam);
}
