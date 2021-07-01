package com.io.yy.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.system.entity.SysSchoolCollege;
import com.io.yy.system.mapper.SysSchoolCollegeMapper;
import com.io.yy.system.service.SysSchoolCollegeService;
import com.io.yy.system.param.SysSchoolCollegeQueryParam;
import com.io.yy.system.vo.SysSchoolCollegeQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SchoolCollegeListQueryVo;
import com.io.yy.util.excel.ExcelImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <pre>
 * 学校专业表 服务实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Slf4j
@Service
public class SysSchoolCollegeServiceImpl extends BaseServiceImpl<SysSchoolCollegeMapper, SysSchoolCollege> implements SysSchoolCollegeService {

    @Autowired
    private SysSchoolCollegeMapper sysSchoolCollegeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysSchoolCollege(SysSchoolCollege sysSchoolCollege) throws Exception {
        sysSchoolCollege.setStatus(1);
        return super.save(sysSchoolCollege);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysSchoolCollege(SysSchoolCollege sysSchoolCollege) throws Exception {
        return super.updateById(sysSchoolCollege);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysSchoolCollege(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysSchoolColleges(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysSchoolCollegeQueryVo getSysSchoolCollegeById(Long id) throws Exception {
        return sysSchoolCollegeMapper.getSysSchoolCollegeById(id);
    }

    @Override
    public Paging<SysSchoolCollegeQueryVo> getSysSchoolCollegePageList(SysSchoolCollegeQueryParam sysSchoolCollegeQueryParam) throws Exception {
        Page page = setPageParam(sysSchoolCollegeQueryParam, OrderItem.desc("create_time"));
        IPage<SysSchoolCollegeQueryVo> iPage = sysSchoolCollegeMapper.getSysSchoolCollegePageList(page, sysSchoolCollegeQueryParam);
        return new Paging(iPage);
    }

    /**
     * 导入专业
     *
     * @param file
     * @return
     */
    @Override
    public Boolean importData(MultipartFile file,String schoolId) {
        Boolean judge = false;
        try {
            ExcelImport excelImport = new ExcelImport(file, 1, 0);
            List<SysSchoolCollege> list = excelImport.getDataList(SysSchoolCollege.class);
            for (SysSchoolCollege sysSchoolCollege : list) {
                if (StrUtil.isBlank(sysSchoolCollege.getCollegeCode())) {
                    throw new BusinessException("未填写专业代码");
                }
                if (StrUtil.isBlank(sysSchoolCollege.getCollegeName())) {
                    throw new BusinessException("未填写专业名称");
                }
                sysSchoolCollege.setStatus(1);
                sysSchoolCollege.setSchoolId(Long.parseLong(schoolId));
            }
            judge = super.saveBatch(list);
        } catch (BusinessException e){
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return judge;
    }

    @Override
    public List<SchoolCollegeListQueryVo> getSchooCollegelList(Long schoolId) throws Exception {
        return sysSchoolCollegeMapper.getSchooCollegelList(schoolId);
    }

    @Override
    public List<SchoolCollegeListQueryVo> getSimpleCollegeListBySchoolId(Long schoolId) throws Exception {
        return sysSchoolCollegeMapper.getSimpleCollegeListBySchoolId(schoolId);
    }

    @Override
    public List<SchoolCollegeListQueryVo> getSimpleFacultyListBySchoolId(Long schoolId) throws Exception {
        return sysSchoolCollegeMapper.getSimpleFacultyListBySchoolId(schoolId);
    }

    @Override
    public List<SchoolCollegeListQueryVo> getSimpleCollegeListBySchoolIdAndFacultyCode(Long schoolId,String facultyCode) throws Exception {
        return sysSchoolCollegeMapper.getSimpleCollegeListBySchoolIdAndFacultyCode(schoolId,facultyCode);
    }


}
