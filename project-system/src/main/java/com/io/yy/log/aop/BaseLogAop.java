package com.io.yy.log.aop;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.io.yy.common.api.ApiCode;
import com.io.yy.common.api.ApiResult;
import com.io.yy.common.exception.WhyySystemException;
import com.io.yy.constant.CommonConstant;
import com.io.yy.ip.entity.IpAddress;
import com.io.yy.log.annotation.Module;
import com.io.yy.log.annotation.OperationLog;
import com.io.yy.log.annotation.OperationLogIgnore;
import com.io.yy.log.bean.OperationLogInfo;
import com.io.yy.log.bean.RequestInfo;
import com.io.yy.log.entity.SysLoginLog;
import com.io.yy.log.entity.SysOperationLog;
import com.io.yy.log.service.SysOperationLogService;
import com.io.yy.ip.service.IpAddressService;
import com.io.yy.log.service.SysLoginLogService;
import com.io.yy.core.properties.WhyySystemAopProperties;
import com.io.yy.shiro.service.LoginToken;
import com.io.yy.shiro.service.LoginUsername;
import com.io.yy.shiro.util.JwtTokenUtil;
import com.io.yy.shiro.util.JwtUtil;
import com.io.yy.shiro.util.LoginUtil;
import com.io.yy.shiro.vo.ClientInfo;
import com.io.yy.util.*;
import com.io.yy.util.lang.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.fusesource.jansi.Ansi;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>
 * Controller Aop ?????????
 * ????????????????????????
 * <p>
 * ?????????????????????print-type
 * 1. ????????????????????????????????????????????????
 * 2. ThreadLocal????????????????????????????????????????????????????????????????????????
 * 3. ThreadLocal????????????????????????????????????????????????????????????????????????
 * </p>
 *
 * @author kris
 * @date 2018-11-08
 */
@Slf4j
public abstract class BaseLogAop {

    @Autowired
    protected SysOperationLogService sysOperationLogService;

    @Autowired
    private IpAddressService ipAddressService;

    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * ???????????????????????????????????????????????????????????????
     */
    protected static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    protected static ThreadLocal<RequestInfo> requestInfoThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<OperationLogInfo> operationLogThreadLocal = new ThreadLocal<>();

    /**
     * ??????ID
     */
    private static final String REQUEST_ID = "requestId";
    /**
     * ???
     */
    private static final int ZERO = 0;
    /**
     * ??????????????????????????????
     */
    private static final int MAX_LENGTH = 300;
    /**
     * ???????????????????????????
     */
    private static final int LOGIN_TYPE = 1;
    /**
     * ???????????????????????????
     */
    private static final int LOGOUT_TYPE = 2;

    /**
     * ?????????????????????
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * Aop????????????
     */
    protected WhyySystemAopProperties.LogAopConfig logAopConfig;

    /**
     * ??????????????????
     */
    protected WhyySystemAopProperties.LogPrintType logPrintType;

    /**
     * ??????????????????ID
     */
    protected boolean enableRequestId;

    /**
     * requestId????????????
     */
    protected WhyySystemAopProperties.RequestIdType requestIdType;

    /**
     * Aop??????????????????
     */
    protected WhyySystemAopProperties.OperationLogConfig operationLogConfig;

    /**
     * Aop??????????????????
     */
    protected WhyySystemAopProperties.LoginLogConfig loginLogConfig;

    @Autowired
    public void setWhyySystemAopProperties(WhyySystemAopProperties whyySystemAopProperties) {
        logAopConfig = whyySystemAopProperties.getLog();
        logPrintType = logAopConfig.getLogPrintType();
        enableRequestId = logAopConfig.isEnableRequestId();
        requestIdType = logAopConfig.getRequestIdType();
        operationLogConfig = whyySystemAopProperties.getOperationLog();
        loginLogConfig = whyySystemAopProperties.getLoginLog();
        log.debug("logAopConfig = " + logAopConfig);
        log.debug("logPrintType = " + logPrintType);
        log.debug("enableRequestId = " + enableRequestId);
        log.debug("requestIdType = " + requestIdType);
        log.debug("operationLogConfig = " + operationLogConfig);
        log.debug("loginLogConfig = " + loginLogConfig);
        log.debug("contextPath = " + contextPath);
    }

