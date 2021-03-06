package com.io.yy.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.job.param.CheckSchoolCodeParam;
import com.io.yy.shiro.cache.LoginRedisService;
import com.io.yy.shiro.jwt.JwtToken;
import com.io.yy.shiro.vo.LoginSysUserVo;
import com.io.yy.system.entity.SysSchool;
import com.io.yy.system.mapper.SysAreaMapper;
import com.io.yy.system.mapper.SysCollegeExamscopeMapper;
import com.io.yy.system.mapper.SysSchoolCollegeMapper;
import com.io.yy.system.mapper.SysSchoolMapper;
import com.io.yy.system.param.NewSysCollegeExamscopeQueryParam;
import com.io.yy.system.param.NewSysCollegeQueryParam;
import com.io.yy.system.param.SysSchoolParam;
import com.io.yy.system.param.SysSchoolQueryParam;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.service.SysSchoolService;
import com.io.yy.system.vo.NewSysSchoolVo;
import com.io.yy.system.vo.SchoolListQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import com.io.yy.system.vo.SysSchoolQueryVo;
import com.io.yy.util.excel.ExcelImport;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <pre>
 * ????????? ???????????????
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Slf4j
@Service
public class SysSchoolServiceImpl extends BaseServiceImpl<SysSchoolMapper, SysSchool> implements SysSchoolService {

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    @Autowired
    private LoginRedisService loginRedisService;

    @Autowired
    private SysSchoolMapper sysSchoolMapper;

    @Autowired
    private SysSchoolCollegeMapper sysSchoolCollegeMapper;

