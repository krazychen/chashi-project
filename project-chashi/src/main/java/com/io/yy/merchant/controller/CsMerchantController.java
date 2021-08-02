package com.io.yy.merchant.controller;

import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.service.CsMerchantService;
import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.merchant.vo.CsMerchantQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.system.entity.SysConfig;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.util.UploadUtil;
import com.io.yy.util.lang.StringUtils;
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
import com.io.yy.common.param.IdParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * 商家管理 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-07-23
 */
@Slf4j
@RestController
@RequestMapping("/csMerchant")
@Api("商家管理 API")
public class CsMerchantController extends BaseController {

    @Autowired
    private CsMerchantService csMerchantService;

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    /**
     * 添加商家管理
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:merchant:add")
    @ApiOperation(value = "添加CsMerchant对象", notes = "添加商家管理", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchant(@Valid @RequestBody CsMerchant csMerchant) throws Exception {
        boolean flag = csMerchantService.saveCsMerchant(csMerchant);
        return ApiResult.result(flag);
    }

    /**
     * 修改商家管理
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:merchant:update")
    @ApiOperation(value = "修改CsMerchant对象", notes = "修改商家管理", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMerchant(@Valid @RequestBody CsMerchant csMerchant) throws Exception {
        boolean flag = csMerchantService.updateCsMerchant(csMerchant);
        return ApiResult.result(flag);
    }

    /**
     * 删除商家管理
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:merchant:delete")
    @ApiOperation(value = "删除CsMerchant对象", notes = "删除商家管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMerchant(@PathVariable("id") Long id) throws Exception {
        boolean flag = csMerchantService.deleteCsMerchantAOffice(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除商家管理
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:merchant:delete")
    @ApiOperation(value = "批量删除CsMerchant对象", notes = "批量删除商家管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMerchant(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csMerchantService.deleteCsMerchants(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取商家管理
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:merchant:info")
    @ApiOperation(value = "获取CsMerchant对象详情", notes = "查看商家管理", response = CsMerchantQueryVo.class)
    public ApiResult<CsMerchantQueryVo> getCsMerchant(@PathVariable("id") Long id) throws Exception {
        CsMerchantQueryVo csMerchantQueryVo = csMerchantService.getCsMerchantById(id);
        return ApiResult.ok(csMerchantQueryVo);
    }

    /**
     * 商家管理分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:merchant:page")
    @ApiOperation(value = "获取CsMerchant分页列表", notes = "商家管理分页列表", response = CsMerchantQueryVo.class)
    public ApiResult<Paging<CsMerchantQueryVo>> getCsMerchantPageList(@Valid @RequestBody CsMerchantQueryParam csMerchantQueryParam) throws Exception {
        Paging<CsMerchantQueryVo> paging = csMerchantService.getCsMerchantPageList(csMerchantQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 添加参数配置表
     */
    @PostMapping("/addCsMerchant")
    @RequiresPermissions("cs:merchant:add")
    @ApiOperation(value = "添加CsMerchant对象", notes = "添加商店表", response = ApiResult.class)
    public ApiResult<Boolean> addCsMerchant(HttpServletRequest request, @ModelAttribute CsMerchant csMerchant) throws Exception {
//        System.out.println(sysConfig.getConfigName());
        //图片对象，需要进行转换

        //上传目录
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        //logo
        MultipartFile[] logoUploadfiles=csMerchant.getLogoUploadFile();
        String logoFileNames="";
        String logoFileOriNames="";
        if(ArrayUtils.isNotEmpty(logoUploadfiles)) {
            for (int i = 0; i < logoUploadfiles.length; i++) {
                MultipartFile uploadF = logoUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != logoUploadfiles.length - 1) {
                    logoFileOriNames += uploadF.getOriginalFilename() + ",";
                    logoFileNames += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    logoFileOriNames += uploadF.getOriginalFilename();
                    logoFileNames += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }
        System.out.println(logoFileNames);
        System.out.println(logoFileOriNames);
        csMerchant.setLogoUrlValue(logoFileNames);
        csMerchant.setLogoUrlName(logoFileOriNames);

        //banner
        MultipartFile[] bannerUploadfiles=csMerchant.getBannerUploadFile();
        String bannerFileNames="";
        String bannerFileOriNames="";
        if(ArrayUtils.isNotEmpty(bannerUploadfiles)) {
            for (int i = 0; i < bannerUploadfiles.length; i++) {
                MultipartFile uploadF = bannerUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != bannerUploadfiles.length - 1) {
                    bannerFileOriNames += uploadF.getOriginalFilename() + ",";
                    bannerFileNames += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    bannerFileOriNames += uploadF.getOriginalFilename();
                    bannerFileNames += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }
        csMerchant.setCarouselUrlValue(bannerFileNames);
        csMerchant.setCarouselUrlName(bannerFileOriNames);

        boolean flag = csMerchantService.saveCsMerchantAOffice(csMerchant);
        return ApiResult.result(flag);
    }

    /**
     * 添加参数配置表
     */
    @PostMapping("/updateCsMerchant")
    @RequiresPermissions("cs:merchant:add")
    @ApiOperation(value = "添加CsMerchant对象", notes = "添加商店表", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMerchant(HttpServletRequest request, @ModelAttribute CsMerchant csMerchant) throws Exception {

        //上传目录 --
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        // 先删除之前的图片
        String[] logoUploadFileDel = csMerchant.getLogoUploadFileDel();
        if(ArrayUtils.isNotEmpty(logoUploadFileDel)) {
            for (int i = 0; i < logoUploadFileDel.length; i++) {
                UploadUtil.deleteQuietly(uploadPath, logoUploadFileDel[i]);
            }
        }

        String[] bannerUploadFileDel = csMerchant.getBannerUploadFileDel();
        if(ArrayUtils.isNotEmpty(bannerUploadFileDel)) {
            for (int i = 0; i < bannerUploadFileDel.length; i++) {
                UploadUtil.deleteQuietly(uploadPath, bannerUploadFileDel[i]);
            }
        }

        //logo 新增文件
        MultipartFile[] logoUploadfiles=csMerchant.getLogoUploadFileAdd();
        String logoFileNames="";
        String logoFileOriNames="";
        if(ArrayUtils.isNotEmpty(logoUploadfiles)) {
            for (int i = 0; i < logoUploadfiles.length; i++) {
                MultipartFile uploadF = logoUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != logoUploadfiles.length - 1) {
                    logoFileOriNames += uploadF.getOriginalFilename() + ",";
                    logoFileNames += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    logoFileOriNames += uploadF.getOriginalFilename();
                    logoFileNames += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
            csMerchant.setLogoUrlValue(logoFileNames);
            csMerchant.setLogoUrlName(logoFileOriNames);
        }

        //banner
        MultipartFile[] bannerUploadfiles=csMerchant.getBannerUploadFileAdd();
        String bannerFileNames=csMerchant.getCarouselUrlValue();
        String bannerFileOriNames=csMerchant.getCarouselUrlName();
        if(ArrayUtils.isNotEmpty(bannerUploadfiles)) {
            for (int i = 0; i < bannerUploadfiles.length; i++) {
                MultipartFile uploadF = bannerUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (StringUtils.isNotEmpty(bannerFileOriNames)) {
                    bannerFileOriNames += "," + uploadF.getOriginalFilename() ;
                    bannerFileNames += "," + whyySystemProperties.getConfigAccessUrl() + fileName;
                } else {
                    bannerFileOriNames += uploadF.getOriginalFilename();
                    bannerFileNames += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
            csMerchant.setCarouselUrlValue(bannerFileNames);
            csMerchant.setCarouselUrlName(bannerFileOriNames);
        }

        boolean flag = csMerchantService.updateCsMerchantAOffice(csMerchant);
        return ApiResult.result(flag);
    }

    /**
     * 修改status状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:merchant:update")
    @ApiOperation(value = "修改status状态", notes = "修改status状态", response = Boolean.class)
    public ApiResult<Boolean> updateStatusById(@Valid @RequestBody CsMerchantQueryParam csMerchantQueryParam){
        return ApiResult.ok(csMerchantService.updateStatusById(csMerchantQueryParam));
    }
}

