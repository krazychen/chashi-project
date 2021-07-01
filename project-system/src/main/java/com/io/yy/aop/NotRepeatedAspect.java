package com.io.yy.aop;

import cn.hutool.core.util.StrUtil;
import com.io.yy.common.api.ApiResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/7/1
 **/
@Aspect
@Component
@ComponentScan
@EnableAspectJAutoProxy
public class NotRepeatedAspect {

    //此处注释，更换为redis，做分布式部署时避免失效
    //public static final Set<String> KEY = new ConcurrentSkipListSet<>();
    @Autowired
    private RedisTemplate redisTemplate;

    //切入点
    @Pointcut("@annotation(com.io.yy.common.constraints.NotRepeated)")
    public void duplicate() {
    }

    /**
     * 对方法拦截后进行参数验证，判断是否重复提交
     *
     * @param joinPoint
     * @return
     */
    @Around("duplicate()")
    public Object duplicate(ProceedingJoinPoint joinPoint) throws Throwable {
        //Signature 封装方法相关的信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法上");
        }
        methodSignature = (MethodSignature) signature;
        //获取方法
        Method method = methodSignature.getMethod();
        StringBuilder builder = new StringBuilder(method.toString());
        //获取方法参数
        Object[] args = joinPoint.getArgs();
        for (Object object : args) {
            if (object != null) {
                builder.append(object.getClass().toString());
                String str = object.toString();
                str = str.replaceAll("state=0","state=1");
                builder.append(str);
            }
        }
        String sign = builder.toString();
        //返回true说明该请求不是重复提交，false表示方法还在执行中
        String oldLock = (String) redisTemplate.opsForValue().getAndSet(sign, "1");
        //boolean success = KEY.add(sign);
        if (StrUtil.isNotBlank(oldLock) && oldLock.equals("1")) {
            return ApiResult.ok(false, "休息一下再点击");
        }
        try {
            return joinPoint.proceed();
        } finally {
            redisTemplate.delete(sign);
        }

    }
}
