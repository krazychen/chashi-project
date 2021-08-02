package com.io.yy.wxmngt.service.impl;

import com.io.yy.wxmngt.entity.CsLabel;
import com.io.yy.wxmngt.mapper.CsLabelMapper;
import com.io.yy.wxmngt.service.CsLabelService;
import com.io.yy.wxmngt.param.CsLabelQueryParam;
import com.io.yy.wxmngt.vo.CsLabelQueryVo;
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
 * 标签管理 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-07-08
 */
@Slf4j
@Service
public class CsLabelServiceImpl extends BaseServiceImpl<CsLabelMapper, CsLabel> implements CsLabelService {

    @Autowired
    private CsLabelMapper csLabelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsLabel(CsLabel csLabel) throws Exception {
        return super.save(csLabel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsLabel(CsLabel csLabel) throws Exception {
        return super.updateById(csLabel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsLabel(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsLabels(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsLabelQueryVo getCsLabelById(Long id) throws Exception {
        return csLabelMapper.getCsLabelById(id);
    }

    @Override
    public Paging<CsLabelQueryVo> getCsLabelPageList(CsLabelQueryParam csLabelQueryParam) throws Exception {
        Page page = setPageParam(csLabelQueryParam, OrderItem.desc("create_time"));
        IPage<CsLabelQueryVo> iPage = csLabelMapper.getCsLabelPageList(page, csLabelQueryParam);
        return new Paging(iPage);
    }

}
