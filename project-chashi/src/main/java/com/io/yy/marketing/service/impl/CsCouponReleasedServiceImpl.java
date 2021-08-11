package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsCouponReleased;
import com.io.yy.marketing.mapper.CsCouponReleasedMapper;
import com.io.yy.marketing.service.CsCouponReleasedService;
import com.io.yy.marketing.param.CsCouponReleasedQueryParam;
import com.io.yy.marketing.vo.CsCouponReleasedQueryVo;
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
import java.util.Date;
import java.util.List;


/**
 * <pre>
 * 优惠卷发放/领取记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-10
 */
@Slf4j
@Service
public class CsCouponReleasedServiceImpl extends BaseServiceImpl<CsCouponReleasedMapper, CsCouponReleased> implements CsCouponReleasedService {

    @Autowired
    private CsCouponReleasedMapper csCouponReleasedMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsCouponReleased(CsCouponReleased csCouponReleased) throws Exception {
        csCouponReleased.setReleasedTime(new Date());
        csCouponReleased.setIsUsed(0);
        return super.save(csCouponReleased);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsCouponReleased(CsCouponReleased csCouponReleased) throws Exception {
        return super.updateById(csCouponReleased);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsCouponReleased(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsCouponReleaseds(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsCouponReleasedQueryVo getCsCouponReleasedById(Serializable id) throws Exception {
        return csCouponReleasedMapper.getCsCouponReleasedById(id);
    }

    @Override
    public Paging<CsCouponReleasedQueryVo> getCsCouponReleasedPageList(CsCouponReleasedQueryParam csCouponReleasedQueryParam) throws Exception {
        Page page = setPageParam(csCouponReleasedQueryParam, OrderItem.desc("create_time"));
        IPage<CsCouponReleasedQueryVo> iPage = csCouponReleasedMapper.getCsCouponReleasedPageList(page, csCouponReleasedQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsCouponReleasedQueryParam csCouponReleasedQueryParam) {
        return csCouponReleasedMapper.updateStatus(csCouponReleasedQueryParam) > 0;
    }

    /**
     * 为新人发放优惠卷更新状态
     *
     * @param csCouponReleased
     * @return
     */
    @Override
    public boolean releaseForNewMember(CsCouponReleased csCouponReleased) throws Exception {
        return false;
    }

}
