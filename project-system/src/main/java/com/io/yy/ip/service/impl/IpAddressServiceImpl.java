

package com.io.yy.ip.service.impl;

import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.constant.CommonConstant;
import com.io.yy.ip.entity.IpAddress;
import com.io.yy.ip.mapper.IpAddressMapper;
import com.io.yy.ip.service.IpAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * IP地址 服务实现类
 *
 * @author kris
 * @since 2020-03-25
 */
@Slf4j
@Service
public class IpAddressServiceImpl extends BaseServiceImpl<IpAddressMapper, IpAddress> implements IpAddressService {

    @Autowired
    private IpAddressMapper ipAddressMapper;


    @Override
    public IpAddress getByIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        if (CommonConstant.LOCALHOST_IP.equals(ip)) {
            return new IpAddress().setArea(CommonConstant.LOCALHOST_IP_NAME);
        }
        if (CommonConstant.LAN_IP.equals(ip)) {
            return new IpAddress().setArea(CommonConstant.LAN_IP_NAME);
        }
        return ipAddressMapper.getByIp(ip);
    }

    @Override
    public String getAreaByIp(String ip) {
        IpAddress ipAddress = getByIp(ip);
        if (ipAddress != null) {
            return ipAddress.getArea();
        }
        return null;
    }

    @Override
    public String getOperatorByIp(String ip) {
        IpAddress ipAddress = getByIp(ip);
        if (ipAddress != null) {
            return ipAddress.getOperator();
        }
        return null;
    }
}