    /**
     * ????????????
     * ???????????????????????????????????????
     * ???????????????????????????????????????
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public abstract Object doAround(ProceedingJoinPoint joinPoint) throws Throwable;

    /**
     * ??????????????????
     *
     * @param joinPoint
     * @param exception
     */
    public abstract void afterThrowing(JoinPoint joinPoint, Exception exception);

    /**
     * ????????????ID
     *
     * @param requestInfo
     */
    protected abstract void setRequestId(RequestInfo requestInfo);

    /**
     * ????????????????????????
     *
     * @param requestInfo
     */
    protected abstract void getRequestInfo(RequestInfo requestInfo);

    /**
     * ????????????????????????
     */
    protected abstract void getResponseResult(Object result);

    /**
     * ?????????????????????????????????????????????
     *
     * @param requestInfo
     * @param operationLogInfo
     * @param result
     * @param exception
     */
    protected abstract void finish(RequestInfo requestInfo, OperationLogInfo operationLogInfo, Object result, Exception exception);

    /**
     * ??????
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        // ????????????????????????
        try {
            // ???????????????HttpServletRequest??????
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // HTTP??????????????????
            RequestInfo requestInfo = new RequestInfo();

            // ???????????? /api/foobar/add
            String path = request.getRequestURI();
            requestInfo.setPath(path);
            // ?????????????????? /foobar/add
            String realPath = getRealPath(path);
            requestInfo.setRealPath(realPath);

            // ????????????
            String[] excludePaths = logAopConfig.getExcludePaths();
            // ????????????
            if (handleExcludePaths(excludePaths, realPath)) {
                return joinPoint.proceed();
            }

            // ?????????????????????????????????
            Signature signature = joinPoint.getSignature();

            // ???????????????????????????
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();

            // ????????????????????????
            handleOperationLogInfo(method);

            // IP??????
            String ip = IpUtil.getRequestIp();
            requestInfo.setIp(ip);

            // ??????????????????
            String requestMethod = request.getMethod();
            requestInfo.setRequestMethod(requestMethod);

            // ????????????????????????
            String contentType = request.getContentType();
            requestInfo.setContentType(contentType);

            // ???????????????????????????????????????RequestBody??????
            Annotation[][] annotations = method.getParameterAnnotations();
            boolean isRequestBody = isRequestBody(annotations);
            requestInfo.setRequestBody(isRequestBody);

            AnnotatedType[] annotatedTypes = method.getAnnotatedParameterTypes();

            // ??????Shiro????????????????????????map???
            handleShiroAnnotationValue(requestInfo, method);

            // ??????????????????
            Object requestParamObject = getRequestParamObject(joinPoint, request, requestMethod, contentType, isRequestBody);
            requestInfo.setParam(requestParamObject);
            requestInfo.setTime(DateUtils.getDate());

            // ???????????????token
            String token = request.getHeader(JwtTokenUtil.getTokenName());
            requestInfo.setToken(token);
            if (StringUtils.isNotBlank(token)) {
                requestInfo.setTokenMd5(DigestUtils.md5Hex(token));
            }

            // ??????????????????????????????
            requestInfo.setUserAgent(request.getHeader(CommonConstant.USER_AGENT));

            // ????????????ID
            setRequestId(requestInfo);

            // ?????????????????????????????????????????????????????????
            getRequestInfo(requestInfo);
        } catch (Exception e) {
            log.error("????????????AOP????????????", e);
        }

        // ??????????????????,???????????????
        // ????????????????????????????????????@AfterThrowing??????????????????????????????????????????????????????????????????
        Object result = joinPoint.proceed();
        try {
            // ?????????????????????????????????????????????????????????
            getResponseResult(result);
        } catch (Exception e) {
            log.error("????????????????????????", e);
        } finally {
            handleAfterReturn(result, null);
        }
        return result;
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param result
     * @param exception
     */
    protected void handleAfterReturn(Object result, Exception exception) {
        // ??????RequestInfo
        RequestInfo requestInfo = requestInfoThreadLocal.get();
        // ??????OperationLogInfo
        OperationLogInfo operationLogInfo = operationLogThreadLocal.get();
        // ??????????????????????????????????????????????????????????????????????????????????????????saveSysOperationLog
        finish(requestInfo, operationLogInfo, result, null);
        // ????????????
        remove();
    }

