package com.io.yy.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysArea;
import com.io.yy.system.mapper.SysAreaMapper;
import com.io.yy.system.param.SysAreaParam;
import com.io.yy.system.param.SysAreaQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.service.SysAreaService;
import com.io.yy.system.vo.SysAreaLazyQueryVo;
import com.io.yy.system.vo.SysAreaQueryVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import com.io.yy.util.excel.ExcelImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * 行政区划 服务实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-04
 */
@Slf4j
@Service
public class SysAreaServiceImpl extends BaseServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysArea(SysArea sysArea) throws Exception {
        Boolean judge = false;
        if (ObjectUtil.isNull(sysArea)) {
            throw new BusinessException("菜单不可以为空");
        }
        String parentName = "首级节点";
        String parentCode = "00000000";
        int parentSort = 1;
        SysAreaQueryVo parent = new SysAreaQueryVo();
        parent.setAreaName(parentName);
        parent.setAreaCode(parentCode);
        parent.setTreeSort(new BigDecimal(parentSort));
        //根据父级id获取父级的信息
        if (StrUtil.isNotBlank(sysArea.getParentCode()) && !sysArea.getParentCode().equals(parentCode)) {
            parent = sysAreaMapper.getSysAreaById(sysArea.getParentCode());
        }
        if (ObjectUtil.isNull(parent)) {
            throw new BusinessException("父级菜单不存在");
        }
        //把所有的父级id以“,”隔开
        String parentCodes = parent.getParentCodes() == null ? "" : parent.getParentCodes();
        parentCodes = ((parentCodes + ",").equals(",") ? "" : (parentCodes + ",")) + parent.getAreaCode();
        String treeNames = parent.getTreeNames() == null ? "" : parent.getTreeNames();
        treeNames = ((treeNames + ",").equals(",") ? "" : (treeNames + ",")) + parent.getAreaName();
        String treeSorts = parent.getTreeSorts() == null ? "" : parent.getTreeSorts();
        treeSorts = ((treeSorts + ",").equals(",") ? "" : (treeSorts + ",")) + parent.getTreeSort();
        sysArea.setParentCodes(parentCodes)
                .setTreeSorts(treeSorts)
                .setTreeNames(treeNames)
                .setTreeLeaf("1")
                .setParentCode(parent.getAreaCode())
                .setVersion(0);
        int treeLevel = parentCodes.split(",").length + 1;
        sysArea.setTreeLevel(new BigDecimal(treeLevel));
        sysArea.setDeleted(0);
        //添加菜单
        super.save(sysArea);
        //变更上级菜单的末级节点状态
        if (StrUtil.isNotBlank(parent.getTreeLeaf()) && parent.getTreeLeaf().equals("1")) {
            sysAreaMapper.updateTreeLeafById(parent.getAreaCode(), "2");
        }
        judge = true;
        return judge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysArea(SysArea sysArea) throws Exception {
        return super.updateById(sysArea);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysArea(String id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysAreas(List<String> idList) throws Exception {
        return super.removeByIds(idList);
    }

    @Override
    public SysAreaQueryVo getSysAreaById(String id) throws Exception {
        return sysAreaMapper.getSysAreaById(id);
    }

    @Override
    public Paging<SysAreaQueryVo> getSysAreaPageList(SysAreaQueryParam sysAreaQueryParam) throws Exception {
        Page page = setPageParam(sysAreaQueryParam, OrderItem.desc("create_time"));
        IPage<SysAreaQueryVo> iPage = sysAreaMapper.getSysAreaPageList(page, sysAreaQueryParam);
        return new Paging(iPage);
    }


    @Override
    public Paging<SysAreaLazyQueryVo> getSysAreaPageListLazy(SysAreaQueryParam sysAreaQueryParam) {
        Page page = setPageParam(sysAreaQueryParam, OrderItem.desc("create_time"));
        IPage<SysAreaLazyQueryVo> iPage = sysAreaMapper.getSysAreaPageListLazy(page, sysAreaQueryParam);
        return new Paging(iPage);
    }

    @Override
    public Boolean addAreaToRedis() {
        try {
            List<SysMenuTreeQueryVo> list = (List<SysMenuTreeQueryVo>)redisTemplate.opsForValue().get("reidsSysArea");
            if(CollUtil.isEmpty(list)){
                list = sysAreaMapper.getSysAreaSimplifyPageList(new SysAreaQueryParam());
                redisTemplate.opsForValue().set("reidsSysArea", list);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<SysMenuTreeQueryVo> getSysAreaSimplifyPageList(SysAreaQueryParam sysAreaQueryParam) {
        return sysAreaMapper.getSysAreaSimplifyPageList(sysAreaQueryParam);
    }

    @Override
    public List<SysMenuTreeQueryVo> getSysAreaRidesSimplifyPageList() {
        List<SysMenuTreeQueryVo> list = (List<SysMenuTreeQueryVo>)redisTemplate.opsForValue().get("reidsSysArea");
        if(CollUtil.isEmpty(list)){
            list = sysAreaMapper.getSysAreaSimplifyPageList(new SysAreaQueryParam());
            redisTemplate.opsForValue().set("reidsSysArea", list);
        }
        return list;
    }


    /**
     * 修改行政区域的状态
     *
     * @param sysDictTypeStatusQueryParam
     * @return
     */
    @Override
    public Boolean updateStatusById(SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam) {
        return sysAreaMapper.updateStatusById(sysDictTypeStatusQueryParam) > 0;
    }

    @Override
    public Boolean importData(MultipartFile file) {
        Boolean judge = false;
        try {
            ExcelImport excelImport = new ExcelImport(file, 1, 0);
            List<SysAreaParam> list = excelImport.getDataList(SysAreaParam.class);
            List<SysArea> listAreaAll = new ArrayList<>();
            for (SysAreaParam sysAreaParam : list) {
                if (StrUtil.isBlank(sysAreaParam.getProvinceCode())) {
                    throw new BusinessException("未填写省份代码");
                }
                if (StrUtil.isBlank(sysAreaParam.getProvinceName())) {
                    throw new BusinessException("未填写省份名称");
                }
                if (StrUtil.isBlank(sysAreaParam.getCityCode())) {
                    throw new BusinessException("未填写城市代码");
                }
                if (StrUtil.isBlank(sysAreaParam.getCityName())) {
                    throw new BusinessException("未填写城市名称");
                }
                if (StrUtil.isBlank(sysAreaParam.getDistrictCode())) {
                    throw new BusinessException("未填写区县代码");
                }
                if (StrUtil.isBlank(sysAreaParam.getDistrictName())) {
                    throw new BusinessException("未填写区县名称");
                }
                List<SysArea> listArea = new ArrayList<>();
                listArea.add(new SysArea());
                listArea.add(new SysArea());
                listArea.add(new SysArea());
                for (SysArea sysArea : listArea) {
                    sysArea.setVersion(0)
                            .setStatus("0")
                            .setDeleted(0);
                }
                System.out.println(sysAreaParam.getTreeSort());
                int sort = Integer.parseInt(sysAreaParam.getTreeSort() == null ? "100" : sysAreaParam.getTreeSort());
                listArea.get(0).setParentCodes("00000000")
                        .setTreeSorts("" + sysAreaParam.getTreeSort())
                        .setTreeNames("首级节点")
                        .setTreeLeaf("1")
                        .setParentCode("00000000")
                        .setAreaName(sysAreaParam.getProvinceName())
                        .setAreaCode(sysAreaParam.getProvinceCode())
                        .setTreeSort(new BigDecimal(sort))
                        .setTreeLevel(new BigDecimal(2))
                        .setAreaType("province")
                        .setStatus("1");
                listArea.get(1).setParentCodes(listArea.get(0).getParentCodes() + "," + listArea.get(0).getAreaCode())
                        .setTreeSorts(listArea.get(0).getTreeSorts() + "," + listArea.get(0).getTreeSort())
                        .setTreeNames(listArea.get(0).getTreeNames() + "," + listArea.get(0).getAreaName())
                        .setTreeLeaf("1")
                        .setParentCode(listArea.get(0).getAreaCode())
                        .setAreaName(sysAreaParam.getCityName())
                        .setAreaCode(sysAreaParam.getCityCode())
                        .setTreeSort(new BigDecimal(sort))
                        .setTreeLevel(new BigDecimal(3))
                        .setAreaType("city")
                        .setStatus("1");
                listArea.get(2).setParentCodes(listArea.get(1).getParentCodes() + "," + listArea.get(1).getAreaCode())
                        .setTreeSorts(listArea.get(1).getTreeSorts() + "," + listArea.get(1).getTreeSort())
                        .setTreeNames(listArea.get(1).getTreeNames() + "," + listArea.get(1).getAreaName())
                        .setTreeLeaf("0")
                        .setParentCode(listArea.get(1).getAreaCode())
                        .setAreaName(sysAreaParam.getDistrictName())
                        .setAreaCode(sysAreaParam.getDistrictCode())
                        .setTreeSort(new BigDecimal(sort))
                        .setTreeLevel(new BigDecimal(4))
                        .setAreaType("region")
                        .setStatus("1");
                listAreaAll.addAll(listArea);
            }
            super.saveBatch(listAreaAll);
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return judge;

    }


    public List<SysAreaQueryVo> getAreaByAreaType(String areaType,String parentCode){
        List<SysAreaQueryVo> list = sysAreaMapper.getAreaByAreaType(areaType,parentCode);
        return list;
    }
}
