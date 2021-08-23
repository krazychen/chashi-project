package com.io.yy.merchant.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.marketing.service.CsMemberCardService;
import com.io.yy.marketing.vo.CsMemberCardQueryVo;
import com.io.yy.marketing.vo.CsMembercardOrderQueryVo;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.entity.CsTearoom;
import com.io.yy.merchant.service.CsTearoomService;
import com.io.yy.merchant.param.CsTearoomQueryParam;
import com.io.yy.merchant.vo.CsTearoomQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.util.UploadUtil;
import com.io.yy.util.codec.EncodeUtils;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.wxops.service.WxUserService;
import com.io.yy.wxops.vo.WxUserQueryVo;
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
 * 茶室管理 前端控制器
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Slf4j
@RestController
@RequestMapping("/csTearoom")
@Api("茶室管理 API")
public class CsTearoomController extends BaseController {

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    @Autowired
    private CsTearoomService csTearoomService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private CsMemberCardService csMemberCardService;
    /**
     * 添加茶室管理
     */
    @PostMapping("/add")
    @RequiresPermissions("cs:tearoom:add")
    @ApiOperation(value = "添加CsTearoom对象", notes = "添加茶室管理", response = ApiResult.class)
    public ApiResult<Boolean> addCsTearoom(@Valid @RequestBody CsTearoom csTearoom) throws Exception {
        boolean flag = csTearoomService.saveCsTearoom(csTearoom);
        return ApiResult.result(flag);
    }

    /**
     * 修改茶室管理
     */
    @PostMapping("/update")
    @RequiresPermissions("cs:tearoom:update")
    @ApiOperation(value = "修改CsTearoom对象", notes = "修改茶室管理", response = ApiResult.class)
    public ApiResult<Boolean> updateCsTearoom(@Valid @RequestBody CsTearoom csTearoom) throws Exception {
        boolean flag = csTearoomService.updateCsTearoom(csTearoom);
        return ApiResult.result(flag);
    }

