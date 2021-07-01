package com.io.yy.system.service.impl;

import com.io.yy.system.entity.SysConfig;
import com.io.yy.system.mapper.SysConfigMapper;
import com.io.yy.system.service.SysConfigService;
import com.io.yy.system.param.SysConfigQueryParam;
import com.io.yy.system.vo.SysConfigDataRedisVo;
import com.io.yy.system.vo.SysConfigQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import com.io.yy.util.ConfigDataUtil;
import com.io.yy.util.RedisAsyncUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.UUID;


/**
 * <pre>
 * 参数配置表 服务实现类
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-02
 */
@Slf4j
@Service
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Autowired
    private ConfigDataUtil configDataUtil;

    @Autowired
    private RedisAsyncUtil redisAsyncUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysConfig(SysConfig sysConfig) throws Exception {
        sysConfig.setId(UUID.randomUUID().toString());
        Boolean flag = super.save(sysConfig);
        if (flag) {
            SysConfigDataRedisVo sysConfigDataRedisVo = new SysConfigDataRedisVo();
            sysConfigDataRedisVo.setConfigKey(sysConfig.getConfigKey())
                    .setConfigName(sysConfig.getConfigName())
                    .setConfigValue(sysConfig.getConfigTextValue());
            redisAsyncUtil.cacheConfigDataInfo(sysConfigDataRedisVo);
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysConfig(SysConfig sysConfig) throws Exception {
        SysConfigQueryVo sysConfigQueryVo = sysConfigMapper.getSysConfigById(sysConfig.getId());
        String configKey = sysConfigQueryVo.getConfigKey();
        Boolean flag = super.updateById(sysConfig);
        if (flag) {
            SysConfigDataRedisVo sysConfigDataRedisVo = new SysConfigDataRedisVo();
            sysConfigDataRedisVo.setConfigKey(sysConfig.getConfigKey())
                    .setConfigName(sysConfig.getConfigName())
                    .setConfigValue(sysConfig.getConfigTextValue());
            configDataUtil.refreshSysConfigDataInfo(configKey, sysConfigDataRedisVo);
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysConfig(String id) throws Exception {
        SysConfigQueryVo sysConfigQueryVo = sysConfigMapper.getSysConfigById(id);
        String configKey = sysConfigQueryVo.getConfigKey();
        Boolean flag = super.removeById(id);
        if (flag) {
            configDataUtil.deleteSysConfigData(configKey);
        }
        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysConfigs(List<String> idList) throws Exception {
        Boolean flag = super.removeByIds(idList);
        if (flag) {
            List<SysConfigDataRedisVo> list = sysConfigMapper.getConfigListByLists(idList);
            for (SysConfigDataRedisVo sysConfigDataRedisVo : list) {
                configDataUtil.deleteSysConfigData(sysConfigDataRedisVo.getConfigKey());
            }
        }
        return flag;
    }

    @Override
    public SysConfigQueryVo getSysConfigById(String id) throws Exception {
        return sysConfigMapper.getSysConfigById(id);
    }

    @Override
    public Paging<SysConfigQueryVo> getSysConfigPageList(SysConfigQueryParam sysConfigQueryParam) throws Exception {
        Page page = setPageParam(sysConfigQueryParam, OrderItem.desc("create_time"));
        IPage<SysConfigQueryVo> iPage = sysConfigMapper.getSysConfigPageList(page, sysConfigQueryParam);
        return new Paging(iPage);
    }

    /**
     * 获取所有系统配置数据
     *
     * @return
     */
    @Override
    public List<SysConfigDataRedisVo> getAllSysConfigData() {
        return sysConfigMapper.getAllSysConfigData();
    }

}
