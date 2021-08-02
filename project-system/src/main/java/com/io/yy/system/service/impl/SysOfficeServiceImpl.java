package com.io.yy.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysOffice;
import com.io.yy.system.mapper.SysOfficeMapper;
import com.io.yy.system.param.SysOfficeQueryParam;
import com.io.yy.system.service.SysOfficeService;
import com.io.yy.system.vo.SysOfficeQueryVo;
import com.io.yy.system.vo.SysOfficeTreeQueryVo;
import com.io.yy.system.vo.SysOfficeUserTreeQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/**
 * <pre>
 * 组织机构表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-23
 */
@Slf4j
@Service
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOfficeMapper, SysOffice> implements SysOfficeService {

    @Autowired
    private SysOfficeMapper sysOfficeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysOffice(SysOffice sysOffice) throws Exception {
        Boolean flag = false;
        SysOffice office = getById(sysOffice.getViewCode());
        if(office != null){
            throw new BusinessException("机构代码已存在！");
        }
        String parentName = "首级节点";
        String parentCode = "00000000";
        int parentSort = 1;
        SysOfficeQueryVo parent = new SysOfficeQueryVo();
        parent.setOfficeName(parentName);
        parent.setOfficeCode(parentCode);
        parent.setTreeSort(new BigDecimal(parentSort));
        //根据父级id获取父级的信息
        if (StrUtil.isNotBlank(sysOffice.getParentCode()) && !sysOffice.getParentCode().equals(parentCode)) {
           parent = sysOfficeMapper.getSysOfficeById(sysOffice.getParentCode());
        }
        if (ObjectUtil.isNull(parent)) {
            throw new BusinessException("父级菜单不存在");
        }
        //tree_names tree_level parent_codes tree_sorts tree_leaf不能为空
        //把所有的父级id以“,”隔开
        String parentCodes = parent.getParentCodes() == null ? "" : parent.getParentCodes();
        parentCodes = ((parentCodes + ",").equals(",") ? "" : (parentCodes + ",")) + parent.getOfficeCode();
        String treeNames = parent.getTreeNames() == null ? "" : parent.getTreeNames();
        treeNames = ((treeNames + ",").equals(",") ? "" : (treeNames + ",")) + parent.getOfficeName();
        String treeSorts = parent.getTreeSorts() == null ? "" : parent.getTreeSorts();
        treeSorts = ((treeSorts + ",").equals(",") ? "" : (treeSorts + ",")) + parent.getTreeSort();
        sysOffice.setOfficeCode(sysOffice.getViewCode());
        sysOffice.setParentCodes(parentCodes);
        sysOffice.setTreeSorts(treeSorts);
        sysOffice.setTreeNames(treeNames);
        sysOffice.setTreeLeaf("1");
        sysOffice.setParentCode(parent.getOfficeCode());
        int treeLevel = parentCodes.split(",").length + 1;
        sysOffice.setTreeLevel(new BigDecimal(treeLevel));
        sysOffice.setDeleted(0);
        sysOffice.setCorpCode(sysOffice.getOfficeCode());
        sysOffice.setCorpName(sysOffice.getOfficeName());
        //添加机构
        super.save(sysOffice);
        //变更上级菜单的末级节点状态
        if (StrUtil.isNotBlank(parent.getTreeLeaf()) && parent.getTreeLeaf().equals("1")) {
            sysOfficeMapper.updateTreeLeafById(parent.getOfficeCode(), "2");
        }
        flag = true;
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysOffice(SysOffice sysOffice) throws Exception {
        return super.updateById(sysOffice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysOffice(String id) throws Exception {
        List<SysOfficeQueryVo> voList = sysOfficeMapper.findChildNode(id);
        if(voList.size() > 0){
            throw new BusinessException("有子节点不能删除！");
        }
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysOffices(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysOfficeQueryVo getSysOfficeById(String id) throws Exception {
        return sysOfficeMapper.getSysOfficeById(id);
    }

    @Override
    public Paging<SysOfficeQueryVo> getSysOfficePageList(SysOfficeQueryParam sysOfficeQueryParam) throws Exception {
        Page page = setPageParam(sysOfficeQueryParam, OrderItem.desc("update_time"));
        IPage<SysOfficeQueryVo> iPage = sysOfficeMapper.getSysOfficePageList(page, sysOfficeQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateBystatus(String id) {
        SysOffice sysOffice = getById(id);
        if(sysOffice.getStatus().equals("1")){
            sysOffice.setStatus("0");//停用设置为0
        }else if(sysOffice.getStatus().equals("0")){
            sysOffice.setStatus("1");//正常设置为0
        }
        return super.updateById(sysOffice);
    }

    @Override
    public List<SysOfficeTreeQueryVo> getParentCodeTree(SysOfficeQueryParam sysOfficeQueryParam) {
        List<SysOfficeTreeQueryVo> list = sysOfficeMapper.getParentCodeTree(sysOfficeQueryParam);
        return list;
    }

    @Override
    public boolean isEnableSysOffice(String officeId) {
       SysOffice sysOffice = new SysOffice()
               .setOfficeCode(String.valueOf(officeId))
               .setDeleted(0)
               .setStatus("1");
        int count = sysOfficeMapper.selectCount(new QueryWrapper<>(sysOffice));
        return count > 0;
    }

    @Override
    public List<SysOfficeUserTreeQueryVo> getParentCodeUserTree(SysOfficeQueryParam sysOfficeQueryParam) throws Exception {
        List<SysOfficeUserTreeQueryVo> list = sysOfficeMapper.getParentCodeUserTree(sysOfficeQueryParam);
        return list;
    }

}
