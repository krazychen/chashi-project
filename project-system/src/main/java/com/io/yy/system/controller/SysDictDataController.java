package com.io.yy.system.controller;

import com.io.yy.common.api.ApiResult;
import com.io.yy.common.controller.BaseController;
import com.io.yy.common.vo.Paging;
import com.io.yy.constant.CommonRedisKey;
import com.io.yy.system.entity.SysDictData;
import com.io.yy.system.param.SysDictDataQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.service.SysDictDataService;
import com.io.yy.system.vo.SysDictDataQueryVo;
import com.io.yy.system.vo.SysDictDataRemarksVo;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 字典数据表 前端控制器
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Slf4j
@RestController
@RequestMapping("/sysDictData")
@Api("字典数据表 API")
public class SysDictDataController extends BaseController {

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    /**
     * 添加字典数据表
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:dict:data:add")
    @ApiOperation(value = "添加SysDictData对象", notes = "添加字典数据表", response = ApiResult.class)
    public ApiResult<Boolean> addSysDictData(@Valid @RequestBody SysDictData sysDictData) throws Exception {
        boolean flag = sysDictDataService.saveSysDictData(sysDictData);
        return ApiResult.result(flag);
    }

    /**
     * 修改字典数据表
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:dict:data:update")
    @ApiOperation(value = "修改SysDictData对象", notes = "修改字典数据表", response = ApiResult.class)
    public ApiResult<Boolean> updateSysDictData(@Valid @RequestBody SysDictData sysDictData) throws Exception {
        boolean flag = sysDictDataService.updateSysDictData(sysDictData);
        return ApiResult.result(flag);
    }

    /**
     * 删除字典数据表
     */
    @PostMapping("/delete/{id}")
    @RequiresPermissions("sys:dict:data:delete")
    @ApiOperation(value = "删除SysDictData对象", notes = "删除字典数据表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysDictData(@PathVariable("id") String id) throws Exception {
        boolean flag = sysDictDataService.deleteSysDictData(id);
        return ApiResult.result(flag);
    }

    /**
     * 批量删除字典数据表
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:dict:data:delete")
    @ApiOperation(value = "批量删除SysDictData对象", notes = "批量删除字典数据表", response = ApiResult.class)
    public ApiResult<Boolean> deleteSysDictData(@Valid @RequestBody List<String> idList) throws Exception {
        boolean flag = sysDictDataService.deleteSysDictDatas(idList);
        return ApiResult.result(flag);
    }

    /**
     * 获取字典数据表
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("sys:dict:data:info")
    @ApiOperation(value = "获取SysDictData对象详情", notes = "查看字典数据表", response = SysDictDataQueryVo.class)
    public ApiResult<SysDictDataQueryVo> getSysDictData(@PathVariable("id") String id) throws Exception {
        SysDictDataQueryVo sysDictDataQueryVo = sysDictDataService.getSysDictDataById(id);
        return ApiResult.ok(sysDictDataQueryVo);
    }

    /**
     * 字典数据表分页列表
     */
    @PostMapping("/getPageList")
    @RequiresPermissions("sys:dict:data:page")
    @ApiOperation(value = "获取SysDictData分页列表", notes = "字典数据表分页列表", response = SysDictDataQueryVo.class)
    public ApiResult<Paging<SysDictDataQueryVo>> getSysDictDataPageList(@Valid @RequestBody SysDictDataQueryParam sysDictDataQueryParam) throws Exception {
        Paging<SysDictDataQueryVo> paging = sysDictDataService.getSysDictDataPageList(sysDictDataQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 菜单精简树结构
     */
    @PostMapping("/getSysDictDataSimplifyPageList")
    @RequiresPermissions("sys:dict:data:simplify")
    @ApiOperation(value = "获取菜单精简树结构", notes = "菜单精简树结构", response = SysMenuQueryVo.class)
    public ApiResult<List<SysMenuTreeQueryVo>> getSysMenuSimplifyPageList(@Valid @RequestBody SysDictDataQueryParam sysDictDataQueryParam) throws Exception {
        List<SysMenuTreeQueryVo> list = sysDictDataService.getSysMenuSimplifyPageList(sysDictDataQueryParam);
        return ApiResult.ok(list);
    }

    /**
     * 修改status状态
     */
    @PostMapping("/updateStatus")
    @RequiresPermissions("sys:dict:data:status")
    @ApiOperation(value = "修改status状态", notes = "修改status状态", response = Boolean.class)
    public ApiResult<Boolean> updateStatusById(@Valid @RequestBody SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam){
        return ApiResult.ok(sysDictDataService.updateStatusById(sysDictTypeStatusQueryParam));
    }

    /**
     * 获取缓存的字典数据表
     */
    @GetMapping("/getAllDictDataCache")
    @ApiOperation(value = "获取缓存的字典数据表", notes = "获取缓存的字典数据表", response = Map.class)
    public ApiResult<Map> getAllDictDataCache() throws Exception {
        // 在登陆判断是否有缓存数据字典，无则缓存（系统启动时加载数据字典缓存，在这边加只是为了以防没有加载）
        Map dictDataCacheMap = (Map) redisTemplate.opsForHash().entries(CommonRedisKey.DICT_DATA_KEY);
        if(dictDataCacheMap==null||dictDataCacheMap.size()==0){
            dictDataCacheMap=sysDictDataService.getAllSysDictData();
            sysDictDataRedisService.cacheSysDictDataInfo(dictDataCacheMap);
        }
        return ApiResult.ok(dictDataCacheMap);
    }

    @PostMapping("/getDictDataByType/{type}")
    @RequiresPermissions("sys:dict:data:info")
    @ApiOperation(value = "根据字典类型获取字典数据", notes = "根据字典类型获取字典数据", response = ApiResult.class)
    public ApiResult<List<SysDictDataRemarksVo>> getDictDataByType(@PathVariable("type") String type){
        return ApiResult.ok(sysDictDataService.getDictDataByType(type));
    }
}

