package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.marketing.mapper.CsMembercardOrderMapper;
import com.io.yy.marketing.service.CsMembercardOrderService;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
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

}
