package com.io.yy.system.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysSchoolCollege;
import com.io.yy.system.param.SysSchoolCollegeQueryParam;
import com.io.yy.system.service.SysSchoolCollegeService;
import com.io.yy.system.vo.SchoolCollegeListQueryVo;
import com.io.yy.system.vo.SysSchoolCollegeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 学校专业表 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Slf4j
@RestController
@RequestMapping("/sysSchoolCollege")
@Api("学校专业表 API")
public class SysSchoolCollegeController extends BaseController {

    @Autowired
    private SysSchoolCollegeService sysSchoolCollegeService;

    /**
     * 添加学校专业表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:school:college:add")
    @ApiOperation(value = "添加SysSchoolCollege对象", notes = "添加学校专业表", response = ApiResult.class)
    public ApiResult<Boolean> addSysSchoolCollege(@Valid @RequestBody SysSchoolCollege sysSchoolCollege) throws Exception {
        boolean flag = sysSchoolCollegeService.saveSysSchoolCollege(sysSchoolCollege);
        return ApiResult.result(flag);
    }

    /**
     * 修改学校专业表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:school:college:update")
    @ApiOperation(value = "修改SysSchoolCollege对象", notes = "修改学校专业表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysSchoolCollege(@Valid @RequestBody SysSchoolCollege sysSchoolCollege) throws Exception {
        boolean flag = sysSchoolCollegeService.updateSysSchoolCollege(sysSchoolCollege);
        return ApiResult.result(flag);
    }

    /**
     * 删除学校专业表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:school:college:delete")
    @ApiOperation(value = "删除SysSchoolCollege对象", notes = "删除学校专业表", response = Boolean.class)
    public ApiResult<Boolean> deleteSysSchoolCollege(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysSchoolCollegeService.deleteSysSchoolCollege(id);
        return ApiResult.ok(flag);
    }

    /**
     * 批量删除学校专业表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:school:college:delete")
    @ApiOperation(value = "批量删除SysSchoolCollege对象", notes = "批量删除学校专业表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysSchoolCollege(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysSchoolCollegeService.deleteSysSchoolColleges(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取学校专业表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:school:college:info")
    @ApiOperation(value = "获取SysSchoolCollege对象详情", notes = "查看学校专业表", response = SysSchoolCollegeQueryVo.class)
    public ApiResult<SysSchoolCollegeQueryVo> getSysSchoolCollege(@PathVariable("id") Long id) throws Exception {
        SysSchoolCollegeQueryVo sysSchoolCollegeQueryVo = sysSchoolCollegeService.getSysSchoolCollegeById(id);
        return ApiResult.ok(sysSchoolCollegeQueryVo);
    }

    /**
     * 学校专业表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:school:college:page")
    @ApiOperation(value = "获取SysSchoolCollege分页列表", notes = "学校专业表分页列表", response = SysSchoolCollegeQueryVo.class)
    public ApiResult<Paging<SysSchoolCollegeQueryVo>> getSysSchoolCollegePageList(@Valid @RequestBody SysSchoolCollegeQueryParam sysSchoolCollegeQueryParam) throws Exception {
        Paging<SysSchoolCollegeQueryVo> paging = sysSchoolCollegeService.getSysSchoolCollegePageList(sysSchoolCollegeQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 导入专业
     *
     * @param file
     * @return
     */
    @PostMapping("/import")
    @RequiresPermissions("sys:school:college:import")
    @ApiOperation(value = "导入专业", notes = "导入专业")
    public ApiResult<Boolean> importData(@RequestParam("file") MultipartFile file, String schoolId) {
        try {
            return ApiResult.ok(sysSchoolCollegeService.importData(file, schoolId));
        } catch (Exception e) {
            return ApiResult.ok(false, e.getMessage());
        }
    }

    /**
     * 根据学校查询专业集合
     */
    @PostMapping("/getSchooCollegelList/{schoolId}")
    @RequiresPermissions("sys:school:college:getSchooCollegelList")
    @ApiOperation(value = "获取学校专业集合", notes = "获取学校专业集合", response = SchoolCollegeListQueryVo.class)
    public ApiResult<List<SchoolCollegeListQueryVo>> getSchooCollegelList(@PathVariable("schoolId") Long schoolId) throws Exception {
        List<SchoolCollegeListQueryVo> schoolCollegeList = sysSchoolCollegeService.getSchooCollegelList(schoolId);
        return ApiResult.ok(schoolCollegeList);
    }

    /**
     * 根据学校查询专业集合
     */
    @GetMapping("/getSchoolCollegeListForWx/{schoolId}")
    @ApiOperation(value = "获取学校专业集合", notes = "获取学校专业集合", response = SchoolCollegeListQueryVo.class)
    public ApiResult<List<SchoolCollegeListQueryVo>> getSchoolCollegeListForWx(@PathVariable("schoolId") Long schoolId) throws Exception {
        List<SchoolCollegeListQueryVo> schoolCollegeList = sysSchoolCollegeService.getSimpleCollegeListBySchoolId(schoolId);
        return ApiResult.ok(schoolCollegeList);
    }

}

