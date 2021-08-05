package com.io.yy.wxmngt.service.impl;

import com.io.yy.wxmngt.entity.CsFacilities;
import com.io.yy.wxmngt.mapper.CsFacilitiesMapper;
import com.io.yy.wxmngt.service.CsFacilitiesService;
import com.io.yy.wxmngt.param.CsFacilitiesQueryParam;
import com.io.yy.wxmngt.vo.CsFacilitiesQueryVo;
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
 * 服务设施管理 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Slf4j
@Service
public class CsFacilitiesServiceImpl extends BaseServiceImpl<CsFacilitiesMapper, CsFacilities> implements CsFacilitiesService {

    @Autowired
    private CsFacilitiesMapper csFacilitiesMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsFacilities(CsFacilities csFacilities) throws Exception {
        return super.save(csFacilities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsFacilities(CsFacilities csFacilities) throws Exception {
        return super.updateById(csFacilities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsFacilities(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsFacilitiess(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsFacilitiesQueryVo getCsFacilitiesById(Serializable id) throws Exception {
        return csFacilitiesMapper.getCsFacilitiesById(id);
    }

    @Override
    public Paging<CsFacilitiesQueryVo> getCsFacilitiesPageList(CsFacilitiesQueryParam csFacilitiesQueryParam) throws Exception {
        Page page = setPageParam(csFacilitiesQueryParam, OrderItem.desc("create_time"));
        IPage<CsFacilitiesQueryVo> iPage = csFacilitiesMapper.getCsFacilitiesPageList(page, csFacilitiesQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsFacilitiesQueryParam csFacilitiesQueryParam) {
        return csFacilitiesMapper.updateStatus(csFacilitiesQueryParam) > 0;
    }

}