    /**
     * ????????????
     *
     * @param exception
     */
    public void handleAfterThrowing(Exception exception) {
        // ??????RequestInfo
        RequestInfo requestInfo = requestInfoThreadLocal.get();
        // ??????OperationLogInfo
        OperationLogInfo operationLogInfo = operationLogThreadLocal.get();
        // ??????????????????????????????????????????????????????????????????????????????????????????saveSysOperationLog
        finish(requestInfo, operationLogInfo, null, exception);
        // ????????????
        remove();
    }


    private void handleOperationLogInfo(Method method) {
        // ???????????????????????????????????????
        OperationLogInfo operationLogInfo = new OperationLogInfo()
                .setControllerClassName(method.getDeclaringClass().getName())
                .setControllerMethodName(method.getName());

        // ??????Module?????????
        Class<?> controllerClass = method.getDeclaringClass();
        Module module = controllerClass.getAnnotation(Module.class);
        if (module != null) {
            String moduleName = module.name();
            String moduleValue = module.value();
            if (StringUtils.isNotBlank(moduleValue)) {
                operationLogInfo.setModule(moduleValue);
            }
            if (StringUtils.isNotBlank(moduleName)) {
                operationLogInfo.setModule(moduleName);
            }
        }
        // ??????OperationLogIgnore??????
        OperationLogIgnore classOperationLogIgnore = controllerClass.getAnnotation(OperationLogIgnore.class);
        if (classOperationLogIgnore != null) {
            // ???????????????
            operationLogInfo.setIgnore(true);
        }
        // ???????????????????????????
        OperationLogIgnore operationLogIgnore = method.getAnnotation(OperationLogIgnore.class);
        if (operationLogIgnore != null) {
            operationLogInfo.setIgnore(true);
        }
        // ??????????????????OperationLog??????
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        if (operationLog != null) {
            String operationLogName = operationLog.name();
            String operationLogValue = operationLog.value();
            if (StringUtils.isNotBlank(operationLogValue)) {
                operationLogInfo.setName(operationLogValue);
            }
            if (StringUtils.isNotBlank(operationLogName)) {
                operationLogInfo.setName(operationLogName);
            }
            operationLogInfo.setType(operationLog.type().getCode()).setRemark(operationLog.remark());
        }
        operationLogThreadLocal.set(operationLogInfo);
    }

