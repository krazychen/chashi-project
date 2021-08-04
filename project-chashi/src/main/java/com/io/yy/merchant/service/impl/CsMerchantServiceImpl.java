package com.io.yy.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.mapper.CsMerchantMapper;
import com.io.yy.merchant.service.CsMerchantService;
import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.merchant.vo.CsMerchantQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.system.entity.SysOffice;
import com.io.yy.system.entity.SysUser;
import com.io.yy.system.entity.SysUserRole;
import com.io.yy.system.param.PasswordQueryParam;
import com.io.yy.system.service.SysOfficeService;
import com.io.yy.system.service.SysUserRoleService;
import com.io.yy.system.service.SysUserService;
import com.io.yy.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.util.List;


/**
 * <pre>
 * 商家管理 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-07-23
 */
@Slf4j
@Service
public class CsMerchantServiceImpl extends BaseServiceImpl<CsMerchantMapper, CsMerchant> implements CsMerchantService {

    @Autowired
    private CsMerchantMapper csMerchantMapper;

    @Autowired
    private SysOfficeService sysOfficeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMerchant(CsMerchant csMerchant) throws Exception {
        return super.save(csMerchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMerchantAOffice(CsMerchant csMerchant) throws Exception {

        Boolean flag;
        String code = UUIDUtil.getUUID();
        csMerchant.setOfficeCode(code);
        csMerchant.setCorpCode(code);
        csMerchant.setCorpName(csMerchant.getMerchantName());

        flag = super.save(csMerchant);

        // 商店保存成功
        if(flag) {

            // 生成office信息
            SysOffice sysOffice = new SysOffice();
            sysOffice.setViewCode(code);
            sysOffice.setParentCode("00000000");
            sysOffice.setOfficeCode(code);
            sysOffice.setOfficeName(csMerchant.getMerchantName());
            sysOffice.setFullName(csMerchant.getMerchantName());
            sysOffice.setOfficeType("1");
            sysOffice.setTreeSort(new BigDecimal(1));
            sysOffice.setStatus("1");

            sysOfficeService.saveSysOffice(sysOffice);

            // 生成账户信息和关联的角色信息
            SysUser sysUser = new SysUser();
            sysUser.setUsername(csMerchant.getMerchantAccount());
            sysUser.setNickname(csMerchant.getMerchantAccount());
            sysUser.setPassword(csMerchant.getMerchantPassword());
            sysUser.setOfficeId(sysOffice.getOfficeCode());
            sysUser.setUserType("2");
            sysUser.setMgrType("0");

            sysUserService.saveSysUserNotCheckOffice(sysUser);


            // 生成账户角色
            SysUser sysUserAfterSave = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", sysUser.getUsername()));
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUserAfterSave.getId());
            sysUserRole.setRoleId(new Long(1));
            sysUserRoleService.saveSysUserRole(sysUserRole);

        }

        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMerchant(CsMerchant csMerchant) throws Exception {
        return super.updateById(csMerchant);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMerchantAOffice(CsMerchant csMerchant) throws Exception {

        Boolean flag;
        csMerchant.setCorpName(csMerchant.getMerchantName());
        flag = super.updateById(csMerchant);

        // 商店保存成功
        if(flag) {

            // 获取office信息
            SysOffice sysOffice = sysOfficeService.getById(csMerchant.getOfficeCode());
            sysOffice.setOfficeName(csMerchant.getMerchantName());
            sysOffice.setFullName(csMerchant.getMerchantName());
            sysOffice.setCorpName(csMerchant.getMerchantName());

            sysOfficeService.updateSysOffice(sysOffice);

            // 生成账户信息和关联的角色信息
            SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", csMerchant.getMerchantAccount()));
            PasswordQueryParam passwordQueryParam = new PasswordQueryParam();
            passwordQueryParam.setPass(csMerchant.getMerchantPassword());
            passwordQueryParam.setId(sysUser.getId().toString());

            sysUserService.passwordSetting(passwordQueryParam);
        }

        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchant(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantAOffice(Long id) throws Exception {
        Boolean flag;

        CsMerchant csMerchant = this.getById(id);

        flag = super.removeById(id);

        // 商店保存成功
        if(flag) {

            // 删除office信息
            sysOfficeService.deleteSysOffice(csMerchant.getOfficeCode());

            // 删除账户、账户和office、账户的角色关联信息
            SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", csMerchant.getMerchantAccount()));
            sysUserService.deleteSysUser(sysUser.getId());
        }

        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchants(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMerchantQueryVo getCsMerchantById(Long id) throws Exception {
        return csMerchantMapper.getCsMerchantById(id);
    }

    @Override
    public CsMerchantQueryVo getCsMerchantByOfficeCode(String officeCode) throws Exception {
        return csMerchantMapper.getCsMerchantByOfficeCode(officeCode);
    }

    @Override
    public Paging<CsMerchantQueryVo> getCsMerchantPageList(CsMerchantQueryParam csMerchantQueryParam) throws Exception {
        Page page = setPageParam(csMerchantQueryParam, OrderItem.desc("create_time"));
        IPage<CsMerchantQueryVo> iPage = csMerchantMapper.getCsMerchantPageList(page, csMerchantQueryParam);
        return new Paging(iPage);
    }

    /**
     * 通过ID更新status
     *
     * @param csMerchantQueryParam
     * @return
     * @throws Exception
     */
    @Override
    public Boolean updateStatusById(CsMerchantQueryParam csMerchantQueryParam) {
        return csMerchantMapper.updateStatusById(csMerchantQueryParam) > 0;
    }

}
