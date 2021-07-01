package com.io.yy.system.service.impl;

import com.io.yy.common.exception.BusinessException;
import com.io.yy.system.entity.SysDictType;
import com.io.yy.system.mapper.SysDictTypeMapper;
import com.io.yy.system.param.SysDictTypeStatusQueryParam;
import com.io.yy.system.service.SysDictDataRedisService;
import com.io.yy.system.service.SysDictTypeService;
import com.io.yy.system.param.SysDictTypeQueryParam;
import com.io.yy.system.vo.SysDictTypeQueryVo;
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
 * 字典类型表 服务实现类
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Slf4j
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Autowired
    private SysDictDataRedisService sysDictDataRedisService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysDictType(SysDictType sysDictType) throws Exception {
        sysDictType.setStatus("1");
        return super.save(sysDictType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysDictType(SysDictType sysDictType) throws Exception {
        if (sysDictTypeMapper.getSysDictTypeCountByDictType(sysDictType.getDictType(), sysDictType.getId()) > 0) {
            throw new BusinessException("字典类型不能重复");
        }
        return super.updateById(sysDictType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysDictType(String id) throws Exception {
        SysDictType sysDictType = super.getById(id);
        Boolean flag = super.removeById(id);
        if (flag) {
            sysDictDataRedisService.deleteSysDictData(sysDictType.getDictType());
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysDictTypes(List<String> idList) throws Exception {
        List<String> list = sysDictTypeMapper.SysDictTypesByidList(idList);
        Boolean flag = super.removeByIds(idList);
        if (flag) {
            for (String dictType : list) {
                sysDictDataRedisService.deleteSysDictData(dictType);
            }
        }
        return flag;
    }

    @Override
    public SysDictTypeQueryVo getSysDictTypeById(String id) throws Exception {
        return sysDictTypeMapper.getSysDictTypeById(id);
    }

    @Override
    public Paging<SysDictTypeQueryVo> getSysDictTypePageList(SysDictTypeQueryParam sysDictTypeQueryParam) throws Exception {
        Page page = setPageParam(sysDictTypeQueryParam, OrderItem.desc("create_time"));
        IPage<SysDictTypeQueryVo> iPage = sysDictTypeMapper.getSysDictTypePageList(page, sysDictTypeQueryParam);
        return new Paging(iPage);
    }

    @Override
    public Boolean updateStatusById(SysDictTypeStatusQueryParam sysDictTypeStatusQueryParam) {
        return sysDictTypeMapper.updateStatusById(sysDictTypeStatusQueryParam) > 0;
    }

}
