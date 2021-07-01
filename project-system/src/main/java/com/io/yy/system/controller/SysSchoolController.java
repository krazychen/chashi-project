package com.io.yy.system.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.job.param.CheckSchoolCodeParam;
import com.io.yy.system.entity.SysSchool;
import com.io.yy.system.param.SysSchoolParam;
import com.io.yy.system.param.SysSchoolQueryParam;
import com.io.yy.system.service.SysSchoolService;
import com.io.yy.system.vo.SchoolListQueryVo;
import com.io.yy.system.vo.SysSchoolQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <pre>
 * 学校表 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Slf4j
@RestController
@RequestMapping("/sysSchool")
@Api("学校表 API")
public class SysSchoolController extends BaseController {

    @Autowired
    private SysSchoolService sysSchoolService;

    /**
     * 添加学校表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:school:add")
    @ApiOperation(value = "添加SysSchool对象", notes = "添加学校表", response = ApiResult.class)
    public ApiResult<Boolean> addSysSchool(@Valid @RequestBody SysSchool sysSchool) throws Exception {
        boolean flag = sysSchoolService.saveSysSchool(sysSchool);
        return ApiResult.result(flag);
    }

    /**
     * 修改学校表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:school:update")
    @ApiOperation(value = "修改SysSchool对象", notes = "修改学校表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysSchool(@Valid @RequestBody SysSchool sysSchool) throws Exception {
        boolean flag = sysSchoolService.updateSysSchool(sysSchool);
        return ApiResult.result(flag);
    }

    /**
     * 删除学校表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:school:delete")
    @ApiOperation(value = "删除SysSchool对象", notes = "删除学校表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysSchool(@PathVariable("id") Long id) throws Exception {
        boolean flag = sysSchoolService.deleteSysSchool(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除学校表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:school:delete")
    @ApiOperation(value = "批量删除SysSchool对象", notes = "批量删除学校表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysSchool(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysSchoolService.deleteSysSchools(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取学校表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:school:info")
    @ApiOperation(value = "获取SysSchool对象详情", notes = "查看学校表", response = SysSchoolQueryVo.class)
    public ApiResult<SysSchoolQueryVo> getSysSchool(@PathVariable("id") Long id) throws Exception {
        SysSchoolQueryVo sysSchoolQueryVo = sysSchoolService.getSysSchoolById(id);
        return ApiResult.ok(sysSchoolQueryVo);
    }

    /**
     * 学校表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:school:page")
    @ApiOperation(value = "获取SysSchool分页列表", notes = "学校表分页列表", response = SysSchoolQueryVo.class)
    public ApiResult<Paging<SysSchoolQueryVo>> getSysSchoolPageList(@Valid @RequestBody SysSchoolQueryParam sysSchoolQueryParam) throws Exception {
        Paging<SysSchoolQueryVo> paging = sysSchoolService.getSysSchoolPageList(sysSchoolQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 导入学校
     * @param file
     * @return
     */
    @PostMapping("/import")
    @RequiresPermissions("sys:school:import")
    @ApiOperation(value = "导入学校", notes = "导入学校")
    public ApiResult uploadExcel(@RequestParam(value = "file", required = false) MultipartFile file){
        try {
            return ApiResult.ok(sysSchoolService.importData(file));
        } catch (Exception e) {
            return ApiResult.ok(false, e.getMessage());
        }
    }

    /**
     * 学校专业模板下载
     */
    @GetMapping("/downloadTemplate")
    @ApiOperation(value = "学校专业模板下载", notes = "学校专业模板下载")
    public void downloadTemplate(HttpServletResponse response) throws Exception {
        //获取文件的相对路径  可在控制台打印查看输出结果
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/school.xlsx");
            InputStream is = classPathResource.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            OutputStream os = response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            //设置响应头，控制浏览器下载该文件
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("school.xlsx", "UTF-8"));
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
            //循环将输入流中的内容读取到缓冲区当中
            os.flush();
            os.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 学校集合查询
     */
    @PostMapping("/getSchoolList")
    @RequiresPermissions("sys:school:getSchoolList")
    @ApiOperation(value = "获取学校集合", notes = "学校集合查询", response = SchoolListQueryVo.class)
    public ApiResult<List<SchoolListQueryVo>> getSchoolList() throws Exception {
        List<SchoolListQueryVo> schoolList = sysSchoolService.getSchoolList();
        return ApiResult.ok(schoolList);
    }

    /**
     * 学校集合查询
     */
    @PostMapping("/getSchoolListByCode")
    @RequiresPermissions("sys:school:getSchoolList")
    @ApiOperation(value = "获取学校集合", notes = "学校集合查询", response = SchoolListQueryVo.class)
    public ApiResult<List<SchoolListQueryVo>> getSchoolListByCode(@Valid @RequestBody SysSchoolParam sysSchoolParam) throws Exception {
        List<SchoolListQueryVo> schoolList = sysSchoolService.getSchoolListByCode(sysSchoolParam);
        return ApiResult.ok(schoolList);
    }

    /**
     * 判断学校code是否存在
     */
    @PostMapping("/checkSchoolCodeValid")
    @RequiresPermissions("sys:school:add")
    @ApiOperation(value = "查看题型名称是否重复", notes = "查看系统题型名是否重复", response = Boolean.class)
    public ApiResult<Boolean> checkSchoolCodeValid(@Valid @RequestBody CheckSchoolCodeParam checkSchoolCodeParam) throws Exception {
        boolean flag = sysSchoolService.checkSchoolCodeValid(checkSchoolCodeParam);
        return ApiResult.ok(flag);
    }


    @PostMapping("/getSchoolListByCodeForWx")
    @ApiOperation(value = "根据省市区获取学校微信接口", notes = "根据省市区获取学校微信接口", response = SchoolListQueryVo.class)
    public ApiResult<List<SchoolListQueryVo>> getSchoolListByCodeForWx(@Valid @RequestBody SysSchoolParam sysSchoolParam) throws Exception {
        List<SchoolListQueryVo> schoolList = sysSchoolService.getSchoolListByCode(sysSchoolParam);
        return ApiResult.ok(schoolList);
    }

}

