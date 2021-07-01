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
package com.io.yy.config;

import com.io.yy.core.properties.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * spring-boot-plus配置
 *
 * @author kris
 * @date 2019/8/4
 * @since 1.2.0-RELEASE
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
        WhyySystemProperties.class,
        WhyySystemCorsProperties.class,
        WhyySystemFilterProperties.class,
        WhyySystemInterceptorProperties.class,
        WhyySystemAopProperties.class
})
public class WhyySystemConfig {

}
