package com.io.yy.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.core.properties.WhyySystemProperties;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.util.JwtUtil;
import com.io.yy.system.entity.SysMenu;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.mapper.SysMenuMapper;
import com.io.yy.system.mapper.SysRoleMapper;
import com.io.yy.system.mapper.SysUserMapper;
import com.io.yy.system.param.SysMenuTypeQueryParam;
import com.io.yy.system.service.SysMenuService;
import com.io.yy.system.param.SysMenuQueryParam;
import com.io.yy.system.vo.SysMenuQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.vo.SysMenuTreeQueryVo;
import com.io.yy.system.vo.SysRoleQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 菜单表 服务实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private WhyySystemProperties whyySystemProperties;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysMenu(SysMenu sysMenu) throws Exception {
        Boolean judge = false;
        if (ObjectUtil.isNull(sysMenu)) {
            throw new BusinessException("菜单不可以为空");
        }
        String parentName = "首级节点";
        String parentCode = "00000000";
        int parentSort = 1;
        SysMenuQueryVo parent = new SysMenuQueryVo();
        parent.setMenuName(parentName);
        parent.setMenuCode(parentCode);
        parent.setTreeSort(new BigDecimal(parentSort));
        //根据父级id获取父级的信息
        if (StrUtil.isNotBlank(sysMenu.getParentCode()) && !sysMenu.getParentCode().equals(parentCode)) {
            parent = sysMenuMapper.getSysMenuById(sysMenu.getParentCode());
        }
        if (ObjectUtil.isNull(parent)) {
            throw new BusinessException("父级菜单不存在");
        }
        //把所有的父级id以“,”隔开
        String parentCodes = parent.getParentCodes() == null ? "" : parent.getParentCodes();
        parentCodes = ((parentCodes + ",").equals(",") ? "" : (parentCodes + ",")) + parent.getMenuCode();
        String treeNames = parent.getTreeNames() == null ? "" : parent.getTreeNames();
        treeNames = ((treeNames + ",").equals(",") ? "" : (treeNames + ",")) + parent.getMenuName();
        String treeSorts = parent.getTreeSorts() == null ? "" : parent.getTreeSorts();
        treeSorts = ((treeSorts + ",").equals(",") ? "" : (treeSorts + ",")) + parent.getTreeSort();
        sysMenu.setParentCodes(parentCodes);
        sysMenu.setTreeSorts(treeSorts);
        sysMenu.setTreeNames(treeNames);
        sysMenu.setTreeLeaf("1");
        sysMenu.setParentCode(parent.getMenuCode());
        int treeLevel = parentCodes.split(",").length + 1;
        sysMenu.setTreeLevel(new BigDecimal(treeLevel));
        sysMenu.setDeleted(0);
        sysMenu.setStatus("1");
        //添加菜单
        super.save(sysMenu);
        //变更上级菜单的末级节点状态
        if (StrUtil.isNotBlank(parent.getTreeLeaf()) && parent.getTreeLeaf().equals("1")) {
            sysMenuMapper.updateTreeLeafById(parent.getMenuCode(), "2");
        }
        judge = true;
        return judge;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysMenu(SysMenu sysMenu) throws Exception {
        return super.updateById(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMenu(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMenus(List<String> idList) throws Exception {
        return super.removeByIds(idList);
    }

    @Override
    public SysMenuQueryVo getSysMenuById(String id) throws Exception {
        return sysMenuMapper.getSysMenuById(id);
    }

    @Override
    public Paging<SysMenuQueryVo> getSysMenuPageList(SysMenuQueryParam sysMenuQueryParam) throws Exception {
        Page page = setPageParam(sysMenuQueryParam, OrderItem.desc("tree_sort"));
        IPage<SysMenuQueryVo> iPage = sysMenuMapper.getSysMenuPageList(page, sysMenuQueryParam);
        return new Paging(iPage);
    }

    @Override
    public List<SysMenuTreeQueryVo> getSysMenuSimplifyPageList(SysMenuTypeQueryParam sysMenuTypeQueryParam) {
        List<SysMenuTreeQueryVo> list = sysMenuMapper.getSysMenuSimplifyPageList(sysMenuTypeQueryParam);
        return list;
    }

    @Override
    public List<SysMenuTreeQueryVo> getSysMenuByRoleId(Map<String, Object> map) {
        if(map.isEmpty()){
            throw new BusinessException("用户id不能为空");
        }
        List<SysMenuTreeQueryVo> list = sysMenuMapper.getSysMenuByRoleId(map.get("id").toString());
        return list;
    }

    @Override
    public List<SysMenuQueryVo> getSysMenuListByRoleId(String roleId){
        if(StringUtils.isBlank(roleId)){
            throw new BusinessException("角色id不能为空");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("roleId",roleId);
        map.put("menuCode","00000000");
        List<SysMenuQueryVo> list = sysMenuMapper.getSysMenuListByRoleId(map);
        return list;
    }

    @Override
    public List<SysMenuQueryVo> getSysMenuListByUserId(String userId){
        if(StringUtils.isBlank(userId)){
            throw new BusinessException("用户id不能为空");
        }
        String token = JwtTokenUtil.getToken();
        String username = JwtUtil.getUsername(token);
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        SysUser sysUser = sysUserMapper.selectOne(userQueryWrapper);
        Map<String,Object> map = new HashMap<>();
        List<SysMenuQueryVo> list = null;
        List<SysRoleQueryVo> roleList = new ArrayList<>();
        if((sysUser.getUserType().equals("2") || sysUser.getUserType().equals("3")) && whyySystemProperties.isEnableHwXueyuanWx() && StrUtil.isNotBlank(sysUser.getIsWx()) && sysUser.getIsWx().equals("0")){
            String roleType = "studentnowx";
            if (sysUser.getUserType().equals("3")) {
                roleType = "teachernowx";
            }
            SysRoleQueryVo sysRoleQueryVo = sysRoleMapper.getSysRoleQueryByType(roleType);
            map.put("roleId",sysRoleQueryVo.getId());
            map.put("menuCode","00000000");
            list = sysMenuMapper.getSysMenuListByRoleId(map);
        }else {
            map.put("userId",userId);
            map.put("menuCode","00000000");
            list = sysMenuMapper.getSysMenuListByUserId(map);
        }
        return list;
    }

}
