/*
 * Copyright 2019-2029 kris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.yy.core.properties;

import com.io.yy.config.WhyySystemWebMvcConfig;
import com.io.yy.shiro.config.ShiroProperties;
import com.io.yy.shiro.jwt.JwtProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * whyy-system属性配置信息
 *
 * @author kris
 * @date 2019-08-04
 * @since 1.2.0-RELEASE
 */
@Data
@ConfigurationProperties(prefix = "whyy-system")
public class WhyySystemProperties {

    /**
     * 是否启用ansi控制台输出有颜色的字体，local环境建议开启，服务器环境设置为false
     */
    private boolean enableAnsi;

    /**
     * 是否启用验证码
     */
    private boolean enableVerifyCode;

    /**
     * 是否学员扫描微信登陆
     */
    private boolean enableHwXueyuanWx;

    /**
     * 实现BaseEnum接口的枚举包
     */
    private String[] enumPackages;

    /**
     * 拦截器配置
     */
    @NestedConfigurationProperty
    private WhyySystemInterceptorProperties interceptor;

    /**
     * 过滤器配置
     */
    @NestedConfigurationProperty
    private WhyySystemFilterProperties filter;

    /**
     * 上传目录
     */
    private String uploadPath;
    /**
     * 资源访问路径，前端访问
     */
    private String resourceAccessPath;
    /**
     * 资源访问路径，后段配置，资源映射/拦截器使用
     */
    private String resourceAccessPatterns;
    /**
     * 资源访问全路径
     */
    private String resourceAccessUrl;

    /**
     * 允许上传的文件后缀集合
     */
    private List<String> allowUploadFileExtensions;
    /**
     * 允许下载的文件后缀集合
     */
    private List<String> allowDownloadFileExtensions;

    /**
     * 配置图片上传目录
     */
    private String configUploadPath;
    /**
     * 配置图片访问路径，前端访问
     */
    private String configAccessPath;
    /**
     * 配置图片访问路径，后段配置，资源映射/拦截器使用
     */
    private String configAccessPatterns;
    /**
     * 配置图片访问全路径
     */
    private String configAccessUrl;

    /**
     * 多租户过滤表
     */
    private String tentTables;

    /**
     * 多租户过滤sql
     */
    private String tentSqls;

    /**
     * JWT配置
     */
    @NestedConfigurationProperty
    private JwtProperties jwt;

    /**
     * Shiro配置
     */
    @NestedConfigurationProperty
    private ShiroProperties shiro = new ShiroProperties();

    /**
     * 项目静态资源访问配置
     *
     * @see WhyySystemWebMvcConfig addResourceHandlers
     */
    private String resourceHandlers;

    /**
     * 跨域配置
     */
    @NestedConfigurationProperty
    private WhyySystemCorsProperties cors = new WhyySystemCorsProperties();



}
