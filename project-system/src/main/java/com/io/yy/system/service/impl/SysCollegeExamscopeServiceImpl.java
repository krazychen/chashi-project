package com.io.yy.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.system.entity.SysCollegeExamscope;
import com.io.yy.system.mapper.SysCollegeExamscopeMapper;
import com.io.yy.system.service.SysCollegeExamscopeService;
import com.io.yy.system.param.SysCollegeExamscopeQueryParam;
import com.io.yy.system.vo.CollegeExamscopeVo;
import com.io.yy.system.vo.SysCollegeExamscopeQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * 考试范围表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-14
 */
@Slf4j
@Service
public class SysCollegeExamscopeServiceImpl extends BaseServiceImpl<SysCollegeExamscopeMapper, SysCollegeExamscope> implements SysCollegeExamscopeService {

    @Autowired
    private SysCollegeExamscopeMapper sysCollegeExamscopeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysCollegeExamscope(SysCollegeExamscope sysCollegeExamscope) throws Exception {
        return super.save(sysCollegeExamscope);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysCollegeExamscope(SysCollegeExamscope sysCollegeExamscope) throws Exception {
        return super.updateById(sysCollegeExamscope);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysCollegeExamscope(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysCollegeExamscopes(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysCollegeExamscopeQueryVo getSysCollegeExamscopeById(Long id) throws Exception {
        return sysCollegeExamscopeMapper.getSysCollegeExamscopeById(id);
    }

    @Override
    public Paging<SysCollegeExamscopeQueryVo> getSysCollegeExamscopePageList(SysCollegeExamscopeQueryParam sysCollegeExamscopeQueryParam) throws Exception {
        Page page = setPageParam(sysCollegeExamscopeQueryParam, OrderItem.desc("create_time"));
        IPage<SysCollegeExamscopeQueryVo> iPage = sysCollegeExamscopeMapper.getSysCollegeExamscopePageList(page, sysCollegeExamscopeQueryParam);
        return new Paging(iPage);
    }

    @Override
    public List<CollegeExamscopeVo> listAll(Long collegeId) {
        QueryWrapper<SysCollegeExamscope> collegeExamscopeQueryWrapper = new QueryWrapper<>();
        collegeExamscopeQueryWrapper.eq("college_id",collegeId);
        SysCollegeExamscope sysCollegeExamscope = getOne(collegeExamscopeQueryWrapper);
        List<CollegeExamscopeVo> list=new ArrayList<>();
        if(sysCollegeExamscope != null){
            CollegeExamscopeVo t1=new CollegeExamscopeVo();
            CollegeExamscopeVo t2=new CollegeExamscopeVo();
            CollegeExamscopeVo t3=new CollegeExamscopeVo();
            CollegeExamscopeVo t4=new CollegeExamscopeVo();
            t1.setId(sysCollegeExamscope.getId());
            t1.setName("科目一");
            if(StrUtil.isBlank(sysCollegeExamscope.getSubOneOrdinal())){
                t1.setXh("1");
            }else {
                t1.setXh(sysCollegeExamscope.getSubOneOrdinal());
            }
            t1.setKmdm(sysCollegeExamscope.getSubOneCode());
            t1.setKmmc(sysCollegeExamscope.getSubOneName());
            t1.setBz(sysCollegeExamscope.getSubOneRemarks());

            t2.setId(sysCollegeExamscope.getId());
            t2.setName("科目二");
            if(StrUtil.isBlank(sysCollegeExamscope.getSubTwoOrdinal())){
                t2.setXh("2");
            }else {
                t2.setXh(sysCollegeExamscope.getSubTwoOrdinal());
            }
            t2.setKmdm(sysCollegeExamscope.getSubTwoCode());
            t2.setKmmc(sysCollegeExamscope.getSubTwoName());
            t2.setBz(sysCollegeExamscope.getSubTwoRemarks());

            t3.setId(sysCollegeExamscope.getId());
            t3.setName("科目三");
            if(StrUtil.isBlank(sysCollegeExamscope.getSubThreeOrdinal())){
                t3.setXh("3");
            }else {
                t3.setXh(sysCollegeExamscope.getSubThreeOrdinal());
            }
            t3.setKmdm(sysCollegeExamscope.getSubThreeCode());
            t3.setKmmc(sysCollegeExamscope.getSubThreeName());
            t3.setBz(sysCollegeExamscope.getSubThreeRemarks());

            t4.setId(sysCollegeExamscope.getId());
            t4.setName("科目四");
            if(StrUtil.isBlank(sysCollegeExamscope.getSubFourOrdinal())){
                t4.setXh("4");
            }else {
                t4.setXh(sysCollegeExamscope.getSubFourOrdinal());
            }
            t4.setKmdm(sysCollegeExamscope.getSubFourCode());
            t4.setKmmc(sysCollegeExamscope.getSubFourName());
            t4.setBz(sysCollegeExamscope.getSubFourRemarks());
            list.add(t1);
            list.add(t2);
            list.add(t3);
            list.add(t4);
            return list;
        }else{
            CollegeExamscopeVo t1=new CollegeExamscopeVo();
            CollegeExamscopeVo t2=new CollegeExamscopeVo();
            CollegeExamscopeVo t3=new CollegeExamscopeVo();
            CollegeExamscopeVo t4=new CollegeExamscopeVo();
            t1.setName("科目一");
            t1.setXh("1");
            t1.setKmdm("");
            t1.setKmmc("政治");
            t1.setBz("");

            t2.setName("科目二");
            t2.setXh("2");
            t2.setKmdm("");
            t2.setKmmc("外语");
            t2.setBz("");

            t3.setName("科目三");
            t3.setXh("3");
            t3.setKmdm("");
            t3.setKmmc("");
            t3.setBz("");

            t4.setName("科目四");
            t4.setXh("4");
            t4.setKmdm("");
            t4.setKmmc("");
            t4.setBz("");
            list.add(t1);
            list.add(t2);
            list.add(t3);
            list.add(t4);
            return list;
        }

    }

    @Override
    public List<CollegeExamscopeVo> getSysCollegeExamscope() throws Exception {
        List<CollegeExamscopeVo> list=new ArrayList<>();
        CollegeExamscopeVo t1=new CollegeExamscopeVo();
        CollegeExamscopeVo t2=new CollegeExamscopeVo();
        CollegeExamscopeVo t3=new CollegeExamscopeVo();
        CollegeExamscopeVo t4=new CollegeExamscopeVo();
        t1.setName("科目一");
        t1.setXh("1");
        t1.setKmdm("");
        t1.setKmmc("政治");
        t1.setBz("");

        t2.setName("科目二");
        t2.setXh("2");
        t2.setKmdm("");
        t2.setKmmc("外语");
        t2.setBz("");

        t3.setName("科目三");
        t3.setXh("3");
        t3.setKmdm("");
        t3.setKmmc("");
        t3.setBz("");

        t4.setName("科目四");
        t4.setXh("4");
        t4.setKmdm("");
        t4.setKmmc("");
        t4.setBz("");
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        return list;
    }

    @Override
    public boolean deleteCollegeIds(List<String> collegeIds) {
        boolean b = sysCollegeExamscopeMapper.deleteCollegeIds(collegeIds);
        return b;
    }

}