    /**
     * 删除茶室管理
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("cs:tearoom:delete")
    @ApiOperation(value = "删除CsTearoom对象", notes = "删除茶室管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsTearoom(@PathVariable("id") Long id) throws Exception {
        boolean flag = csTearoomService.deleteCsTearoom(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除茶室管理
     */
    @PostMapping("/delete")
    @RequiresPermissions("cs:tearoom:delete")
    @ApiOperation(value = "批量删除CsTearoom对象", notes = "批量删除茶室管理", response = ApiResult.class)
    public ApiResult<Boolean> deleteCsTearoom(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = csTearoomService.deleteCsTearooms(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取茶室管理
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("cs:tearoom:info")
    @ApiOperation(value = "获取CsTearoom对象详情", notes = "查看茶室管理", response = CsTearoomQueryVo.class)
    public ApiResult<CsTearoomQueryVo> getCsTearoom(@PathVariable("id") Long id) throws Exception {
        CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(id);
        return ApiResult.ok(csTearoomQueryVo);
    }

    /**
     * 茶室管理分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("cs:tearoom:page")
    @ApiOperation(value = "获取CsTearoom分页列表", notes = "茶室管理分页列表", response = CsTearoomQueryVo.class)
    public ApiResult<Paging<CsTearoomQueryVo>> getCsTearoomPageList(@Valid @RequestBody CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Paging<CsTearoomQueryVo> paging = csTearoomService.getCsTearoomPageList(csTearoomQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 茶室管理分页列表
     */
    @PostMapping("/getPageObjList")
    @RequiresPermissions("cs:tearoom:page")
    @ApiOperation(value = "获取CsTearoomObj分页列表", notes = "茶室管理分页列表", response = CsTearoomQueryVo.class)
    public ApiResult<Paging<CsTearoomQueryVo>> getCsTearoomObjPageList(@Valid @RequestBody CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Paging<CsTearoomQueryVo> paging = csTearoomService.getCsTearoomObjPageList(csTearoomQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 茶室管理修改状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("cs:tearoom:update")
    @ApiOperation(value = "修改CsTearoom状态", notes = "茶室管理修改状态", response = ApiResult.class)
    public ApiResult<Boolean> updateStatus(@Valid @RequestBody CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        return ApiResult.ok(csTearoomService.updateStatus(csTearoomQueryParam));
    }

    /**
     * 添加茶室表
     */
    @PostMapping("/addRoom")
    @RequiresPermissions("cs:tearoom:add")
    @ApiOperation(value = "修改CsTearoom状态", notes = "添加茶室表", response = ApiResult.class)
    public ApiResult<Boolean> addCsTearoom(HttpServletRequest request, @ModelAttribute CsTearoom csTearoom) throws Exception {
        //图片对象，需要进行转换

        //上传目录
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        //logo
        MultipartFile[] logoUploadfiles=csTearoom.getLogoUploadFile();
        String logoFileUrls="";
        String logoFileNames="";
        if(ArrayUtils.isNotEmpty(logoUploadfiles)) {
            for (int i = 0; i < logoUploadfiles.length; i++) {
                MultipartFile uploadF = logoUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != logoUploadfiles.length - 1) {
                    logoFileNames += uploadF.getOriginalFilename() + ",";
                    logoFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    logoFileNames += uploadF.getOriginalFilename();
                    logoFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }
        csTearoom.setRoomLogoUrl(logoFileUrls);
        csTearoom.setRoomLogoName(logoFileNames);

        //banner
        MultipartFile[] bannerUploadfiles=csTearoom.getBannerUploadFile();
        String bannerFileUrls="";
        String bannerFileNames="";
        if(ArrayUtils.isNotEmpty(bannerUploadfiles)) {
            for (int i = 0; i < bannerUploadfiles.length; i++) {
                MultipartFile uploadF = bannerUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != bannerUploadfiles.length - 1) {
                    bannerFileUrls += uploadF.getOriginalFilename() + ",";
                    bannerFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    bannerFileUrls += uploadF.getOriginalFilename();
                    bannerFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }
        csTearoom.setRoomBannerUrl(bannerFileUrls);
        csTearoom.setRoomBannerName(bannerFileNames);

        boolean flag = csTearoomService.saveCsTearoom(csTearoom);
        return ApiResult.result(flag);
    }

    /**
     * 更新茶室表
     */
    @PostMapping("/updateRoom")
    @RequiresPermissions("cs:tearoom:add")
    @ApiOperation(value = "添加CsTearoom对象", notes = "更新茶室表", response = ApiResult.class)
    public ApiResult<Boolean> updateCsTearoom(HttpServletRequest request, @ModelAttribute CsTearoom csTearoom) throws Exception {

        //上传目录 --
        String uploadPath = whyySystemProperties.getConfigUploadPath();

        // 先删除之前的图片
        String[] logoUploadFileDel = csTearoom.getLogoUploadFileDel();
        if(ArrayUtils.isNotEmpty(logoUploadFileDel)) {
            for (int i = 0; i < logoUploadFileDel.length; i++) {
                UploadUtil.deleteQuietly(uploadPath, logoUploadFileDel[i]);
            }
        }

        String[] bannerUploadFileDel = csTearoom.getBannerUploadFileDel();
        if(ArrayUtils.isNotEmpty(bannerUploadFileDel)) {
            for (int i = 0; i < bannerUploadFileDel.length; i++) {
                UploadUtil.deleteQuietly(uploadPath, bannerUploadFileDel[i]);
            }
        }

        //logo 新增文件
        MultipartFile[] logoUploadfiles=csTearoom.getLogoUploadFileAdd();
        String logoFileUrls="";
        String logoFileNames="";
        if(ArrayUtils.isNotEmpty(logoUploadfiles)) {
            for (int i = 0; i < logoUploadfiles.length; i++) {
                MultipartFile uploadF = logoUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (i != logoUploadfiles.length - 1) {
                    logoFileNames += uploadF.getOriginalFilename() + ",";
                    logoFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName + ",";
                } else {
                    logoFileNames += uploadF.getOriginalFilename();
                    logoFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }
        csTearoom.setRoomLogoUrl(logoFileUrls);
        csTearoom.setRoomLogoName(logoFileNames);

        //banner
        MultipartFile[] bannerUploadfiles=csTearoom.getBannerUploadFileAdd();
        String bannerFileUrls=StringUtils.isNotEmpty(csTearoom.getRoomBannerUrl())?csTearoom.getRoomBannerUrl():"";
        String bannerFileNames=StringUtils.isNotEmpty(csTearoom.getRoomBannerName())?csTearoom.getRoomBannerName():"";
        if(ArrayUtils.isNotEmpty(bannerUploadfiles)) {
            for (int i = 0; i < bannerUploadfiles.length; i++) {
                MultipartFile uploadF = bannerUploadfiles[i];
                String fileName = UploadUtil.upload(uploadPath, uploadF);
                if (StringUtils.isNotEmpty(bannerFileNames)) {
                    bannerFileNames += "," + uploadF.getOriginalFilename() ;
                    bannerFileUrls += "," + whyySystemProperties.getConfigAccessUrl() + fileName;
                } else {
                    bannerFileNames += uploadF.getOriginalFilename();
                    bannerFileUrls += whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
        }
        csTearoom.setRoomBannerUrl(bannerFileUrls);
        csTearoom.setRoomBannerName(bannerFileNames);

        boolean flag = csTearoomService.updateCsTearoom(csTearoom);
        return ApiResult.result(flag);
    }

    /**
     * 导出功能
     */
    @PostMapping("/exportList")
    @RequiresPermissions("cs:tearoom:page")
    @ApiOperation(value = "导出", notes = "导出")
    public void exportStudentList(@Valid @RequestBody CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        csTearoomService.exportList(csTearoomQueryParam);
    }

    /**
     * 通过商户ID获取茶室管理小程序分页列表
     */
    @PostMapping("/getRoomListForWx")
    @ApiOperation(value = "通过商户ID获取茶室管理小程序分页列表", notes = "通过商户ID获取茶室管理小程序分页列表", response = CsTearoomQueryVo.class)
    public ApiResult<Paging<CsTearoomQueryVo>> getRoomListForWx(@Valid @RequestBody CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        Paging<CsTearoomQueryVo> paging = csTearoomService.getCsTearoomPageListOrderBySort(csTearoomQueryParam);
        //设置茶室的会员价，先获取当前的用户会员
        boolean isNon=false;
        if(StringUtils.isNotEmpty(csTearoomQueryParam.getOpenid())){
            WxUserQueryVo wxUserQueryVo = wxUserService.getWxUserByOpenid(csTearoomQueryParam.getOpenid());
            if(wxUserQueryVo!=null && wxUserQueryVo.getCsMembercardOrderQueryVo()!=null){
                CsMembercardOrderQueryVo csMembercardOrderQueryVo=wxUserQueryVo.getCsMembercardOrderQueryVo();
                double discount=csMembercardOrderQueryVo.getDiscountOff()/10;
                paging.getRecords().stream().forEach(a->a.setMenberAmount(Double.valueOf(discount*a.getHoursAmount())));
            }else{
                isNon=true;
            }
        }else{
            isNon=true;
        }
        //没有登录，也不是会员，则取会员卡的最低价
        if(isNon){
            CsMemberCardQueryVo csMemberCardQueryVo=csMemberCardService.getCsMemberCardOfMin();
            if(csMemberCardQueryVo!=null){
                double discount=csMemberCardQueryVo.getDiscountOff()/10;
                paging.getRecords().stream().forEach(a->a.setMenberAmount(Double.valueOf(discount*a.getHoursAmount())));
            }
        }

        return ApiResult.ok(paging);
    }

    /**
     * 获取wx茶室管理
     */
    @GetMapping("/infoForWx/")
    @ApiOperation(value = "获取wx CsTearoom对象详情", notes = "查看wx茶室管理", response = CsTearoomQueryVo.class)
    public ApiResult<CsTearoomQueryVo> getCsTearoomForWx(@Valid @RequestBody CsTearoomQueryParam csTearoomQueryParam) throws Exception {
        CsTearoomQueryVo csTearoomQueryVo = csTearoomService.getCsTearoomById(csTearoomQueryParam.getId());
        //设置茶室的会员价，先获取当前的用户会员
        boolean isNon=false;
        if(StringUtils.isNotEmpty(csTearoomQueryParam.getOpenid())){
            WxUserQueryVo wxUserQueryVo = wxUserService.getWxUserByOpenid(csTearoomQueryParam.getOpenid());
            if(wxUserQueryVo!=null && wxUserQueryVo.getCsMembercardOrderQueryVo()!=null){
                CsMembercardOrderQueryVo csMembercardOrderQueryVo=wxUserQueryVo.getCsMembercardOrderQueryVo();
                double discount=csMembercardOrderQueryVo.getDiscountOff()/10;
                csTearoomQueryVo.setMenberAmount(Double.valueOf(discount*csTearoomQueryVo.getHoursAmount()));
            }else{
                isNon=true;
            }
        }else{
            isNon=true;
        }
        //没有登录，也不是会员，则取会员卡的最低价
        if(isNon){
            CsMemberCardQueryVo csMemberCardQueryVo=csMemberCardService.getCsMemberCardOfMin();
            if(csMemberCardQueryVo!=null){
                double discount=csMemberCardQueryVo.getDiscountOff()/10;
                csTearoomQueryVo.setMenberAmount(Double.valueOf(discount*csTearoomQueryVo.getHoursAmount()));
            }
        }
        return ApiResult.ok(csTearoomQueryVo);
    }
}

