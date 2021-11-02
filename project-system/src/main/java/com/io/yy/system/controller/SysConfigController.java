package com.io.yy.system.controller;

import com.io.yy.constant.CommonRedisKey;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.system.entity.SysConfig;
import com.io.yy.system.service.SysConfigService;
import com.io.yy.system.param.SysConfigQueryParam;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.system.vo.SysConfigQueryVo;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.DictUtil;
import com.io.yy.util.UploadUtil;
import com.io.yy.util.codec.EncodeUtils;
import com.io.yy.util.lang.DateUtils;
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
import javax.xml.stream.events.EndDocument;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.io.yy.common.vo.Paging;
import com.io.yy.common.param.IdParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * 参数配置表 前端控制器
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-02
 */
@Slf4j
@RestController
@RequestMapping("/sysConfig")
@Api("参数配置表 API")
public class SysConfigController extends BaseController {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private WhyySystemProperties whyySystemProperties;

    /**
     * 添加参数配置表
     */
    @PostMapping("/addConfigPic")
    @RequiresPermissions("sys:config:add")
    @ApiOperation(value = "添加SysConfig对象", notes = "添加参数配置表", response = ApiResult.class)
    public ApiResult<Boolean> addSysConfig(HttpServletRequest request,@ModelAttribute SysConfig sysConfig) throws Exception {
//        System.out.println(sysConfig.getConfigName());
        //图片对象，需要进行转换
        if(sysConfig.getConfigType().equals("1")){

            //上传目录
            String uploadPath = whyySystemProperties.getConfigUploadPath();
            MultipartFile[] uploadfiles=sysConfig.getUploadFile();
            String fileNames="";
            String fileOriNames="";
            for(int i=0;i<uploadfiles.length;i++){
                MultipartFile uploadF=uploadfiles[i];
                String fileName=UploadUtil.upload(uploadPath,uploadF);
                if(i!=uploadfiles.length-1){
                    fileOriNames+=uploadF.getOriginalFilename()+",";
                    fileNames+=whyySystemProperties.getConfigAccessUrl()+ fileName+",";
                }else{
                    fileOriNames+=uploadF.getOriginalFilename();
                    fileNames+=whyySystemProperties.getConfigAccessUrl() + fileName;
                }
            }
            System.out.println(fileNames);
            System.out.println(fileOriNames);
            sysConfig.setConfigPicValue(fileNames);
            sysConfig.setConfigPicName(fileOriNames);
        }
        boolean flag = sysConfigService.saveSysConfig(sysConfig);
        return ApiResult.result(flag);
    }

    /**
     * 添加参数配置表
     */
    @PostMapping("/updateConfigPic")
    @RequiresPermissions("sys:config:update")
    @ApiOperation(value = "更新picSysConfig对象", notes = "添加参数配置表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysPicConfig(HttpServletRequest request,@ModelAttribute SysConfig sysConfig) throws Exception {
//        System.out.println(sysConfig.getConfigName());
        //图片对象，需要进行转换
        if(sysConfig.getConfigType().equals("1")){

            //上传目录
            String uploadPath = whyySystemProperties.getConfigUploadPath();

            //banner
            MultipartFile[] uploadfiles=sysConfig.getUploadFileAdd();
            String fileNames=sysConfig.getConfigPicValue();
            String fileOriNames=sysConfig.getConfigPicName();
            List<String> fileNameList = Arrays.asList(fileNames.split(","));
            List<String> fileOriNameList = Arrays.asList(fileOriNames.split(","));

            String[] uploadFileDel = sysConfig.getUploadFileDel();
            if(ArrayUtils.isNotEmpty(uploadFileDel)) {
                for (int i = 0; i < uploadFileDel.length; i++) {
                    UploadUtil.deleteQuietly(uploadPath, uploadFileDel[i]);
                    String temp =uploadFileDel[i];
                    fileNameList.removeIf(str->str.contains(temp));
                    fileOriNameList.removeIf(str->str.equals(temp));
                }
            }

            if(ArrayUtils.isNotEmpty(uploadfiles)) {
                for (int i = 0; i < uploadfiles.length; i++) {
                    MultipartFile uploadF = uploadfiles[i];
                    String fileName = UploadUtil.upload(uploadPath, uploadF);
                    if (StringUtils.isNotBlank(fileOriNames)) {
//                    bannerFileOriNames += "," + uploadF.getOriginalFilename() ;
                        fileOriNames += "," + fileName;
                        fileNames += "," + whyySystemProperties.getConfigAccessUrl() + fileName;
                    } else {
//                    bannerFileOriNames += uploadF.getOriginalFilename();
                        fileOriNames += fileName;
                        fileNames += whyySystemProperties.getConfigAccessUrl() + fileName;
                    }
                }
            }

//            MultipartFile[] uploadfiles=sysConfig.getUploadFile();
//            String fileNames="";
//            String fileOriNames="";
//            for(int i=0;i<uploadfiles.length;i++){
//                MultipartFile uploadF=uploadfiles[i];
//                String fileName=UploadUtil.upload(uploadPath,uploadF);
//                if(i!=uploadfiles.length-1){
//                    fileOriNames+=uploadF.getOriginalFilename()+",";
//                    fileNames+=whyySystemProperties.getConfigAccessUrl()+ fileName+",";
//                }else{
//                    fileOriNames+=uploadF.getOriginalFilename();
//                    fileNames+=whyySystemProperties.getConfigAccessUrl() + fileName;
//                }
//            }
//            System.out.println(fileNames);
//            System.out.println(fileOriNames);
            sysConfig.setConfigPicValue(fileNames);
            sysConfig.setConfigPicName(fileOriNames);
        }
        boolean flag = sysConfigService.updateSysConfig(sysConfig);
        return ApiResult.result(flag);
    }


