package com.io.yy.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.io.yy.system.entity.SysMsgRecord;
import com.io.yy.system.mapper.SysMsgRecordMapper;
import com.io.yy.system.service.SysMsgRecordService;
import com.io.yy.system.param.SysMsgRecordQueryParam;
import com.io.yy.system.vo.SysMsgRecordQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;
import java.util.List;


/**
 * <pre>
 * 系统消息记录表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Slf4j
@Service
public class SysMsgRecordServiceImpl extends BaseServiceImpl<SysMsgRecordMapper, SysMsgRecord> implements SysMsgRecordService {

    @Autowired
    private SysMsgRecordMapper sysMsgRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysMsgRecord(SysMsgRecord sysMsgRecord) throws Exception {
        return super.save(sysMsgRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysMsgRecord(SysMsgRecord sysMsgRecord) throws Exception {
        return super.updateById(sysMsgRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMsgRecord(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysMsgRecords(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public SysMsgRecordQueryVo getSysMsgRecordById(Long id) throws Exception {
        return sysMsgRecordMapper.getSysMsgRecordById(id);
    }

    @Override
    public Paging<SysMsgRecordQueryVo> getSysMsgRecordPageList(SysMsgRecordQueryParam sysMsgRecordQueryParam) throws Exception {
        Page page = setPageParam(sysMsgRecordQueryParam, OrderItem.desc("create_time,id"));
        IPage<SysMsgRecordQueryVo> iPage = sysMsgRecordMapper.getSysMsgRecordPageList(page, sysMsgRecordQueryParam);
        return new Paging(iPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateByRecord(String id) throws Exception {
        SysMsgRecord msgRecord = getById(id);
        //设置读取状态和读取时间
        msgRecord.setStatus("1")
                 .setReadTime(new Date());
        return super.updateById(msgRecord);
    }

    @Override
    public Integer findMsgRecordCount(Long id) throws Exception {
        QueryWrapper<SysMsgRecord> msgRecordQueryWrapper = new QueryWrapper<>();
        msgRecordQueryWrapper.eq("receive_user_id",id);
        msgRecordQueryWrapper.eq("status","2");
        int count = sysMsgRecordMapper.selectCount(msgRecordQueryWrapper);
        return count;
    }

}
