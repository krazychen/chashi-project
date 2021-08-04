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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.io.yy.core.handler.MyTenantHandler;
import com.io.yy.core.handler.MyTenantSqlParser;
import com.io.yy.shiro.util.LoginUtil;
import com.io.yy.shiro.vo.LoginSysUserRedisVo;
import com.io.yy.util.lang.StringUtils;
import com.io.yy.util.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     MybatisPlus配置
 * </p>
 * @author kris
 * @since 2018-11-08
 */
@Slf4j
@Configuration
public class MybatisPlusConfig {

//    /**
//     * mybatis-plus分页插件<br>
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }

    @Value("${whyy-system.tentTables}")
    private String tentTables;

    @Value("${whyy-system.tentSqls}")
    private String tentSqls;

    /**
     * 多租户属于 SQL 解析部分，依赖 MP 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /*
         * 【测试多租户】 SQL 解析处理拦截器<br>
         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
         */
        List<ISqlParser> sqlParserList = new ArrayList<>();
        MyTenantSqlParser tenantSqlParser = new MyTenantSqlParser();
//        TenantSqlParser tenantSqlParser = new TenantSqlParser();

        tenantSqlParser.setTenantHandler(new MyTenantHandler() {
            @Override
            public boolean doUserFilter() {
                /**
                 * 这个参数是当前线程变量中的用户信息
                 * 当用户信息没有租户ID（超管或者未登录），即不过滤该sql
                 */
                LoginSysUserRedisVo userInfo = LoginUtil.getLoginSysUserRedisVo();
                if(ObjectUtil.isNotNull(userInfo)) {
                    log.debug("yonghu xinx:" + userInfo.getIsSuperAdmin() + ":" + userInfo.getId());
                }else{
                    log.debug("yonghu xinx:" + userInfo);
                }
                if (ObjectUtil.isNotNull(userInfo) && ObjectUtil.isNotNull(userInfo.getOfficeCode()) &&ObjectUtil.isNotNull(userInfo.getIsSuperAdmin()) &&!userInfo.getIsSuperAdmin()) {
                    return true;
                }
                return false;
            }

            @Override
            public Expression getTenantId(boolean select) {
                // select since: 3.3.2，参数 true 表示为 select 下的 where 条件,false 表示 insert/update/delete 下的条件
                // 只有 select 下才允许多参(ValueListExpression),否则只支持单参
//                if (!select) {
//                    return new LongValue(1);
//                }
//                ValueListExpression expression = new ValueListExpression();
//                ExpressionList list = new ExpressionList(new LongValue(1), new LongValue(2));
//                expression.setExpressionList(list);
//                return expression;
                LoginSysUserRedisVo userInfo = LoginUtil.getLoginSysUserRedisVo();
                if (ObjectUtil.isNotNull(userInfo) && ObjectUtil.isNotNull(userInfo.getOfficeCode())) {
                    log.debug("corpcode"+ userInfo.getOfficeCode()+":"+userInfo);
                    return new StringValue(userInfo.getOfficeCode());
                }
                return null;
            }

            /**
             * 数据库各表中，租户ID字段名
             * @return
             */
            @Override
            public String getTenantIdColumn() {
                return "corp_code";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                /**
                 * 这里可以判断是否过滤表
                 * 表名根据实际去配置，凡是不带tent_id的表均应该配置，否则sql会报找不到tent_id这个字段
                 */
//                log.debug(tentTables);
                if (ObjectUtil.isNotNull(tentTables)) {
                    List<String> tables = Arrays.asList(tentTables.split(","));
                    log.debug("tentTables："+tentTables);
                    if (CollUtil.isNotEmpty(tables) && tables.contains(tableName)) {
                        return false;
                    }
                }
                return true;
            }
        });
        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
            @Override
            public boolean doFilter(MetaObject metaObject) {
                BoundSql boundSql = (BoundSql) metaObject.getValue("boundSql");
                String sql = boundSql.getSql();
                if(StringUtils.containsIgnoreCase(sql, "insert")){
                    log.debug("insert sql: "+ sql);
                    Object parameterObject = boundSql.getParameterObject();
                    Field corpCodeField = ReflectUtils.getAccessibleField(parameterObject,"corpCode");
                    if(ObjectUtil.isNotNull(corpCodeField)){
                        log.debug("insert sql corpcode: "+ corpCodeField.toString());
                        String corpCode = ReflectUtils.getFieldValue(parameterObject,"corpCode");
                        if(ObjectUtil.isNotNull(corpCode)||ObjectUtil.isNotEmpty(corpCode)){
                            return true;
                        }
                    }
                }
                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
                List<String> sqls = Arrays.asList(tentSqls.split(" "));
                if(CollUtil.isNotEmpty(sqls) && sqls.contains(ms.getId())){
//                if ("com.io.yy.homework.mapper.HwHomeworkAuditMapper.getRepeatNumber".equals(ms.getId())||
//                        "com.io.yy.homework.mapper.HwHomeworkAuditMapper.getRepeatNumberByClassId".equals(ms.getId())||
//                        "com.io.yy.homework.mapper.HwHomeworkAuditMapper.getAuditNumber".equals(ms.getId())||
//                        "com.io.yy.homework.mapper.HwHomeworkAuditMapper.getNumber".equals(ms.getId())||
//                        "com.io.yy.homework.mapper.HwHomeworkAuditMapper.getNumberByClassId".equals(ms.getId())||
//                        "com.io.yy.homework.mapper.HwHomeworkAuditMapper.getRepeatAuditNumber".equals(ms.getId())||
//                        "com.io.yy.system.mapper.SysUserMapper.getSysUserById".equals(ms.getId())||
//                        "com.io.yy.system.mapper.SysOfficeMapper.selectOne".equals(ms.getId())
//                ) {
                    log.debug("过滤sql："+ms.getId());
                    return true;
                }
                return false;
//                return StringUtils.containsIgnoreCase(sql, "insert")
//                        && StringUtils.containsIgnoreCase(sql, "corp_code");

//                MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
//                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
//                if ("com.baomidou.springboot.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
//                    return true;
//                }
//                return false;
            }
        });
        return paginationInterceptor;
    }

    /**
     * mybatios-plus乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
