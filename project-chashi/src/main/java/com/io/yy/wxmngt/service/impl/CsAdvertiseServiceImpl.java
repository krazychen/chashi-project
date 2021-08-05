package com.io.yy.wxmngt.service.impl;

import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.wxmngt.entity.CsAdvertise;
import com.io.yy.wxmngt.mapper.CsAdvertiseMapper;
import com.io.yy.wxmngt.service.CsAdvertiseService;
import com.io.yy.wxmngt.param.CsAdvertiseQueryParam;
import com.io.yy.wxmngt.vo.CsAdvertiseQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


/**
 * <pre>
 * 广告设置 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-02
 */
@Slf4j
@Service
public class CsAdvertiseServiceImpl extends BaseServiceImpl<CsAdvertiseMapper, CsAdvertise> implements CsAdvertiseService {

    @Autowired
    private CsAdvertiseMapper csAdvertiseMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsAdvertise(CsAdvertise csAdvertise) throws Exception {
        return super.save(csAdvertise);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsAdvertise(CsAdvertise csAdvertise) throws Exception {
        return super.updateById(csAdvertise);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsAdvertise(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsAdvertises(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsAdvertiseQueryVo getCsAdvertiseById(Long id) throws Exception {
        return csAdvertiseMapper.getCsAdvertiseById(id);
    }

    @Override
    public Paging<CsAdvertiseQueryVo> getCsAdvertisePageList(CsAdvertiseQueryParam csAdvertiseQueryParam) throws Exception {
        Page page = setPageParam(csAdvertiseQueryParam, OrderItem.desc("create_time"));
        IPage<CsAdvertiseQueryVo> iPage = csAdvertiseMapper.getCsAdvertisePageList(page, csAdvertiseQueryParam);
        return new Paging(iPage);
    }

    /**
     * 通过ID更新status
     *
     * @param csAdvertiseQueryParam
     * @return
     * @throws Exception
     */
    @Override
    public boolean updateStatusById(CsAdvertiseQueryParam csAdvertiseQueryParam) {
        return csAdvertiseMapper.updateStatusById(csAdvertiseQueryParam) > 0;
    }
}
