package com.io.yy.wxmngt.controller;

import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.util.UploadUtil;
import com.io.yy.wxmngt.entity.CsAdvertise;
import com.io.yy.wxmngt.service.CsAdvertiseService;
import com.io.yy.wxmngt.param.CsAdvertiseQueryParam;
import com.io.yy.wxmngt.vo.CsAdvertiseQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import com.io.yy.common.vo.Paging;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * 广告设置 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-02
 */
@Slf4j
@RestController
@RequestMapping("/csAdvertise")
@Api("广告设置 API")
public class CsAdvertiseController extends BaseController {

    @Autowired
    private CsAdvertiseService csAdvertiseService;

    @Autowired
    private WhyySystemProperties whyySystemProperties;
    /**
     * 添加广告设置
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:advertise:add")
    @ApiOperation(value = "添加CsAdvertise对象", notes = "添加广告设置", response = ApiResult.class)
    public ApiResult<Boolean> addCsAdvertise(HttpServletRequest request, @ModelAttribute CsAdvertise csAdvertise) throws Exception {

        //上传目录
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        //logo
        MultipartFile[] uploadfiles=csAdvertise.getUploadFile();
        String fileNames="";
        String fileUrls="";
        if(ArrayUtils.isNotEmpty(uploadfiles)) {
            for (int i = 0; i < uploadfiles.length; i++) {
                MultipartFile uploadF = uploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != uploadfiles.length - 1) {
                    fileNames += uploadF.getOriginalFilename() + ",";
                    fileUrls += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    fileNames += uploadF.getOriginalFilename();
                    fileUrls += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }

        csAdvertise.setPicUrl(fileUrls);
        csAdvertise.setPicName(fileNames);

        boolean flag = csAdvertiseService.saveCsAdvertise(csAdvertise);
        return ApiResult.result(flag);
    }

    /**
     * 修改广告设置
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:advertise:update")
    @ApiOperation(value = "修改CsAdvertise对象", notes = "修改广告设置", response = ApiResult.class)
    public ApiResult<Boolean> updateCsAdvertise(HttpServletRequest request, @ModelAttribute CsAdvertise csAdvertise) throws Exception {

        //上传目录 --
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        // 先删除之前的图片
        String[] uploadFileDel = csAdvertise.getUploadFileDel();
        if(ArrayUtils.isNotEmpty(uploadFileDel)) {
            for (int i = 0; i < uploadFileDel.length; i++) {
                UploadUtil.deleteQuietly(uploadPath, uploadFileDel[i]);
            }
        }

        //logo 新增文件
        MultipartFile[] uploadfiles=csAdvertise.getUploadFileAdd();
        String fileNames="";
        String fileUrls="";
        if(ArrayUtils.isNotEmpty(uploadfiles)) {
            for (int i = 0; i < uploadfiles.length; i++) {
                MultipartFile uploadF = uploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != uploadfiles.length - 1) {
                    fileNames += uploadF.getOriginalFilename() + ",";
                    fileUrls += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    fileNames += uploadF.getOriginalFilename();
                    fileUrls += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
            csAdvertise.setPicUrl(fileUrls);
            csAdvertise.setPicName(fileNames);
        }

        boolean flag = csAdvertiseService.updateCsAdvertise(csAdvertise);
        return ApiResult.result(flag);
    }

    /**
     * 删除广告设置
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:advertise:delete")
    @ApiOperation(value = "删除CsAdvertise对象", notes = "删除广告设置", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsAdvertise(@PathVariable("id") Long id) throws Exception {
        boolean flag = csAdvertiseService.deleteCsAdvertise(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除广告设置
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:advertise:delete")
    @ApiOperation(value = "批量删除CsAdvertise对象", notes = "批量删除广告设置", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsAdvertise(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csAdvertiseService.deleteCsAdvertises(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取广告设置
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:advertise:info")
    @ApiOperation(value = "获取CsAdvertise对象详情", notes = "查看广告设置", response = CsAdvertiseQueryVo.class)
    public ApiResult<CsAdvertiseQueryVo> getCsAdvertise(@PathVariable("id") Long id) throws Exception {
        CsAdvertiseQueryVo csAdvertiseQueryVo = csAdvertiseService.getCsAdvertiseById(id);
        return ApiResult.ok(csAdvertiseQueryVo);
    }

    /**
     * 广告设置分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:advertise:page")
    @ApiOperation(value = "获取CsAdvertise分页列表", notes = "广告设置分页列表", response = CsAdvertiseQueryVo.class)
    public ApiResult<Paging<CsAdvertiseQueryVo>> getCsAdvertisePageList(@Valid @RequestBody CsAdvertiseQueryParam csAdvertiseQueryParam) throws Exception {
        Paging<CsAdvertiseQueryVo> paging = csAdvertiseService.getCsAdvertisePageList(csAdvertiseQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 修改status状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:advertise:update")
    @ApiOperation(value = "修改status状态", notes = "修改status状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatusById(@Valid @RequestBody CsAdvertiseQueryParam csAdvertiseQueryPara){
        return ApiResult.ok(csAdvertiseService.updateStatusById(csAdvertiseQueryPara));
    }
}

