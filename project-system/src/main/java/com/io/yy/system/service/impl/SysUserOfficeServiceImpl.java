package com.io.yy.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.system.entity.SysUserOffice;
import com.io.yy.system.mapper.SysUserOfficeMapper;
import com.io.yy.system.param.SysUserOfficeQueryParam;
import com.io.yy.system.service.SysUserOfficeService;
import com.io.yy.system.vo.SysUserOfficeQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <pre>
 * 用户和机构关联表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-30
 */
@Slf4j
@Service
public class SysUserOfficeServiceImpl extends BaseServiceImpl<SysUserOfficeMapper, SysUserOffice> implements SysUserOfficeService {

    @Autowired
    private SysUserOfficeMapper sysUserOfficeMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUserOffice(SysUserOffice sysUserOffice) throws Exception {
        return super.save(sysUserOffice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUserOffice(SysUserOffice sysUserOffice) throws Exception {
        return super.updateById(sysUserOffice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUserOffice(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUserOffices(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysUserOfficeQueryVo getSysUserOfficeById(Long id) throws Exception {
        return sysUserOfficeMapper.getSysUserOfficeById(id);
    }

    @Override
    public Paging<SysUserOfficeQueryVo> getSysUserOfficePageList(SysUserOfficeQueryParam sysUserOfficeQueryParam) throws Exception {
        Page page = setPageParam(sysUserOfficeQueryParam, OrderItem.desc("create_time"));
        IPage<SysUserOfficeQueryVo> iPage = sysUserOfficeMapper.getSysUserOfficePageList(page, sysUserOfficeQueryParam);
        return new Paging(iPage);
    }

}
