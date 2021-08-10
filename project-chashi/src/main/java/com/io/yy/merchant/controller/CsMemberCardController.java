package com.io.yy.merchant.controller;

import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.merchant.entity.CsMemberCard;
import com.io.yy.merchant.service.CsMemberCardService;
import com.io.yy.merchant.param.CsMemberCardQueryParam;
import com.io.yy.merchant.vo.CsMemberCardQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.util.UploadUtil;
import com.io.yy.util.codec.EncodeUtils;
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
 * 会员卡 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-09
 */
@Slf4j
@RestController
@RequestMapping("/csMemberCard")
@Api("会员卡 API")
public class CsMemberCardController extends BaseController {

    @Autowired
    private CsMemberCardService csMemberCardService;

    @Autowired
    private WhyySystemProperties whyySystemProperties;
    /**
     * 添加会员卡
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:member:card:add")
    @ApiOperation(value = "添加CsMemberCard对象", notes = "添加会员卡", response = ApiResult.class)
    public ApiResult<Boolean> addCsMemberCard(HttpServletRequest request, @ModelAttribute CsMemberCard csMemberCard) throws Exception {
        //上传目录
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        //logo
        MultipartFile[] uploadfiles=csMemberCard.getUploadFile();
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

        csMemberCard.setLogoUrl(fileUrls);
        csMemberCard.setLogoName(fileNames);
        csMemberCard.setUsageNotice(EncodeUtils.decodeHtml(csMemberCard.getUsageNotice()));
        csMemberCard.setUseRights(EncodeUtils.decodeHtml(csMemberCard.getUseRights()));

        boolean flag = csMemberCardService.saveCsMemberCard(csMemberCard);
        return ApiResult.result(flag);
    }

    /**
     * 修改会员卡
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:member:card:update")
    @ApiOperation(value = "修改CsMemberCard对象", notes = "修改会员卡", response = ApiResult.class)
    public ApiResult<Boolean> updateCsMemberCard(HttpServletRequest request, @ModelAttribute CsMemberCard csMemberCard) throws Exception {
        //上传目录 --
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        // 先删除之前的图片
        String[] uploadFileDel = csMemberCard.getUploadFileDel();
        if(ArrayUtils.isNotEmpty(uploadFileDel)) {
            for (int i = 0; i < uploadFileDel.length; i++) {
                UploadUtil.deleteQuietly(uploadPath, uploadFileDel[i]);
            }
        }

        //logo 新增文件
        MultipartFile[] uploadfiles=csMemberCard.getUploadFileAdd();
        String fileNames=csMemberCard.getLogoName();
        String fileUrls=csMemberCard.getLogoUrl();
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
        csMemberCard.setLogoUrl(fileUrls);
        csMemberCard.setLogoName(fileNames);
        csMemberCard.setUsageNotice(EncodeUtils.decodeHtml(csMemberCard.getUsageNotice()));
        csMemberCard.setUseRights(EncodeUtils.decodeHtml(csMemberCard.getUseRights()));

        boolean flag = csMemberCardService.updateCsMemberCard(csMemberCard);
        return ApiResult.result(flag);
    }

    /**
     * 删除会员卡
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:member:card:delete")
    @ApiOperation(value = "删除CsMemberCard对象", notes = "删除会员卡", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMemberCard(@PathVariable("id") Long id) throws Exception {
        boolean flag = csMemberCardService.deleteCsMemberCard(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除会员卡
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:member:card:delete")
    @ApiOperation(value = "批量删除CsMemberCard对象", notes = "批量删除会员卡", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsMemberCard(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csMemberCardService.deleteCsMemberCards(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取会员卡
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:member:card:info")
    @ApiOperation(value = "获取CsMemberCard对象详情", notes = "查看会员卡", response = CsMemberCardQueryVo.class)
    public ApiResult<CsMemberCardQueryVo> getCsMemberCard(@PathVariable("id") Long id) throws Exception {
        CsMemberCardQueryVo csMemberCardQueryVo = csMemberCardService.getCsMemberCardById(id);
        return ApiResult.ok(csMemberCardQueryVo);
    }

    /**
     * 会员卡分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:member:card:page")
    @ApiOperation(value = "获取CsMemberCard分页列表", notes = "会员卡分页列表", response = CsMemberCardQueryVo.class)
    public ApiResult<Paging<CsMemberCardQueryVo>> getCsMemberCardPageList(@Valid @RequestBody CsMemberCardQueryParam csMemberCardQueryParam) throws Exception {
        Paging<CsMemberCardQueryVo> paging = csMemberCardService.getCsMemberCardPageList(csMemberCardQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 会员卡修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:member:card:update")
    @ApiOperation(value = "修改CsMemberCard状态", notes = "会员卡修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsMemberCardQueryParam csMemberCardQueryParam) throws Exception {
        return ApiResult.ok(csMemberCardService.updateStatus(csMemberCardQueryParam));
    }


}

