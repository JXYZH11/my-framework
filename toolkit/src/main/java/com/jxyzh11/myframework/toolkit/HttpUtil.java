package com.jxyzh11.myframework.toolkit;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Http工具类
 */
@Slf4j
public class HttpUtil {

    public static String KEY_STATUS_CODE = "statusCode";
    public static String KEY_CONTENT = "content";

    //连接池管理器-全局唯一
    private final static PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager();

    //重试处理
    private final static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            // 如果超过最大重试计数，不要重试
            if (executionCount >= 5) {
                return false;
            }
            //当服务器端由于负载过大等情况发生时，可能会导致在收到请求后无法处理(比如没有足够的线程资源)，会直接丢弃链接而不进行处理
            if (exception instanceof NoHttpResponseException) {
                return false;
            }
            //IO操作由于线程运行被打断而终止
            if (exception instanceof InterruptedIOException) {
                return false;
            }
            //解析主机名错误
            if (exception instanceof UnknownHostException) {
                return false;
            }
            //连接超时
            if (exception instanceof ConnectTimeoutException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext
                    .adapt(context);
            HttpRequest request = clientContext.getRequest();

            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        }
    };

    //类加载的时候 设置最大连接数 和 每个路由的最大连接数
    static {
        poolConnManager.setMaxTotal(10);
        poolConnManager.setDefaultMaxPerRoute(10);
    }

    /**
     * 从poolConnManager中取出一个连接
     *
     * @return
     */
    private static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(poolConnManager)
                .setRetryHandler(httpRequestRetryHandler)
                .build();
        return httpClient;
    }

    /**
     * buildResultMap
     *
     * @param response
     * @param entity
     * @return
     * @throws IOException
     */
    private static Map<String, Object> buildResultMap(CloseableHttpResponse response, HttpEntity entity) throws
            IOException {
        Map<String, Object> result;
        result = new HashMap<>(2);
        result.put(KEY_STATUS_CODE, response.getStatusLine().getStatusCode());  //status code
        if (entity != null) {
            result.put(KEY_CONTENT, EntityUtils.toString(entity, "UTF-8")); //message content
        }
        return result;
    }

    /**
     * send json by post method
     *
     * @param url
     * @param message
     * @return
     * @throws Exception
     */
    public static Map<String, Object> postJson(String url, String message) {

        Map<String, Object> result = null;
        CloseableHttpClient httpClient = getCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {

            httpPost.setHeader("Accept", "application/json;charset=UTF-8");
            httpPost.setHeader("Content-Type", "application/json");

            StringEntity stringEntity = new StringEntity(message);
            stringEntity.setContentType("application/json;charset=UTF-8");

            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = buildResultMap(response, entity);
        } catch (Exception e) {
            log.error("e",e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error("e",e);
                }
            }
        }
        return result;
    }
}
