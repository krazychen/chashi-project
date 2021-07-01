package com.io.yy.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.system.entity.SysSchoolCollege;
import com.io.yy.system.param.NewSysCollegeQueryParam;
import com.io.yy.system.param.SysSchoolCollegeQueryParam;
import com.io.yy.system.vo.SysSchoolCollegeQueryVo;
import com.io.yy.system.vo.SchoolCollegeListQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 学校专业表 Mapper 接口
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Repository
public interface SysSchoolCollegeMapper extends BaseMapper<SysSchoolCollege> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    SysSchoolCollegeQueryVo getSysSchoolCollegeById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param sysSchoolCollegeQueryParam
     * @return
     */
    IPage<SysSchoolCollegeQueryVo> getSysSchoolCollegePageList(@Param("page") Page page, @Param("param") SysSchoolCollegeQueryParam sysSchoolCollegeQueryParam);

    List<SchoolCollegeListQueryVo> getSchooCollegelList(@Param("schoolId") Long schoolId);

    SysSchoolCollegeQueryVo findByCollegeName(@Param("schoolId") Long schoolId, @Param("collegeName") String collegeName);

    /**
     * 批量保存
     * @param listCollege
     */
    void insertBatch(@Param("listCollege") List<NewSysCollegeQueryParam> listCollege);

    /**
     * 获取简单的 专业名称
     * @param schoolId
     * @return
     */
    List<SchoolCollegeListQueryVo> getSimpleCollegeListBySchoolId(@Param("schoolId") Long schoolId);


    /**
     * 根据学校查找院校 去重
     * @param schoolId
     * @return
     */
    List<SchoolCollegeListQueryVo> getSimpleFacultyListBySchoolId(@Param("schoolId") Long schoolId);

    /**
     * 根据 学校id 院校代码 获取专业
     * @param schoolId
     * @param facultyCode
     * @return
     */
    List<SchoolCollegeListQueryVo> getSimpleCollegeListBySchoolIdAndFacultyCode(@Param("schoolId") Long schoolId,@Param("facultyCode") String facultyCode);



}
