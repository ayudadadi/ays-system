package com.marvin.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marvin
 * @Description com.marvin.utils
 * @create 2021-11-16 16:47
 */

public class HttpUtils {
    private PoolingHttpClientConnectionManager cm;
    private ArrayList<String> agents;

    public HttpUtils() {
        // 创建连接池管理器
        this.cm = new PoolingHttpClientConnectionManager();
        // 设置连接数
        this.cm.setMaxTotal(100);
        // 设置每个主机（理解为网站，如：百度10个、网易10个）的最大连接数

        this.cm.setDefaultMaxPerRoute(10);
        //初始化 User-Agent 信息
        this.agents = new ArrayList<String>();
        // 添加 User-Agent 信息
        agents.add("Mozilla/5.0 (Windows NT 5.1; U; en; rv:1.8.1) Gecko/20061208 Firefox/2.0.0 Opera 9.50");
        agents.add("Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
        agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");
        agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        agents.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        agents.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
        agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
        agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER");
        agents.add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
        agents.add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
        agents.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 UBrowser/4.0.3214.0 Safari/537.36");
        System.out.println("<--------- HttpUtils initialization success --------->");
    }


    /**
     * 获取页面源代码
     *
     * @param url 网页链接
     * @return 页面源代码
     */
    public String doGetHtml(String url) {
        // 通过连接池获取 httpClient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        HttpGet httpGet = new HttpGet(url);
        //设置代理ip
        HttpHost proxy = new HttpHost("tps222.kdlapi.com",15818);

        // 伪造 User-Agent（反反爬虫）
        int agentNum = new Random().nextInt(agents.size());// 生成一个范围在 0-x（不包含x）内的任意正整数
        httpGet.addHeader("User-Agent", agents.get(agentNum));

        // 设置请求信息
        httpGet.setConfig(getConfig(proxy));

        // 定义 response，方便 finally 中关闭
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 获取并判断，状态码是否正常（正常值：200）
            System.out.println("状态码："+response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                // 判断响应体是否为空，不为空则获取内容
                if (response.getEntity() != null) {
                    // 获取响应体，并指定 UTF-8 编码
                    String content = EntityUtils.toString(response.getEntity(), "utf8");

                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 判断并关闭 response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 不关闭 httpClient，交给连接池管理
        }
        System.out.println("<--------- doGetHtml() ERROR --------->");
        return "";
    }

    private RequestConfig getConfig(HttpHost proxy) {
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(1000)// 创建连接的最长时间
                .setConnectionRequestTimeout(1000)// 获取连接最长时间
                .setSocketTimeout(10 * 1000)// 数据传输最长时间
                .build();
        return config;
    }
}