    /**
     * ??????Shiro????????????????????????map???
     *
     * @param requestInfo
     * @param method
     */
    protected void handleShiroAnnotationValue(RequestInfo requestInfo, Method method) {
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null) {
            String[] requiresRolesValues = requiresRoles.value();
            if (ArrayUtils.isNotEmpty(requiresRolesValues)) {
                String requiresRolesString = Arrays.toString(requiresRolesValues);
                requestInfo.setRequiresRoles(requiresRolesString);
            }
        }

        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null) {
            String[] requiresPermissionsValues = requiresPermissions.value();
            if (ArrayUtils.isNotEmpty(requiresPermissionsValues)) {
                String requiresPermissionsString = Arrays.toString(requiresPermissionsValues);
                requestInfo.setRequiresPermissions(requiresPermissionsString);
            }
        }

        RequiresAuthentication requiresAuthentication = method.getAnnotation(RequiresAuthentication.class);
        if (requiresAuthentication != null) {
            requestInfo.setRequiresAuthentication(true);
        }

        RequiresUser requiresUser = method.getAnnotation(RequiresUser.class);
        if (requiresUser != null) {
            requestInfo.setRequiresUser(true);
        }

        RequiresGuest requiresGuest = method.getAnnotation(RequiresGuest.class);
        if (requiresGuest != null) {
            requestInfo.setRequiresGuest(true);
        }

    }

    /**
     * ????????????ID
     *
     * @param requestInfo
     */
    protected void handleRequestId(RequestInfo requestInfo) {
        if (!enableRequestId) {
            return;
        }
        String requestId = null;
        if (WhyySystemAopProperties.RequestIdType.IDWORK == requestIdType) {
            requestId = IdWorker.getIdStr();
        } else if (WhyySystemAopProperties.RequestIdType.UUID == requestIdType) {
            requestId = UUIDUtil.getUUID();
        }
        // ????????????ID
        MDC.put(REQUEST_ID, requestId);
        requestInfo.setRequestId(requestId);
    }

    /**
     * ??????????????????
     *
     * @param requestInfo
     */
    protected void handleRequestInfo(RequestInfo requestInfo) {
        requestInfoThreadLocal.set(requestInfo);
        if (WhyySystemAopProperties.LogPrintType.NONE == logPrintType) {
            return;
        }
        // ??????????????????
        String requestInfoString = formatRequestInfo(requestInfo);
        // ????????????????????????????????????????????????????????????????????????threadLocal???
        if (WhyySystemAopProperties.LogPrintType.ORDER == logPrintType) {
            printRequestInfoString(requestInfoString);
        } else {
            threadLocal.set(requestInfoString);
        }
    }

    /**
     * ??????????????????
     *
     * @param result
     */
    protected void handleResponseResult(Object result) {
        if (WhyySystemAopProperties.LogPrintType.NONE == logPrintType) {
            return;
        }
        if (result != null && result instanceof ApiResult) {
            ApiResult<?> apiResult = (ApiResult<?>) result;
            int code = apiResult.getCode();
            // ?????????????????????????????????
            String responseResultString = formatResponseResult(apiResult);
            if (WhyySystemAopProperties.LogPrintType.ORDER == logPrintType) {
                printResponseResult(code, responseResultString);
            } else {
                // ???threadLocal???????????????????????????
                String requestInfoString = threadLocal.get();
                // ????????????????????????????????????????????????????????????????????????
                if (WhyySystemAopProperties.LogPrintType.LINE == logPrintType) {
                    printRequestInfoString(requestInfoString);
                    printResponseResult(code, responseResultString);
                } else if (WhyySystemAopProperties.LogPrintType.MERGE == logPrintType) {
                    printRequestResponseString(code, requestInfoString, responseResultString);
                }
            }
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param code
     * @param requestInfoString
     * @param responseResultString
     */
    protected void printRequestResponseString(int code, String requestInfoString, String responseResultString) {
        if (code == ApiCode.SUCCESS.getCode()) {
            log.info(requestInfoString + "\n" + responseResultString);
        } else {
            log.error(requestInfoString + "\n" + responseResultString);
        }
    }


    /**
     * ?????????????????????
     *
     * @param requestInfo
     * @return
     */
    protected String formatRequestInfo(RequestInfo requestInfo) {
        String requestInfoString = null;
        try {
            if (logAopConfig.isRequestLogFormat()) {
                requestInfoString = "\n" + Jackson.toJsonStringNonNull(requestInfo, true);
            } else {
                requestInfoString = Jackson.toJsonStringNonNull(requestInfo);
            }
        } catch (Exception e) {
            log.error("?????????????????????????????????", e);
        }
        return AnsiUtil.getAnsi(Ansi.Color.GREEN, "requestInfo:" + requestInfoString);
    }

    /**
     * ??????????????????
     *
     * @param requestInfoString
     */
    protected void printRequestInfoString(String requestInfoString) {
        log.info(requestInfoString);
    }

    /**
     * ?????????????????????
     *
     * @param apiResult
     * @return
     */
    protected String formatResponseResult(ApiResult<?> apiResult) {
        String responseResultString = "responseResult:";
        try {
            if (logAopConfig.isResponseLogFormat()) {
                responseResultString += "\n" + Jackson.toJsonString(apiResult, true);
            } else {
                responseResultString += Jackson.toJsonString(apiResult);
            }
            int code = apiResult.getCode();
            if (code == ApiCode.SUCCESS.getCode()) {
                return AnsiUtil.getAnsi(Ansi.Color.BLUE, responseResultString);
            } else {
                return AnsiUtil.getAnsi(Ansi.Color.RED, responseResultString);
            }
        } catch (Exception e) {
            log.error("???????????????????????????", e);
        }
        return responseResultString;
    }

    /**
     * ??????????????????
     *
     * @param code
     * @param responseResultString
     */
    protected void printResponseResult(int code, String responseResultString) {
        if (code == ApiCode.SUCCESS.getCode()) {
            log.info(responseResultString);
        } else {
            log.error(responseResultString);
        }
    }

    /**
     * ??????????????????JSON?????????
     *
     * @param joinPoint
     * @param request
     * @param requestMethod
     * @param contentType
     * @param isRequestBody
     */
    protected Object getRequestParamObject(ProceedingJoinPoint joinPoint, HttpServletRequest request, String requestMethod, String contentType, boolean isRequestBody) {
        Object paramObject = null;
        if (isRequestBody) {
            // POST,application/json,RequestBody?????????,????????????,??????????????????JSON?????????
            Object[] args = joinPoint.getArgs();
            paramObject = getArgsObject(args);
        } else {
            // ??????getParameterMap???????????????,?????????????????????JSON?????????
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getParamJSONObject(paramsMap);
        }
        return paramObject;
    }

    /**
     * ???????????????????????????????????????RequestBody??????
     *
     * @param annotations
     * @return
     */
    protected boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }

    /**
     * ??????????????????
     *
     * @param args
     * @return
     */
    protected Object getArgsObject(Object[] args) {
        if (args == null) {
            return null;
        }
        // ??????HttpServletRequest???HttpServletResponse
        List<Object> realArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                continue;
            }
            if (arg instanceof HttpServletResponse) {
                continue;
            }
            if (arg instanceof MultipartFile) {
                continue;
            }
            if (arg instanceof ModelAndView) {
                continue;
            }
            realArgs.add(arg);
        }
        if (realArgs.size() == 1) {
            return realArgs.get(0);
        } else {
            return realArgs;
        }
    }


    /**
     * ????????????Map???JSON?????????
     *
     * @param paramsMap
     * @return
     */
    protected JSONObject getParamJSONObject(Map<String, String[]> paramsMap) {
        if (MapUtils.isEmpty(paramsMap)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            // ?????????
            if (values == null) {
                jsonObject.put(key, null);
            } else if (values.length == 1) {
                // ?????????
                jsonObject.put(key, values[0]);
            } else {
                // ?????????
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }

    /**
     * ?????????????????????????????????true???????????????false
     *
     * @param excludePaths ????????????
     * @param realPath     ??????????????????
     * @return
     */
    protected boolean handleExcludePaths(String[] excludePaths, String realPath) {
        if (ArrayUtils.isEmpty(excludePaths) || StringUtils.isBlank(realPath)) {
            return false;
        }
        // ?????????????????????????????????,???????????????????????????
        PathMatcher pathMatcher = new AntPathMatcher();
        if (ArrayUtils.isNotEmpty(excludePaths)) {
            for (String pattern : excludePaths) {
                if (pathMatcher.match(pattern, realPath)) {
                    return true;
                }
            }
        }
//        if (excludePaths.contains(realPath)) {
//            return true;
//        }
        return false;
    }

    /**
     * ??????????????????
     *
     * @param requestPath
     * @return
     */
    private String getRealPath(String requestPath) {
        // ??????????????????????????????????????????????????????????????????????????????
        if (StringUtils.isNotBlank(contextPath)) {
            return requestPath.substring(contextPath.length());
        }
        return requestPath;
    }

    /**
     * ??????????????????????????????
     *
     * @param requestInfo
     * @param operationLogInfo
     * @param result
     * @param exception
     */
    @Async
    protected void saveSysOperationLog(RequestInfo requestInfo, OperationLogInfo operationLogInfo, Object result, Exception exception) {
        try {
            // ???????????????????????????????????????
            if (!operationLogConfig.isEnable()) {
                return;
            }
            // ????????????
            String[] excludePaths = operationLogConfig.getExcludePaths();
            // ????????????
            if (handleExcludePaths(excludePaths, requestInfo.getRealPath())) {
                return;
            }

            // ????????????
            SysOperationLog sysOperationLog = new SysOperationLog();
            // ????????????????????????
            if (operationLogInfo != null) {
                // ??????????????????????????????OperationLogIgnore????????????
                if (operationLogInfo.isIgnore()) {
                    return;
                }
                sysOperationLog.setModule(operationLogInfo.getModule())
                        .setName(operationLogInfo.getName())
                        .setType(operationLogInfo.getType())
                        .setRemark(operationLogInfo.getRemark())
                        .setClassName(operationLogInfo.getControllerClassName())
                        .setMethodName(operationLogInfo.getControllerMethodName());
            }
            // ????????????????????????
            if (requestInfo != null) {
                sysOperationLog.setIp(requestInfo.getIp())
                        .setPath(requestInfo.getPath())
                        .setRequestId(requestInfo.getRequestId())
                        .setRequestMethod(requestInfo.getRequestMethod())
                        .setContentType(requestInfo.getContentType())
                        .setRequestBody(requestInfo.getRequestBody())
                        .setToken(requestInfo.getTokenMd5());

                // ?????????????????????
                sysOperationLog.setParam(Jackson.toJsonStringNonNull(requestInfo.getParam()));
                // User-Agent
                ClientInfo clientInfo = ClientInfoUtil.get(requestInfo.getUserAgent());
                if (clientInfo != null) {
                    sysOperationLog.setBrowserName(clientInfo.getBrowserName())
                            .setBrowserVersion(clientInfo.getBrowserversion())
                            .setEngineName(clientInfo.getEngineName())
                            .setEngineVersion(clientInfo.getEngineVersion())
                            .setOsName(clientInfo.getOsName())
                            .setPlatformName(clientInfo.getPlatformName())
                            .setMobile(clientInfo.isMobile())
                            .setDeviceName(clientInfo.getDeviceName())
                            .setDeviceModel(clientInfo.getDeviceModel());
                }
                // ??????IP??????
                IpAddress ipAddress = ipAddressService.getByIp(requestInfo.getIp());
                if (ipAddress != null) {
                    requestInfo.setIpAddress(ipAddress);
                    sysOperationLog.setArea(ipAddress.getArea()).setOperator(ipAddress.getOperator());
                }
            }

            // ??????????????????
            if (result != null && result instanceof ApiResult) {
                ApiResult<?> apiResult = (ApiResult<?>) result;
                apiResult.getCode();
                sysOperationLog.setSuccess(apiResult.isSuccess())
                        .setCode(apiResult.getCode())
                        .setMessage(apiResult.getMessage());
            }

            // ????????????????????????
            sysOperationLog.setUserId(LoginUtil.getUserId()).setUserName(LoginUtil.getUsername());

            // ??????????????????
            if (exception != null) {
                Integer errorCode = null;
                String exceptionMessage = exception.getMessage();
                if (StringUtils.isNotBlank(exceptionMessage)) {
                    exceptionMessage = StringUtils.substring(exceptionMessage, ZERO, MAX_LENGTH);
                }
                if (exception instanceof WhyySystemException) {
                    WhyySystemException whyySystemException = (WhyySystemException) exception;
                    errorCode = whyySystemException.getErrorCode();
                }
                // ???????????????????????????
                sysOperationLog.setSuccess(false)
                        .setCode(errorCode)
                        .setExceptionMessage(exceptionMessage)
                        .setExceptionName(exception.getClass().getName());
            }
            // ????????????????????????
            sysOperationLogService.saveSysOperationLog(sysOperationLog);


        } catch (Exception e) {
            if (e instanceof JWTDecodeException) {
                JWTDecodeException jwtDecodeException = (JWTDecodeException) e;
                throw jwtDecodeException;
            }
            log.error("??????????????????????????????", e);
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param requestInfo
     * @param operationLogInfo
     * @param result
     * @param exception
     */
    @Async
    protected void saveSysLoginLog(RequestInfo requestInfo, OperationLogInfo operationLogInfo, Object result, Exception exception) {
        try {
            // ???????????????????????????????????????
            if (!loginLogConfig.isEnable()) {
                return;
            }
            String realPath = requestInfo.getRealPath();
//            log.info("--saveSysLoginLog--"+realPath);
            if (StringUtils.isBlank(realPath)) {
                return;
            }

            boolean flag = false;
            Integer type = null;
            // ???????????????????????????
            if (loginLogConfig.getLoginPath().contains(realPath)) {
                flag = true;
                type = LOGIN_TYPE;
            } else if (realPath.equals(loginLogConfig.getLogoutPath())) {
                flag = true;
                type = LOGOUT_TYPE;
            }

            // ????????????????????????
            if (flag) {
                SysLoginLog sysLoginLog = new SysLoginLog();
                sysLoginLog.setType(type);
                // ??????????????????
                if (exception != null) {
                    Integer errorCode = null;
                    String exceptionMessage = exception.getMessage();
                    if (StringUtils.isNotBlank(exceptionMessage)) {
                        exceptionMessage = StringUtils.substring(exceptionMessage, ZERO, MAX_LENGTH);
                    }
                    if (exception instanceof WhyySystemException) {
                        WhyySystemException whyySystemException = (WhyySystemException) exception;
                        errorCode = whyySystemException.getErrorCode();
                    }
                    // ???????????????????????????
                    sysLoginLog.setCode(errorCode).setExceptionMessage(exceptionMessage);
                }

                // ????????????????????????
                if (result != null && result instanceof ApiResult) {
                    ApiResult<?> apiResult = (ApiResult<?>) result;
                    sysLoginLog.setSuccess(apiResult.isSuccess()).setCode(apiResult.getCode());
                    if (apiResult.isSuccess()) {
                        if (LOGIN_TYPE == type) {
                            Object object = apiResult.getData();
                            if (object != null && object instanceof LoginToken) {
                                LoginToken loginToken = (LoginToken) object;
                                String token = loginToken.getToken();
                                if (StringUtils.isNotBlank(token)) {
                                    // ????????????token
                                    String tokenMd5 = DigestUtils.md5Hex(token);
                                    sysLoginLog.setToken(tokenMd5);
                                }
                            }
                        }
                    } else {
                        sysLoginLog.setExceptionMessage(apiResult.getMessage());
                    }
                }

                // ????????????????????????
                if (requestInfo != null) {
                    sysLoginLog.setIp(requestInfo.getIp()).setRequestId(requestInfo.getRequestId());
                    // ?????????????????????
                    if (LOGIN_TYPE == type) {
                        Object paramObject = requestInfo.getParam();
                        if (paramObject != null && paramObject instanceof LoginUsername) {
                            LoginUsername loginUsername = (LoginUsername) paramObject;
                            String username = loginUsername.getUsername();
                            sysLoginLog.setUsername(username);
                        }
                    } else if (LOGOUT_TYPE == type) {
                        String username = JwtUtil.getUsername(requestInfo.getToken());
                        sysLoginLog.setUsername(username);
                        // ????????????token
                        sysLoginLog.setToken(requestInfo.getTokenMd5());
                    }

                    // User-Agent
                    String userAgent = requestInfo.getUserAgent();
                    if (StringUtils.isNotBlank(userAgent)) {
                        sysLoginLog.setUserAgent(StringUtils.substring(userAgent, ZERO, MAX_LENGTH));
                    }
                    ClientInfo clientInfo = ClientInfoUtil.get(userAgent);
                    if (clientInfo != null) {
                        sysLoginLog.setBrowserName(clientInfo.getBrowserName())
                                .setBrowserVersion(clientInfo.getBrowserversion())
                                .setEngineName(clientInfo.getEngineName())
                                .setEngineVersion(clientInfo.getEngineVersion())
                                .setOsName(clientInfo.getOsName())
                                .setPlatformName(clientInfo.getPlatformName())
                                .setMobile(clientInfo.isMobile())
                                .setDeviceName(clientInfo.getDeviceName())
                                .setDeviceModel(clientInfo.getDeviceModel());
                    }
                    IpAddress ipAddress = requestInfo.getIpAddress();
                    if (ipAddress == null) {
                        ipAddress = ipAddressService.getByIp(requestInfo.getIp());
                    }
                    if (ipAddress != null) {
                        sysLoginLog.setArea(ipAddress.getArea()).setOperator(ipAddress.getOperator());
                    }
                    // ??????????????????
                    sysLoginLogService.saveSysLoginLog(sysLoginLog);
                }
            }
        } catch (Exception e) {
            log.error("??????????????????????????????", e);
        }
    }


    /**
     * ????????????
     */
    protected void remove() {
        threadLocal.remove();
        requestInfoThreadLocal.remove();
        operationLogThreadLocal.remove();
        MDC.clear();
    }

}