    @Autowired
    private SysCollegeExamscopeMapper sysCollegeExamscopeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysSchool(SysSchool sysSchool) throws Exception {
        sysSchool.setStatus(1);
        return super.save(sysSchool);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysSchool(SysSchool sysSchool) throws Exception {
        return super.updateById(sysSchool);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysSchool(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysSchools(List<String> idList) throws Exception {
        return super.removeByIds(idList);
    }

    @Override
    public SysSchoolQueryVo getSysSchoolById(Long id) throws Exception {
        return sysSchoolMapper.getSysSchoolById(id);
    }

    @Override
    public Paging<SysSchoolQueryVo> getSysSchoolPageList(SysSchoolQueryParam sysSchoolQueryParam) throws Exception {
        Page page = setPageParam(sysSchoolQueryParam, OrderItem.desc("create_time"));
        IPage<SysSchoolQueryVo> iPage = sysSchoolMapper.getSysSchoolPageList(page, sysSchoolQueryParam);
        return new Paging(iPage);
    }

    public Map<String, Object> assignment(List<SysMenuTreeQueryVo> lists, String provinceName, String cityName, String districtName) {
        Map<String, Object> map = MapUtil.newHashMap();
        Boolean judge = false;
        for (SysMenuTreeQueryVo sys : lists) {
            if (!judge) {
                if (StrUtil.isNotBlank(provinceName) && sys.getLabel().equals(provinceName)) {
                    map.put("provinceCode", sys.getId());
                    map.put("provinceName", sys.getLabel());
                    judge = true;
                    if (ObjectUtil.isNotEmpty(sys.getChildren())) {
                        for (SysMenuTreeQueryVo sys1 : sys.getChildren()) {
                            if (StrUtil.isNotBlank(cityName) && sys1.getLabel().equals(cityName)) {
                                map.put("cityCode", sys1.getId());
                                map.put("cityName", sys1.getLabel());
                                judge = true;
                                if (ObjectUtil.isNotEmpty(sys1.getChildren())) {
                                    for (SysMenuTreeQueryVo sys2 : sys1.getChildren()) {
                                        if (StrUtil.isNotBlank(districtName)) {
                                            if (sys2.getLabel().equals(districtName)) {
                                                map.put("districtCode", sys2.getId());
                                                map.put("districtName", sys2.getLabel());
                                                judge = true;
                                                break;
                                            }
                                        } else {
                                            judge = true;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }

        }
        map.put("judge", judge);
        return map;
    }

    /**
     * ????????????
     *
     * @param file
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean importData(MultipartFile file) {
        //???????????????????????????????????????
        //???????????????
        //???????????????
        //??????????????????

        //???????????????????????????????????????????????????????????????????????????

        //?????????????????????key??????key???????????????????????????????????????????????????????????????
        //????????????key????????????????????????????????????????????????????????????????????????????????????????????????
        //??????????????????????????????
        //????????????????????????
        //????????????????????????
        List<SysMenuTreeQueryVo> lists1 = (List<SysMenuTreeQueryVo>) redisTemplate.opsForValue().get("city:area:redisSysArea");
        Boolean judge = true;
        try {
            ExcelImport excelImport = new ExcelImport(file, 2, 0);
            List<NewSysSchoolVo> list = excelImport.getDataList(NewSysSchoolVo.class);
            Date date = new Date();
            //??????
            List<String> SchoolCodeList=list.stream().map(NewSysSchoolVo::getSchoolCode).collect(Collectors.toList());
            //?????????????????????????????????????????????????????????????????????
            List<SysSchool> schoolList = sysSchoolMapper.getSysSchoolBySchoolCodeList(SchoolCodeList);
            Map<String, SysSchool> mapSchool = MapUtil.newHashMap();
            for(SysSchool sysSchool :schoolList){
                mapSchool.put(sysSchool.getSchoolCode(), sysSchool);
            }
            List<SysSchool> listSchool = CollUtil.newArrayList();
            List<NewSysCollegeQueryParam> listCollege = CollUtil.newArrayList();
            List<NewSysCollegeExamscopeQueryParam> listCollegeExamscope = CollUtil.newArrayList();
            for (NewSysSchoolVo newSysSchool : list) {
                if (StrUtil.isBlank(newSysSchool.getSchoolCode())) {
                    throw new BusinessException("?????????????????????");
                }
                if (StrUtil.isBlank(newSysSchool.getSchoolName())) {
                    throw new BusinessException("?????????????????????");
                }
/*                if(ObjectUtil.isNotNull(mapSchool.get(newSysSchool.getSchoolCode()))){
                    throw new BusinessException("????????????????????????????????????");
                }*/
                if (StrUtil.isBlank(newSysSchool.getFacultyDepartmentName())) {
                    throw new BusinessException("????????????????????????");
                }
                if (StrUtil.isBlank(newSysSchool.getCollegeName())) {
                    throw new BusinessException("??????????????????");
                }
                if (StrUtil.isBlank(newSysSchool.getResearchDirectionName())) {
                    throw new BusinessException("?????????????????????");
                }
                if (StrUtil.isBlank(newSysSchool.getExamMethodName())) {
                    throw new BusinessException("?????????????????????");
                }
                if (StrUtil.isBlank(newSysSchool.getSubjectCategory())) {
                    throw new BusinessException("?????????????????????");
                }
                if (StrUtil.isBlank(newSysSchool.getStudyMethodName())) {
                    throw new BusinessException("?????????????????????");
                }
                //???????????????????????????????????????????????????????????????????????????????????????
                SysSchool sysSchool = null;
                if (ObjectUtil.isNull(mapSchool.get(newSysSchool.getSchoolCode()))) {
                    Map<String, Object> map = this.assignment(lists1, newSysSchool.getProvinceName(), newSysSchool.getCityName(), newSysSchool.getDistrictName());
                    sysSchool = new SysSchool()
                            .setSchoolCode(newSysSchool.getSchoolCode())
                            .setSchoolName(newSysSchool.getSchoolName())
                            .setSchoolInfo(newSysSchool.getSchoolInfo())
                            .setCreateTime(date)
                            .setCreateBy(this.getLoginSysUserVo().getId() + "")
                            .setStatus(1)
                            .setAddress(newSysSchool.getAddress())
                            .setRemarks(newSysSchool.getRemarks());
                    if (ObjectUtil.isNotEmpty(map) && (Boolean) map.get("judge")) {
                        sysSchool.setCityName(ObjectUtil.isNull(map.get("cityName")) ? null : map.get("cityName").toString())
                                .setCityCode(ObjectUtil.isNull(map.get("cityCode")) ? null : map.get("cityCode").toString())
                                .setDistrictCode(ObjectUtil.isNull(map.get("districtCode")) ? null : map.get("districtCode").toString())
                                .setDistrictName(ObjectUtil.isNull(map.get("districtName")) ? null : map.get("districtName").toString())
                                .setProvinceCode(ObjectUtil.isNull(map.get("provinceCode")) ? null : map.get("provinceCode").toString())
                                .setProvinceName(ObjectUtil.isNull(map.get("provinceName")) ? null : map.get("provinceName").toString());
                    }
                    //??????????????????
                    listSchool.add(sysSchool);
                    mapSchool.put(sysSchool.getSchoolCode(), sysSchool);
                } else {
                    sysSchool = mapSchool.get(newSysSchool.getSchoolCode());
                }

                NewSysCollegeQueryParam newSysCollegeQueryParam = new NewSysCollegeQueryParam()
                        .setSysSchool(sysSchool)
                        .setCollegeCode(this.getCode(newSysSchool.getCollegeName()))
                        .setCollegeName(this.getName(newSysSchool.getCollegeName()))
                        .setFacultyDepartmentName(this.getName(newSysSchool.getFacultyDepartmentName()))
                        .setFacultyDepartmentCode(this.getCode(newSysSchool.getFacultyDepartmentName()))
                        .setResearchDirectionName(this.getName(newSysSchool.getResearchDirectionName()))
                        .setResearchDirectionCode(this.getCode(newSysSchool.getFacultyDepartmentName()))
                        .setExamMethodName(newSysSchool.getExamMethodName())
                        .setExamMethod(this.sysDictDataMatching("scr_exam_method", newSysSchool.getExamMethodName()))
//                        .setDegreeTypeName(newSysSchool.getDegreeTypeName())
//                        .setDegreeType(this.sysDictDataMatching("scr_subject_category",newSysSchool.getDegreeTypeName()))
                        .setStudyMethodName(newSysSchool.getStudyMethodName())
                        .setStudyMethod(this.sysDictDataMatching("scr_study_method", newSysSchool.getStudyMethodName()))
                        .setSubjectCategory(this.sysDictDataMatching("scr_subject_category", newSysSchool.getSubjectCategory()))
                        .setSubjectCategoryName(newSysSchool.getSubjectCategory())
                        .setDegreeType("")
                        .setDegreeTypeName("")
                        .setCreateBy(this.getLoginSysUserVo().getId() + "")
                        .setCreateTime(date);
                listCollege.add(newSysCollegeQueryParam);

                NewSysCollegeExamscopeQueryParam collegeExamscope = new NewSysCollegeExamscopeQueryParam()
                        .setCollege(newSysCollegeQueryParam)
                        .setSubOneName(newSysSchool.getSubOneName())
                        .setSubTwoName(newSysSchool.getSubTwoName())
                        .setSubThreeName(newSysSchool.getSubThreeName())
                        .setSubFourName(newSysSchool.getSubFourName())
                        .setCreateBy(this.getLoginSysUserVo().getId() + "")
                        .setCreateTime(date)
                        .setStatus(1);
                listCollegeExamscope.add(collegeExamscope);
            }
            if(CollUtil.isNotEmpty(listSchool)){
                sysSchoolMapper.insertBatch(listSchool);
            }
            if(CollUtil.isNotEmpty(listCollege)){
                sysSchoolCollegeMapper.insertBatch(listCollege);
            }
            if(CollUtil.isNotEmpty(listCollegeExamscope)){
                sysCollegeExamscopeMapper.insertBatch(listCollegeExamscope);
            }
            //judge = super.saveBatch(list);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getCause() instanceof MySQLIntegrityConstraintViolationException){
                throw new BusinessException("???????????????????????????????????????????????????");
            }else{
                throw new BusinessException(e.getMessage());
            }
        }
        return judge;
    }

    public String sysDictDataMatching(String type, String label) throws BusinessException {
        String code = sysDictDataRedisService.getSysDictDataValue(type, label);
        if ((type.equals("scr_exam_method") || type.equals("scr_study_method")) && StrUtil.isNotBlank(code)) {
            return code;
        } else if (StrUtil.isNotBlank(code) && code.equals(this.getCode(label))) {
            return code;
        } else {
            if (type.equals("scr_subject_category")) {
                throw new BusinessException("????????????\"" + label + "\"????????????");
            }
            if (type.equals("scr_exam_method")) {
                throw new BusinessException("????????????\"" + label + "\"????????????");
            }
            if (type.equals("scr_study_method")) {
                throw new BusinessException("????????????\"" + label + "\"????????????");
            }
            return null;
        }
    }

    public String getName(String str) {
        if (StrUtil.isNotBlank(str)) {
            return str.substring(str.indexOf(")")+1);
        } else {
            return null;
        }
    }

    public String getCode(String str) {
        if (StrUtil.isNotBlank(str)) {
            return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
        } else {
            return null;
        }
    }

    public LoginSysUserVo getLoginSysUserVo() {
        JwtToken jwtToken = (JwtToken) SecurityUtils.getSubject().getPrincipal();
        if (ObjectUtil.isNull(jwtToken)) {
            throw new BusinessException("????????????");
        }
        String username = jwtToken.getUsername();
        if (StrUtil.isBlank(username)) {
            throw new BusinessException("???????????????");
        }
        LoginSysUserVo loginSysUserVo = loginRedisService.getLoginSysUserVo(username);
        if (ObjectUtil.isNull(loginSysUserVo)) {
            throw new BusinessException("???????????????");
        }
        return loginSysUserVo;
    }


    @Override
    public List<SchoolListQueryVo> getSchoolListByCode(SysSchoolParam sysSchoolParam) {
        return sysSchoolMapper.getSchoolListByCode(sysSchoolParam);
    }

    @Override
    public Boolean checkSchoolCodeValid(CheckSchoolCodeParam checkSchoolCodeParam) {
        QueryWrapper<SysSchool> schoolCodeQueryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotNull(checkSchoolCodeParam.getId())) {
            schoolCodeQueryWrapper
                    .ne("id", checkSchoolCodeParam.getId())
                    .eq("school_code", checkSchoolCodeParam.getSchoolCode());
        } else {
            schoolCodeQueryWrapper
                    .eq("school_code", checkSchoolCodeParam.getSchoolCode());
        }
        return !(sysSchoolMapper.selectCount(schoolCodeQueryWrapper) >= 1);
    }

    @Override
    public List<SchoolListQueryVo> getSchoolList() throws Exception {
        return sysSchoolMapper.getSchoolList();
    }

}
