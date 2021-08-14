package com.io.yy.merchant.service.impl;

import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.mapper.CsMerchantOrderMapper;
import com.io.yy.merchant.service.CsMerchantOrderService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
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
 * 商店茶室订单记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Slf4j
@Service
public class CsMerchantOrderServiceImpl extends BaseServiceImpl<CsMerchantOrderMapper, CsMerchantOrder> implements CsMerchantOrderService {

    @Autowired
    private CsMerchantOrderMapper csMerchantOrderMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception {
        return super.save(csMerchantOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception {
        return super.updateById(csMerchantOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantOrder(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantOrders(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMerchantOrderQueryVo getCsMerchantOrderById(Serializable id) throws Exception {
        return csMerchantOrderMapper.getCsMerchantOrderById(id);
    }

    @Override
    public Paging<CsMerchantOrderQueryVo> getCsMerchantOrderPageList(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception {
        Page page = setPageParam(csMerchantOrderQueryParam, OrderItem.desc("create_time"));
        IPage<CsMerchantOrderQueryVo> iPage = csMerchantOrderMapper.getCsMerchantOrderPageList(page, csMerchantOrderQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        return csMerchantOrderMapper.updateStatus(csMerchantOrderQueryParam) > 0;
    }

    /**
     * 根据tearoomid和预订日期获取当前茶室已经被预定的时间段，返回是时间段的一个包含","的字符串
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    @Override
    public String getTimeRangeForWx(CsMerchantOrderQueryParam csMerchantOrderQueryParam) {
        List<CsMerchantOrderQueryVo> list = csMerchantOrderMapper.getTimeRangeForWx(csMerchantOrderQueryParam);
        String timeRanges = "";
        for(int i=0 ; i<list.size();i++){
            if(i != list.size()-1){
                timeRanges += list.get(i)+",";
            }else{
                timeRanges += list.get(i);
            }
        }
        return timeRanges;
    }

}
