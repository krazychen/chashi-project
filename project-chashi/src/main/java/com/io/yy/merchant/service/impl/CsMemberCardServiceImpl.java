package com.io.yy.merchant.service.impl;

import com.io.yy.merchant.entity.CsMemberCard;
import com.io.yy.merchant.mapper.CsMemberCardMapper;
import com.io.yy.merchant.service.CsMemberCardService;
import com.io.yy.merchant.param.CsMemberCardQueryParam;
import com.io.yy.merchant.vo.CsMemberCardQueryVo;
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
 * 会员卡 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-09
 */
@Slf4j
@Service
public class CsMemberCardServiceImpl extends BaseServiceImpl<CsMemberCardMapper, CsMemberCard> implements CsMemberCardService {

    @Autowired
    private CsMemberCardMapper csMemberCardMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMemberCard(CsMemberCard csMemberCard) throws Exception {
        return super.save(csMemberCard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMemberCard(CsMemberCard csMemberCard) throws Exception {
        return super.updateById(csMemberCard);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMemberCard(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMemberCards(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMemberCardQueryVo getCsMemberCardById(Serializable id) throws Exception {
        return csMemberCardMapper.getCsMemberCardById(id);
    }

    @Override
    public Paging<CsMemberCardQueryVo> getCsMemberCardPageList(CsMemberCardQueryParam csMemberCardQueryParam) throws Exception {
        Page page = setPageParam(csMemberCardQueryParam, OrderItem.desc("create_time"));
        IPage<CsMemberCardQueryVo> iPage = csMemberCardMapper.getCsMemberCardPageList(page, csMemberCardQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsMemberCardQueryParam csMemberCardQueryParam) {
        return csMemberCardMapper.updateStatus(csMemberCardQueryParam) > 0;
    }

}
