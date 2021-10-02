package com.io.yy.wxops.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

public class HttpUtil {
    public static final String GET = "get";
    public static final String POST = "post";
    public static final String CHARSET = "UTF-8";

    private static RequestConfig requestConfig;

    static {
        //设置http的状态参数
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
    }

    /**
     * GET 请求
     *
     * @param url 请求URL
     * @return
     */
    public static String doGet(String url) {
        return doSend(GET, url, null, CHARSET, false);
    }

    /**
     * GET 请求
     *
     * @param url    请求URL
     * @param params 请求参数
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        return doSend(GET, url, params, CHARSET, false);
    }

    /**
     * POST 请求
     *
     * @param url 请求URL
     * @return
     */
    public static String doPost(String url) {
        return doSend(POST, url, null, CHARSET, false);
    }

    /**
     * POST 请求
     *
     * @param url    请求URL
     * @param params 请求参数
     * @return
     */
    public static String doPost(String url, Map<String, Object> params) {
        return doSend(POST, url, params, CHARSET, false);
    }

    /**
     * https POST 请求
     *
     * @param url 请求URL
     * @return
     */
    public static String doPostSSL(String url) {
        return doSend(POST, url, null, CHARSET, true);
    }

    /**
     * https POST 请求
     *
     * @param url    请求URL
     * @param params 请求参数
     * @return
     */
    public static String doPostSSL(String url, Map<String, Object> params) {
        return doSend(POST, url, params, CHARSET, true);
    }

    /**
     * json 参数方式POST提交
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, JSONObject params) {
        return doSend(POST, url, params, CHARSET, false);
    }


    /**
     * HTTP请求
     *
     * @param method    请求方式("post","get")
     * @param url       请求的url地址
     * @param params    请求的参数
     * @param charset   编码格式
     * @param enableSSL 是否开启SSL
     * @return
     */
    public static String doSend(String method, String url, Map<String, Object> params, String charset, boolean enableSSL) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        CloseableHttpClient httpClient;
        CloseableHttpResponse response = null;
        if (enableSSL) { // https  注意这里获取https内容，使用了忽略证书的方式，当然还有其他的方式来获取https内容
            httpClient = HttpUtil.createSSLClientDefault();
        } else {
            httpClient = HttpClients.createDefault();
        }
        try {
            HttpUriRequest httpUriRequest = getRequestMethod(params, url, method);
//            httpUriRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
            response = httpClient.execute(httpUriRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpUriRequest.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, StringUtils.isEmpty(charset) ? CHARSET : charset);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null)
                    httpClient.close();
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    /**
     * 获取请求
     *
     * @param params 请求的参数
     * @param url    请求的URL
     * @param method 请求方式
     * @return
     */
    public static HttpUriRequest getRequestMethod(Object params, String url, String method) throws UnsupportedEncodingException {
        HttpUriRequest reqMethod = null;
        if (params.getClass() == Map.class) { // map参数
            // 创建参数队列
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (params != null && !((Map<String, Object>) params).isEmpty()) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) params).entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
            if (POST.equals(method)) {
                reqMethod = RequestBuilder.post().setUri(url)
                        .addParameters(nameValuePairs.toArray(new BasicNameValuePair[params == null ? 0 : ((Map<String, Object>) params).size()]))
                        .setConfig(requestConfig).build();
            } else if (GET.equals(method)) {
                reqMethod = RequestBuilder.get().setUri(url)
                        .addParameters(nameValuePairs.toArray(new BasicNameValuePair[params == null ? 0 : ((Map<String, Object>) params).size()]))
                        .setConfig(requestConfig).build();
            }
        } else if (params.getClass() == JSONObject.class) { // json参数
            StringEntity entity = new StringEntity(((JSONObject) params).toJSONString());
            entity.setContentEncoding(CHARSET);
//            entity.setContentType("application/json");//发送json数据需要设置contentType
            entity.setContentType("application/x-www-form-urlencoded");
            if (POST.equals(method)) {
                reqMethod = RequestBuilder.post().setUri(url)
                        .setEntity(entity)
                        .setConfig(requestConfig).build();
            } else if (GET.equals(method)) {
                reqMethod = RequestBuilder.get().setUri(url)
                        .setEntity(entity)
                        .setConfig(requestConfig).build();
            }
        }
        return reqMethod;
    }

    /**
     * 这里创建了忽略整数验证的CloseableHttpClient对象
     *
     * @return
     */
    private static CloseableHttpClient createSSLClientDefault() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    /**
     * Map 转 JSONObject
     *
     * @param map
     * @return
     */
    public static JSONObject toJsonObj(Map<String, String> map) {
        JSONObject resultJson = new JSONObject();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            resultJson.put(key, map.get(key));
        }
        return resultJson;
    }

    public static void main(String[] args) {
        String url = "https://api.ttlock.com/v3/lock/listKeyboardPwd";
        Map<String, String> map = new HashMap();
        map.put("clientId", "a2a4925348c84f55921331391bf9f800");
        map.put("accessToken", "13373d03617e6de7e73d0774cadb2d75");
        map.put("lockId", "3731369");
        map.put("pageNo", "1");
        map.put("pageSize", "100");
        map.put("date", "1633190596150");
        Map res = (Map) JSON.parse(HttpUtil.doPostSSL(url, HttpUtil.toJsonObj(map)));
        System.out.println(res);
    }
}