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

package com.io.yy.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.io.yy.common.param.OrderQueryParam;
import com.io.yy.common.param.QueryParam;
import com.io.yy.common.service.BaseService;
import com.io.yy.util.LambdaColumn;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author kris
 * @date 2019-10-24
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected Page setPageParam(QueryParam queryParam) {
        return setPageParam(queryParam,null);
    }

    protected Page setPageParam(QueryParam queryParam, OrderItem defaultOrder) {
        Page page = new Page();
        // 设置当前页码
        page.setCurrent(queryParam.getCurrent());
        // 设置页大小
        page.setSize(queryParam.getSize());
        /**
         * 如果是queryParam是OrderQueryParam，并且不为空，则使用前端排序
         * 否则使用默认排序
         */
        if (queryParam instanceof OrderQueryParam){
            OrderQueryParam orderQueryParam = (OrderQueryParam) queryParam;
            List<OrderItem> orderItems = orderQueryParam.getOrders();
            if (CollectionUtils.isEmpty(orderItems) && defaultOrder!=null){
                page.setOrders(Arrays.asList(defaultOrder));
            }else{
                page.setOrders(orderItems);
            }
        }else{
            if(defaultOrder!=null){
                page.setOrders(Arrays.asList(defaultOrder));
            }
        }

        return page;
    }

    /**
     * 实体类型
     */
    private Class<?> entityClass;

    {
        Class<?> clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) p[1];
        }
    }

    /**
     * 获取对应字段的数据表列名称
     *
     * @param func
     * @return
     */
    public String getLambdaColumn(SFunction<T, ?> func) {
        return new LambdaColumn<T>().get(func);
    }


}
