package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.marketing.mapper.CsMembercardOrderMapper;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.vo.CsMemberCardQueryVo;
import com.io.yy.marketing.vo.CsMembercardOrderQueryVo;
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
 * 会员卡购买记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Slf4j
@Service
public class CsMembercardOrderServiceImpl extends BaseServiceImpl<CsMembercardOrderMapper, CsMembercardOrder> implements CsMembercardOrderService {

    @Autowired
    private CsMembercardOrderMapper csMembercardOrderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMembercardOrder(CsMembercardOrder csMembercardOrder) throws Exception {
        //  检查是否购买过会员卡，购买过则不能再购买;
        CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
        csMembercardOrderQueryParam.setMembercardId(csMembercardOrder.getMembercardId());
        csMembercardOrderQueryParam.setWxuserId(csMembercardOrder.getWxuserId());
        CsMembercardOrderQueryVo csMembercardOrderQueryVo = csMembercardOrderMapper.isExistCsMembercardOrderByUserId(csMembercardOrderQueryParam);
        if(csMembercardOrderQueryVo != null){
            return false;
        }

        return super.save(csMembercardOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMembercardOrder(CsMembercardOrder csMembercardOrder) throws Exception {
        return super.updateById(csMembercardOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMembercardOrder(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMembercardOrders(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMembercardOrderQueryVo getCsMembercardOrderById(Serializable id) throws Exception {
        return csMembercardOrderMapper.getCsMembercardOrderById(id);
    }

    @Override
    public Paging<CsMembercardOrderQueryVo> getCsMembercardOrderPageList(CsMembercardOrderQueryParam csMembercardOrderQueryParam) throws Exception {
        Page page = setPageParam(csMembercardOrderQueryParam, OrderItem.desc("create_time"));
        IPage<CsMembercardOrderQueryVo> iPage = csMembercardOrderMapper.getCsMembercardOrderPageList(page, csMembercardOrderQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsMembercardOrderQueryParam csMembercardOrderQueryParam) {
        return csMembercardOrderMapper.updateStatus(csMembercardOrderQueryParam) > 0;
    }

    /**
     * 根据ID获取查询对象
     *
     * @param wxuserId
     * @return
     * @throws Exception
     */
    @Override
    public CsMembercardOrderQueryVo getMemberCardForWx(Serializable wxuserId) throws Exception {
        CsMembercardOrderQueryParam csMembercardOrderQueryParam = new CsMembercardOrderQueryParam();
        csMembercardOrderQueryParam.setWxuserId(Long.valueOf(wxuserId.toString()));
        return csMembercardOrderMapper.isExistCsMembercardOrderByUserId(csMembercardOrderQueryParam);
    }

    /**
     * 更新支付状态
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    @Override
    public boolean updatePaymentStatus(CsMembercardOrderQueryParam csMembercardOrderQueryParam) {
        return csMembercardOrderMapper.updatePaymentStatus(csMembercardOrderQueryParam) > 0;
    }

    /**
     * 更新剩余优惠时长和优惠金额
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    @Override
    public boolean updateRest(CsMembercardOrderQueryParam csMembercardOrderQueryParam) {
        return csMembercardOrderMapper.updateRest(csMembercardOrderQueryParam) > 0;
    }

}
