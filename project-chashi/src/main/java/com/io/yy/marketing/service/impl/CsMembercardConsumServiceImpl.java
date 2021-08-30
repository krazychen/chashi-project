package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsMembercardConsum;
import com.io.yy.marketing.mapper.CsMembercardConsumMapper;
import com.io.yy.marketing.service.CsMembercardConsumService;
import com.io.yy.marketing.param.CsMembercardConsumQueryParam;
import com.io.yy.marketing.vo.CsMembercardConsumQueryVo;
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
 * 会员卡消费记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-24
 */
@Slf4j
@Service
public class CsMembercardConsumServiceImpl extends BaseServiceImpl<CsMembercardConsumMapper, CsMembercardConsum> implements CsMembercardConsumService {

    @Autowired
    private CsMembercardConsumMapper csMembercardConsumMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMembercardConsum(CsMembercardConsum csMembercardConsum) throws Exception {
        return super.save(csMembercardConsum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMembercardConsum(CsMembercardConsum csMembercardConsum) throws Exception {
        return super.updateById(csMembercardConsum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMembercardConsum(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMembercardConsums(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMembercardConsumQueryVo getCsMembercardConsumById(Serializable id) throws Exception {
        return csMembercardConsumMapper.getCsMembercardConsumById(id);
    }

    @Override
    public Paging<CsMembercardConsumQueryVo> getCsMembercardConsumPageList(CsMembercardConsumQueryParam csMembercardConsumQueryParam) throws Exception {
        Page page = setPageParam(csMembercardConsumQueryParam, OrderItem.desc("create_time"));
        IPage<CsMembercardConsumQueryVo> iPage = csMembercardConsumMapper.getCsMembercardConsumPageList(page, csMembercardConsumQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsMembercardConsumQueryParam csMembercardConsumQueryParam) {
        return csMembercardConsumMapper.updateStatus(csMembercardConsumQueryParam) > 0;
    }

}
