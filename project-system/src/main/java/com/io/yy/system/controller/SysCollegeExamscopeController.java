package com.io.yy.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysCollegeExamscope;
import com.io.yy.system.param.SysCollegeExamscopeQueryParam;
import com.io.yy.system.service.SysCollegeExamscopeService;
import com.io.yy.system.vo.CollegeExamscopeVo;
import com.io.yy.system.vo.SysCollegeExamscopeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 考试范围表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-14
 */
@Slf4j
@RestController
@RequestMapping("/sysCollegeExamscope")
@Api("考试范围表 API")
public class SysCollegeExamscopeController extends BaseController {

    @Autowired
    private SysCollegeExamscopeService sysCollegeExamscopeService;

    /**
     * 添加考试范围表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:college:examscope:add")
    @ApiOperation(value = "添加SysCollegeExamscope对象", notes = "添加考试范围表", response = ApiResult.class)
    public ApiResult<Boolean> addSysCollegeExamscope(@RequestBody Map<String, Object> params) throws Exception {
        boolean flag = true;
        Object examscopeList = params.get("examscopeList");
        Object sysCollegeIds = params.get("sysCollegeIds");
        List<String> collegeIds = (List<String>)sysCollegeIds;
        //批量添加之前把之前的考试范围删除
        sysCollegeExamscopeService.deleteCollegeIds(collegeIds);
        String s = JSONObject.toJSONString(examscopeList);
        List<CollegeExamscopeVo> collegeExamscopeVos = JSONObject.parseArray(s, CollegeExamscopeVo.class);
        for(String collegeId : collegeIds){
            SysCollegeExamscope sysCollegeExamscope = new SysCollegeExamscope();
            sysCollegeExamscope.setSubOneOrdinal(collegeExamscopeVos.get(0).getXh());
            sysCollegeExamscope.setSubOneCode(collegeExamscopeVos.get(0).getKmdm());
            sysCollegeExamscope.setSubOneName("政治");
            sysCollegeExamscope.setSubOneRemarks(collegeExamscopeVos.get(0).getBz());
            sysCollegeExamscope.setSubTwoOrdinal(collegeExamscopeVos.get(1).getXh());
            sysCollegeExamscope.setSubTwoCode(collegeExamscopeVos.get(1).getKmdm());
            sysCollegeExamscope.setSubTwoName("外语");
            sysCollegeExamscope.setSubTwoRemarks(collegeExamscopeVos.get(1).getBz());
            sysCollegeExamscope.setSubThreeOrdinal(collegeExamscopeVos.get(2).getXh());
            sysCollegeExamscope.setSubThreeCode(collegeExamscopeVos.get(2).getKmdm());
            sysCollegeExamscope.setSubThreeName(collegeExamscopeVos.get(2).getKmmc());
            sysCollegeExamscope.setSubThreeRemarks(collegeExamscopeVos.get(2).getBz());
            sysCollegeExamscope.setSubFourOrdinal(collegeExamscopeVos.get(3).getXh());
            sysCollegeExamscope.setSubFourCode(collegeExamscopeVos.get(3).getKmdm());
            sysCollegeExamscope.setSubFourName(collegeExamscopeVos.get(3).getKmmc());
            sysCollegeExamscope.setSubFourRemarks(collegeExamscopeVos.get(3).getBz());
            sysCollegeExamscope.setCollegeId(Long.valueOf(collegeId));
            flag = sysCollegeExamscopeService.saveSysCollegeExamscope(sysCollegeExamscope);
        }
        return ApiResult.result(flag);
    }

    /**
     * 修改考试范围表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:college:examscope:update")
    @ApiOperation(value = "修改SysCollegeExamscope对象", notes = "修改考试范围表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysCollegeExamscope(@RequestBody Map<String, Object> params) throws Exception {
        boolean flag = true;
        Object examscopeList = params.get("examscopeList");
        String sysCollegeId = (String)params.get("sysCollegeId");
        String s = JSONObject.toJSONString(examscopeList);
        List<CollegeExamscopeVo> collegeExamscopeVos = JSONObject.parseArray(s, CollegeExamscopeVo.class);
        SysCollegeExamscope sysCollegeExamscope = new SysCollegeExamscope();
            sysCollegeExamscope.setSubOneOrdinal(collegeExamscopeVos.get(0).getXh());
            sysCollegeExamscope.setSubOneCode(collegeExamscopeVos.get(0).getKmdm());
            sysCollegeExamscope.setSubOneName("政治");
            sysCollegeExamscope.setSubOneRemarks(collegeExamscopeVos.get(0).getBz());
            sysCollegeExamscope.setSubTwoOrdinal(collegeExamscopeVos.get(1).getXh());
            sysCollegeExamscope.setSubTwoCode(collegeExamscopeVos.get(1).getKmdm());
            sysCollegeExamscope.setSubTwoName("外语");
            sysCollegeExamscope.setSubTwoRemarks(collegeExamscopeVos.get(1).getBz());
            sysCollegeExamscope.setSubThreeOrdinal(collegeExamscopeVos.get(2).getXh());
            sysCollegeExamscope.setSubThreeCode(collegeExamscopeVos.get(2).getKmdm());
            sysCollegeExamscope.setSubThreeName(collegeExamscopeVos.get(2).getKmmc());
            sysCollegeExamscope.setSubThreeRemarks(collegeExamscopeVos.get(2).getBz());
            sysCollegeExamscope.setSubFourOrdinal(collegeExamscopeVos.get(3).getXh());
            sysCollegeExamscope.setSubFourCode(collegeExamscopeVos.get(3).getKmdm());
            sysCollegeExamscope.setSubFourName(collegeExamscopeVos.get(3).getKmmc());
            sysCollegeExamscope.setSubFourRemarks(collegeExamscopeVos.get(3).getBz());
            if(collegeExamscopeVos.get(0).getId() != null){
                sysCollegeExamscope.setId(collegeExamscopeVos.get(0).getId());
                flag = sysCollegeExamscopeService.updateSysCollegeExamscope(sysCollegeExamscope);
            }else{
                sysCollegeExamscope.setCollegeId(Long.valueOf(sysCollegeId));
                flag = sysCollegeExamscopeService.saveSysCollegeExamscope(sysCollegeExamscope);
            }

        return ApiResult.result(flag);
    }

    /**
     * 删除考试范围表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:college:examscope:delete")
    @ApiOperation(value = "删除SysCollegeExamscope对象", notes = "删除考试范围表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysCollegeExamscope(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysCollegeExamscopeService.deleteSysCollegeExamscope(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除考试范围表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:college:examscope:delete")
    @ApiOperation(value = "批量删除SysCollegeExamscope对象", notes = "批量删除考试范围表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysCollegeExamscope(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysCollegeExamscopeService.deleteSysCollegeExamscopes(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取考试范围表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:college:examscope:info")
    @ApiOperation(value = "获取SysCollegeExamscope对象详情", notes = "查看考试范围表", response = SysCollegeExamscopeQueryVo.class)
    public ApiResult<SysCollegeExamscopeQueryVo> getSysCollegeExamscope(@PathVariable("id") Long id) throws Exception {
        SysCollegeExamscopeQueryVo sysCollegeExamscopeQueryVo = sysCollegeExamscopeService.getSysCollegeExamscopeById(id);
        return ApiResult.ok(sysCollegeExamscopeQueryVo);
    }

    /**
     * 考试范围表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:college:examscope:page")
    @ApiOperation(value = "获取SysCollegeExamscope分页列表", notes = "考试范围表分页列表", response = SysCollegeExamscopeQueryVo.class)
    public ApiResult<Paging<SysCollegeExamscopeQueryVo>> getSysCollegeExamscopePageList(@Valid @RequestBody SysCollegeExamscopeQueryParam sysCollegeExamscopeQueryParam) throws Exception {
        Paging<SysCollegeExamscopeQueryVo> paging = sysCollegeExamscopeService.getSysCollegeExamscopePageList(sysCollegeExamscopeQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 根据专业id获取考试范围表
     */
    @GetMapping("/infoByCollegeId/{collegeId}")
    @RequiresPermissions("sys:college:examscope:infoByCollegeId")
    @ApiOperation(value = "根据专业id获取SysCollegeExamscope对象详情", notes = "查看考试范围表", response = SysCollegeExamscope.class)
    public ApiResult< List<CollegeExamscopeVo>> getSysCollegeExamscopeByCollegeId(@PathVariable("collegeId") Long collegeId) throws Exception {

        return ApiResult.ok(sysCollegeExamscopeService.listAll(collegeId));
    }

    /**
     * 获取考试范围页面
     */
    @GetMapping("/infoByCollegeExam")
    @RequiresPermissions("sys:college:examscope:infoByCollegeId")
    @ApiOperation(value = "根据专业id获取SysCollegeExamscope对象详情", notes = "查看考试范围表", response = SysCollegeExamscope.class)
    public ApiResult< List<CollegeExamscopeVo>> getSysCollegeExamscope() throws Exception {

        return ApiResult.ok(sysCollegeExamscopeService.getSysCollegeExamscope());
    }

}

