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
import com.io.yy.system.entity.SysDictData;
import com.io.yy.system.mapper.SysDictDataMapper;
import com.io.yy.system.param.SysDictDataQueryParam;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.service.SysDictDataService;
import com.io.yy.system.vo.SysDictDataQueryVo;
import com.io.yy.system.vo.SysDictDataRedisVo;
import com.io.yy.system.vo.SysDictDataRemarksVo;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * <pre>
 * 字典数据表 服务实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Slf4j
@Service
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysDictData(SysDictData sysDictData) throws Exception {
        sysDictData.setDictCode(UUID.randomUUID().toString());
        Boolean judge = false;
        if (ObjectUtil.isNull(sysDictData)) {
            throw new BusinessException("字典不可以为空");
        }
        //验证标签是否存在
        if (sysDictDataMapper.getSysDictDataCountByDictTypeAndDictLabel(sysDictData.getDictType(), sysDictData.getDictLabel(), null) > 0) {
            throw new BusinessException("标签不能重复");
        }
        //验证类型下的值是否存在
        if (sysDictDataMapper.getSysDictDataCountByDictTypeAndDictValue(sysDictData.getDictType(), sysDictData.getDictValue(), null) > 0) {
            throw new BusinessException("值不能重复");
        }

        String parentName = "首级节点";
        String parentCode = "00000000";
        int parentSort = 1;
        SysDictDataQueryVo parent = new SysDictDataQueryVo();
        parent.setDictLabel(parentName);
        parent.setDictCode(parentCode);
        parent.setTreeSort(new

                BigDecimal(parentSort));
        //根据父级id获取父级的信息
        if (StrUtil.isNotBlank(sysDictData.getParentCode()) && !sysDictData.getParentCode().

                equals(parentCode)) {
            parent = sysDictDataMapper.getSysDictDataById(sysDictData.getParentCode());
        }
        if (ObjectUtil.isNull(parent)) {
            throw new BusinessException("父级菜单不存在");
        }

        //把所有的父级id以“,”隔开
        String parentCodes = parent.getParentCodes() == null ? "" : parent.getParentCodes();
        parentCodes = ((parentCodes + ",").

                equals(",") ? "" : (parentCodes + ",")) + parent.getDictCode();
        String treeNames = parent.getTreeNames() == null ? "" : parent.getTreeNames();
        treeNames = ((treeNames + ",").

                equals(",") ? "" : (treeNames + ",")) + parent.getDictLabel();
        String treeSorts = parent.getTreeSorts() == null ? "" : parent.getTreeSorts();
        treeSorts = ((treeSorts + ",").

                equals(",") ? "" : (treeSorts + ",")) + parent.getTreeSort();
        sysDictData.setParentCodes(parentCodes);
        sysDictData.setTreeSorts(treeSorts);
        sysDictData.setTreeNames(treeNames);
        sysDictData.setTreeLeaf("1");
        sysDictData.setParentCode(parent.getDictCode());
        int treeLevel = parentCodes.split(",").length + 1;
        sysDictData.setTreeLevel(new

                BigDecimal(treeLevel));
        sysDictData.setDeleted(0);
        //添加菜单
        sysDictData.setStatus("1");
        super.save(sysDictData);
        //变更上级菜单的末级节点状态
        if (StrUtil.isNotBlank(parent.getTreeLeaf()) && parent.getTreeLeaf().equals("1")) {
            sysDictDataMapper.updateTreeLeafById(parent.getDictCode(), "2");
        }

        judge = true;
        if(judge){
            //判断redis中是否存在，不存在则从数据库中取
            List<SysDictDataRedisVo> list = sysDictDataMapper.getSysDictDataRedisVoByDictType(sysDictData.getDictType());
            if(CollUtil.isNotEmpty(list)){
                sysDictDataRedisService.refreshSysDictDataInfo(sysDictData.getDictType(), list);
            }
        }
        return judge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysDictData(SysDictData sysDictData) throws Exception {
        //验证标签是否存在
        if (sysDictDataMapper.getSysDictDataCountByDictTypeAndDictLabel(sysDictData.getDictType(), sysDictData.getDictLabel(), sysDictData.getDictCode()) > 0) {
            throw new BusinessException("标签不能重复");
        }
        //验证类型下的值是否存在
        if (sysDictDataMapper.getSysDictDataCountByDictTypeAndDictValue(sysDictData.getDictType(), sysDictData.getDictValue(), sysDictData.getDictCode()) > 0) {
            throw new BusinessException("值不能重复");
        }
        Boolean flag = super.updateById(sysDictData);
        if(flag){
            //判断redis中是否存在，不存在则从数据库中取
            List<SysDictDataRedisVo> list = sysDictDataMapper.getSysDictDataRedisVoByDictType(sysDictData.getDictType());
            if(CollUtil.isNotEmpty(list)){
                sysDictDataRedisService.refreshSysDictDataInfo(sysDictData.getDictType(), list);
            }
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysDictData(String id) throws Exception {
        SysDictData sysDictData = super.getById(id);
        Boolean flag = super.removeById(id);
        if(flag){
            //判断redis中是否存在，不存在则从数据库中取
            List<SysDictDataRedisVo> list = sysDictDataMapper.getSysDictDataRedisVoByDictType(sysDictData.getDictType());
            if(CollUtil.isNotEmpty(list)){
                sysDictDataRedisService.refreshSysDictDataInfo(sysDictData.getDictType(), list);
            }else{
                sysDictDataRedisService.deleteSysDictData(sysDictData.getDictType());
            }
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysDictDatas(List<String> idList) throws Exception {
        SysDictData sysDictData = super.getById(idList.get(0));
        Boolean flag = super.removeByIds(idList);
        if(flag){
            List<SysDictDataRedisVo> list = sysDictDataMapper.getSysDictDataRedisVoByDictType(sysDictData.getDictType());
            if(CollUtil.isNotEmpty(list)){
                sysDictDataRedisService.refreshSysDictDataInfo(sysDictData.getDictType(), list);
            }else{
                sysDictDataRedisService.deleteSysDictData(sysDictData.getDictType());
            }
        }
        return flag;
    }

    @Override
    public SysDictDataQueryVo getSysDictDataById(String id) throws Exception {
        return sysDictDataMapper.getSysDictDataById(id);
    }

    @Override
    public Paging<SysDictDataQueryVo> getSysDictDataPageList(SysDictDataQueryParam sysDictDataQueryParam) throws Exception {
        Page page = setPageParam(sysDictDataQueryParam, OrderItem.desc("create_time"));
        IPage<SysDictDataQueryVo> iPage = sysDictDataMapper.getSysDictDataPageList(page, sysDictDataQueryParam);
        return new Paging(iPage);
    }

    @Override
    public List<SysMenuTreeQueryVo> getSysMenuSimplifyPageList(SysDictDataQueryParam sysDictDataQueryParam) {
        return sysDictDataMapper.getSysDictDataSimplifyPageList(sysDictDataQueryParam);
    }

    @Override
    public Boolean updateStatusById(SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam) {
        return sysDictDataMapper.updateStatusById(sysDictTypeStatusQueryParam) > 0;
    }

    @Override
    public Map<String, List<SysDictDataRedisVo>> getAllSysDictData() {

        Map<String, List<SysDictDataRedisVo>> sysDictDataMap = sysDictDataMapper.getAllSysDictData().stream().collect(Collectors.groupingBy(SysDictDataRedisVo::getDictType));
        return sysDictDataMap;
    }

    @Override
    public List<SysDictDataRemarksVo> getDictDataByType(String type) {
        return sysDictDataMapper.getDictDataByType(type);
    }

}
