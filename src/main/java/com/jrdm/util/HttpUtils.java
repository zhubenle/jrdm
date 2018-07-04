package com.jrdm.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

/**
 * <br/>
 * Created on 2017/12/5 12:10.
 *
 * @author zhubenle
 */
public class HttpUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private final static String CHARSET = "UTF-8";
    private final static Integer STATUS_CODE_200 = 200;

    private final static String UNKNOWN = "unknown";

    private HttpUtils() {
    }

    /**
     * 发送form表单post请求
     *
     * @param url
     *         {@link String} 请求url
     * @param parameters
     *         {@link NameValuePair} 请求form参数
     * @param header
     *         {@link Map} 请求头
     *
     * @return {@link String}
     */
    public static String sendPost(String url, Map<String, String> parameters, Map<String, String> header) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);
            if (header != null) {
                for (Entry<String, String> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            if (parameters != null) {
                for (Entry<String, String> entry : parameters.entrySet()) {
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, CHARSET));
            response = client.execute(httpPost);
            Integer statusCode = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), CHARSET);
            LOGGER.info("POST请求地址-->{}, 返回状态码-->{}, 返回结果: {}", url, statusCode, result);
            if (STATUS_CODE_200.equals(statusCode)) {
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("POST请求地址{} 异常", url, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送entity参数post请求
     *
     * @param url
     *         {@link String} 请求url
     * @param parameter
     *         {@link String} 请求参数
     * @param header
     *         {@link Map} 请求头
     *
     * @return {@link String}
     */
    public static String sendPost(String url, String parameter, Map<String, Object> header) {
        CloseableHttpClient client;
        CloseableHttpResponse response = null;
        try {
            client = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(url);

            if (header != null) {
                for (Entry<String, Object> entry : header.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            httpPost.setEntity(new StringEntity(parameter, CHARSET));
            response = client.execute(httpPost);
            Integer statusCode = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), CHARSET);
            LOGGER.info("POST请求地址-->{}, 返回状态码-->{}, 返回结果: {}", url, statusCode, result);
            if (STATUS_CODE_200.equals(statusCode)) {
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("POST请求地址{} 异常", url, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("发送post请求后，关闭response异常", e);
            }
        }
        return null;
    }

    /**
     * 发送get请求
     *
     * @param url
     *         {@link String} 请求url
     * @param header
     *         {@link Map} 请求头
     *
     * @return {@link String}
     */
    public static String sendGet(String url, Map<String, Object> header) {
        CloseableHttpClient client;
        CloseableHttpResponse response = null;
        try {
            client = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(url);
            if (header != null) {
                for (Entry<String, Object> entry : header.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            response = client.execute(httpGet);
            Integer statusCode = response.getStatusLine().getStatusCode();
            String result = EntityUtils.toString(response.getEntity(), CHARSET);
            LOGGER.info("GET请求地址-->{}, 返回状态码-->{}, 返回结果: {}", url, statusCode, result);
            if (STATUS_CODE_200.equals(statusCode)) {
                return result;
            }
        } catch (Exception e) {
            LOGGER.error("GET请求地址{} 异常", url, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取请求的IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 发送get请求
     *
     * @param url
     *         {@link String} 请求url
     *
     * @return {@link String}
     */
    public static String sendGet(String url) {
        return sendGet(url, null);
    }

    /**
     * 发送get请求
     *
     * @param url
     *         {@link String} 请求url
     *
     * @return {@link JSONObject}
     */
    public static JSONObject sendGetJSON(String url) {
        return convert2JSON(sendGet(url, null));
    }

    /**
     * 发送form表单post请求
     *
     * @param url
     *         {@link String} 请求url
     * @param parameters
     *         {@link NameValuePair}
     *
     * @return {@link String}
     */
    public static String sendPost(String url, Map<String, String> parameters) {
        return sendPost(url, parameters, null);
    }

    /**
     * 发送form表单post请求
     *
     * @param url
     *         {@link String} 请求url
     * @param parameters
     *         {@link NameValuePair}
     *
     * @return {@link JSONObject}
     */
    public static JSONObject sendPostJSON(String url, Map<String, String> parameters) {
        return convert2JSON(sendPost(url, parameters, null));
    }

    /**
     * 发送post请求
     *
     * @param url
     *         {@link String} 请求url
     * @param parameter
     *         {@link String} 请求参数
     *
     * @return {@link JSONObject}
     */
    public static JSONObject sendPostJSON(String url, String parameter) {
        return convert2JSON(sendPost(url, parameter, null));
    }

    /**
     * 字符串转换成JSONObject
     *
     * @param str
     *         {@link String}
     *
     * @return {@link JSONObject}
     */
    private static JSONObject convert2JSON(String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(Optional.ofNullable(str).orElse(null));
        } catch (Exception e) {
            LOGGER.error("{}转换JSONObject异常", str, e);
        }
        return jsonObject;
    }
}
