package com.io.yy.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.common.exception.BusinessException;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.shiro.util.LoginUtil;
import com.io.yy.shiro.vo.LoginSysUserRedisVo;
import com.io.yy.system.entity.SysRoleMenu;
import com.io.yy.system.mapper.SysRoleMenuMapper;
import com.io.yy.system.param.SysRoleMenuQueryParam;
import com.io.yy.system.service.SysRoleMenuService;
import com.io.yy.system.vo.SysRoleMenuQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <pre>
 * 角色与菜单关联表 服务实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-03
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception {
        return super.save(sysRoleMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception {
        return super.updateById(sysRoleMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysRoleMenu(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysRoleMenus(List<String> idList) throws Exception {
        return super.removeByIds(idList);
    }

    @Override
    public SysRoleMenuQueryVo getSysRoleMenuById(Long id) throws Exception {
        return sysRoleMenuMapper.getSysRoleMenuById(id);
    }

    @Override
    public Paging<SysRoleMenuQueryVo> getSysRoleMenuPageList(SysRoleMenuQueryParam sysRoleMenuQueryParam) throws Exception {
        Page page = setPageParam(sysRoleMenuQueryParam, OrderItem.desc("create_time"));
        IPage<SysRoleMenuQueryVo> iPage = sysRoleMenuMapper.getSysRoleMenuPageList(page, sysRoleMenuQueryParam);
        return new Paging(iPage);
    }

    /**
     * 批量插入角色和菜单的关联
     *
     * @param sysRoleMenuQueryParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addRoleAndMenu(SysRoleMenuQueryParam sysRoleMenuQueryParam) {
        LoginSysUserRedisVo loginSysUserRedisVo = LoginUtil.getLoginSysUserRedisVo();
        if (ObjectUtil.isNull(loginSysUserRedisVo))
            throw new BusinessException("当前用户未登录");
        //此处做剔除数据操作
        List<String> listCodeWeb = sysRoleMenuQueryParam.getIdList();
        List<String> listCodeWeb1 = ObjectUtil.cloneByStream(listCodeWeb);
        //获取角色对应的菜单code
        List<String> listCode = sysRoleMenuMapper.getSysRoleMenuCodeByRoleId(sysRoleMenuQueryParam.getId());

        //取交集
        listCodeWeb.retainAll(listCode);

        //用前台的数据去除并集，得到要写入数据库的数据
        listCodeWeb1.removeAll(listCodeWeb);

        //用数据库的去除并集，得到的是要从数据库移除的数据
        listCode.removeAll(listCodeWeb);
        //sysRoleMenuMapper.deleteRoleMenuByRoleId(sysRoleMenuQueryParam.getId());

        Boolean judge = false;
        ArrayList<SysRoleMenu> list = null;
        if (ObjectUtil.isNotNull(listCodeWeb1)) {
            Date date = new Date();
            list = CollUtil.newArrayList();
            for (String id : listCodeWeb1) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu
//                        .setCorpCode("")
//                        .setCorpName("")
                        .setCreateBy(loginSysUserRedisVo.getId().toString())
                        .setCreateTime(date)
                        .setUpdateBy(loginSysUserRedisVo.getId().toString())
                        .setRoleId(sysRoleMenuQueryParam.getId())
                        .setUpdateTime(date)
                        .setMenuCode(id)
//                        .setVersion(1)
                        .setDeleted(0);
                list.add(sysRoleMenu);
            }
        }
        if (CollUtil.isNotEmpty(list)) {
            judge = super.saveBatch(list);
        }
        if(CollUtil.isNotEmpty(listCode)){
            judge = sysRoleMenuMapper.deleteByMenuCodes(listCode,sysRoleMenuQueryParam.getId())>=0;
        }
        return judge;
    }

}
