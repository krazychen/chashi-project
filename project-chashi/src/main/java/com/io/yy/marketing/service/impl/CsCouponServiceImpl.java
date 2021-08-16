package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsCoupon;
import com.io.yy.marketing.mapper.CsCouponMapper;
import com.io.yy.marketing.service.CsCouponService;
import com.io.yy.marketing.param.CsCouponQueryParam;
import com.io.yy.marketing.vo.CsCouponQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 优惠卷 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
@Slf4j
@Service
public class CsCouponServiceImpl extends BaseServiceImpl<CsCouponMapper, CsCoupon> implements CsCouponService {

    @Autowired
    private CsCouponMapper csCouponMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsCoupon(CsCoupon csCoupon) throws Exception {
        return super.save(csCoupon);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsCoupon(CsCoupon csCoupon) throws Exception {
        return super.updateById(csCoupon);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsCoupon(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsCoupons(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsCouponQueryVo getCsCouponById(Serializable id) throws Exception {
        return csCouponMapper.getCsCouponById(id);
    }

    @Override
    public Paging<CsCouponQueryVo> getCsCouponPageList(CsCouponQueryParam csCouponQueryParam) throws Exception {
        Page page = setPageParam(csCouponQueryParam, OrderItem.desc("create_time"));
        IPage<CsCouponQueryVo> iPage = csCouponMapper.getCsCouponPageList(page, csCouponQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsCouponQueryParam csCouponQueryParam) {
        return csCouponMapper.updateStatus(csCouponQueryParam) > 0;
    }

    /**
     * 获取新人优惠卷
     *
     * @return
     */
    @Override
    public List<CsCouponQueryVo> getCsCouponOfNewMember() {
        return csCouponMapper.getCsCouponOfNewMember();
    }

    /**
     * 获取领卷中心优惠卷
     *
     * @return
     */
    @Override
    public List<CsCouponQueryVo> getCsCouponListOfCouponCenter() {
        return csCouponMapper.getCsCouponListOfCouponCenter();
    }

}