    /**
     * 添加参数配置表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:config:add")
    @ApiOperation(value = "添加SysConfig对象", notes = "添加参数配置表", response = ApiResult.class)
    public ApiResult<Boolean> addSysConfig(@Valid @RequestBody SysConfig sysConfig) throws Exception {
        sysConfig.setConfigContentValue(EncodeUtils.decodeHtml(sysConfig.getConfigContentValue()));
        boolean flag = sysConfigService.saveSysConfig(sysConfig);
        return ApiResult.result(flag);
    }

    /**
     * 修改参数配置表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:config:update")
    @ApiOperation(value = "修改SysConfig对象", notes = "修改参数配置表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysConfig(@Valid @RequestBody SysConfig sysConfig) throws Exception {
        boolean flag = sysConfigService.updateSysConfig(sysConfig);
        return ApiResult.result(flag);
    }

    /**
     * 删除参数配置表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:config:delete")
    @ApiOperation(value = "删除SysConfig对象", notes = "删除参数配置表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysConfig(@PathVariable("id") String id) throws Exception {
        boolean flag = sysConfigService.deleteSysConfig(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除参数配置表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    @ApiOperation(value = "批量删除SysConfig对象", notes = "批量删除参数配置表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysConfig(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysConfigService.deleteSysConfigs(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取参数配置表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    @ApiOperation(value = "获取SysConfig对象详情", notes = "查看参数配置表", response = SysConfigQueryVo.class)
    public ApiResult<SysConfigQueryVo> getSysConfig(@PathVariable("id") String id) throws Exception {
        SysConfigQueryVo sysConfigQueryVo = sysConfigService.getSysConfigById(id);
        return ApiResult.ok(sysConfigQueryVo);
    }

    /**
     * 参数配置表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:config:page")
    @ApiOperation(value = "获取SysConfig分页列表", notes = "参数配置表分页列表", response = SysConfigQueryVo.class)
    public ApiResult<Paging<SysConfigQueryVo>> getSysConfigPageList(@Valid @RequestBody SysConfigQueryParam sysConfigQueryParam) throws Exception {
        Paging<SysConfigQueryVo> paging = sysConfigService.getSysConfigPageList(sysConfigQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 获取缓存的参数配置数据
     */
    @GetMapping("/getAllConfigDataCache")
    @ApiOperation(value = "获取缓存的参数配置数据", notes = "获取缓存的参数配置数据", response = List.class)
    public ApiResult<List> getAllDictDataCache() throws Exception {
        // 在登陆判断是否有缓存参数配置，无则缓存（系统启动时加载参数配置缓存，在这边加只是为了以防没有加载）
        List<SysConfigDataRedisVo> sysConfigDataList = ConfigDataUtil.getAllSysConfigData();
        if(sysConfigDataList==null||sysConfigDataList.size()==0){
            sysConfigDataList=sysConfigService.getAllSysConfigData();
            ConfigDataUtil.cacheSysDictDataInfo(sysConfigDataList);
        }
        return ApiResult.ok(sysConfigDataList);
    }
}

