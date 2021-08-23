package com.io.yy.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.marketing.vo.CsMembercardOrderQueryVo;
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
import com.io.yy.util.lang.StringUtils;
import com.io.yy.wxops.vo.WxUserQueryVo;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;
import com.spatial4j.core.shape.impl.RectangleImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


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

    private SpatialContext spatialContext = SpatialContext.GEO;

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

    @Override
    public Paging<CsMerchantQueryVo> getCsMerchantPageListForWx(CsMerchantQueryParam csMerchantQueryParam) throws Exception {
        IPage<CsMerchantQueryVo> iPage = null;
        Rectangle rectangle = null;
        Page page = setPageParam(csMerchantQueryParam, null);
        switch (csMerchantQueryParam.getSize()){
            case 2:
                //1.获取外接正方形, 经纬度为空，以厦门市的经纬度为准
                if(StringUtils.isNotEmpty(csMerchantQueryParam.getUserLng())&&StringUtils.isNotEmpty(csMerchantQueryParam.getUserLat())){
                    rectangle = getRectangle(csMerchantQueryParam.getDistance(),Double.valueOf(csMerchantQueryParam.getUserLng()), Double.valueOf(csMerchantQueryParam.getUserLat()));
                }else{
                    rectangle = getRectangle(csMerchantQueryParam.getDistance(),Double.valueOf("118.0894"), Double.valueOf("24.4798"));
                }
                //2.获取位置在正方形内的所有用户
                csMerchantQueryParam.setMinlng(rectangle.getMinX());
                csMerchantQueryParam.setMaxlng(rectangle.getMaxX());
                csMerchantQueryParam.setMinlat(rectangle.getMinY());
                csMerchantQueryParam.setMaxlat(rectangle.getMaxY());
                iPage = csMerchantMapper.getCsMerchantPageListOrderByNear(page,csMerchantQueryParam);
//                //3.剔除半径超过指定距离的多余用户
//                List<CsMerchantQueryVo> csMerchantQueryVos = iPage.getRecords().stream()
//                        .filter(a -> getDistance(Double.valueOf(a.getLongitude()), Double.valueOf(a.getLatitude()), Double.valueOf(csMerchantQueryParam.getUserLng()), Double.valueOf(csMerchantQueryParam.getUserLat())    ) <= csMerchantQueryParam.getDistance())
//                        .collect(Collectors.toList());
//                iPage.setRecords(csMerchantQueryVos);
                break;
            case 3:
                iPage = csMerchantMapper.getCsMerchantPageListOrderByPriceASC(page, csMerchantQueryParam);
                break;
            case 4:
                iPage = csMerchantMapper.getCsMerchantPageListOrderByPriceDESC(page, csMerchantQueryParam);
                break;
            default:
                //1.获取外接正方形, 经纬度为
                if(StringUtils.isNotEmpty(csMerchantQueryParam.getUserLng())&&StringUtils.isNotEmpty(csMerchantQueryParam.getUserLat())){
                    rectangle = getRectangle(csMerchantQueryParam.getDistance(),Double.valueOf(csMerchantQueryParam.getUserLng()), Double.valueOf(csMerchantQueryParam.getUserLat()));
                    csMerchantQueryParam.setMinlng(rectangle.getMinX());
                    csMerchantQueryParam.setMaxlng(rectangle.getMaxX());
                    csMerchantQueryParam.setMinlat(rectangle.getMinY());
                    csMerchantQueryParam.setMaxlat(rectangle.getMaxY());
                }else{
                    csMerchantQueryParam.setMinlng(0);
                    csMerchantQueryParam.setMaxlng(0);
                    csMerchantQueryParam.setMinlat(0);
                    csMerchantQueryParam.setMaxlat(0);
                }
                //2.获取位置在正方形内的所有用户
                page = setPageParam(csMerchantQueryParam, OrderItem.desc("create_time"));
                iPage = csMerchantMapper.getCsMerchantPageListOrderByNear(page,csMerchantQueryParam);

        }
//        Page page = setPageParam(csMerchantQueryParam, OrderItem.desc("create_time"));
//        IPage<CsMerchantQueryVo> iPage = csMerchantMapper.getCsMerchantPageList(page, csMerchantQueryParam);

        //计算用户和商店的距离
        if(StringUtils.isNotEmpty(csMerchantQueryParam.getUserLng())&&StringUtils.isNotEmpty(csMerchantQueryParam.getUserLat())) {
            iPage.getRecords().stream().forEach(
                    a -> a.setMerchantDistance(
                            getDistance(Double.valueOf(a.getLongitude()), Double.valueOf(a.getLatitude()),
                                    Double.valueOf(csMerchantQueryParam.getUserLng()), Double.valueOf(csMerchantQueryParam.getUserLat()))));
        }else{
            iPage.getRecords().stream().forEach(
                    a -> a.setMerchantDistance(Double.valueOf(0)));
        }

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

    /**
     * wx根据ID获取查询对象
     *
     * @param csMerchantQueryPara
     * @return
     * @throws Exception
     */
    @Override
    public CsMerchantQueryVo getCsMerchantByIdForWx(CsMerchantQueryParam csMerchantQueryPara) throws Exception {
        return csMerchantMapper.getCsMerchantByIdForWx(csMerchantQueryPara.getId());
    }

    // 计算半径

    //地球半径常量，km
    private static final double EARTH_RADIUS = 6378.137;

    private Rectangle getRectangle(double distance, double userLng, double userLat) {
        return spatialContext.getDistCalc()
                .calcBoxByDistFromPt(spatialContext.makePoint(userLng, userLat),
                        distance * DistanceUtils.KM_TO_DEG, spatialContext, null);
    }

    /**
     * 根据地球上任意两点的经纬度计算两点间的距离,返回距离单位：km
     *
     * @param longitude1 坐标1 经度
     * @param latitude1  坐标1 纬度
     * @param longitude2 坐标2 经度
     * @param latitude2  坐标2 纬度
     * @return 返回km
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double radLat1 = rad(latitude1);
        double radLat2 = rad(latitude2);
        double a = radLat1 - radLat2;
        double b = rad(longitude1) - rad(longitude2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000.0;
        return distance;
    }

    /**
     * 角度转弧度
     *
     * @param d
     * @return
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}

