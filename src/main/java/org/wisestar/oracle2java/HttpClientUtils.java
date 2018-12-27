package org.wisestar.oracle2java;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {

	 @SuppressWarnings("deprecation")
	public static String callUrlPostByJson(String url, String jsonData) {
		 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(url);
	        
	        RequestConfig requestConfig = RequestConfig.custom()
	                .setSocketTimeout(10000)
	                .setConnectTimeout(20000)
	                .setConnectionRequestTimeout(10000).build();
	        
	        httpPost.setConfig(requestConfig);
	        String context = "";
	        if (!StringUtils.isEmpty(jsonData)) {
	            StringEntity body = new StringEntity(jsonData, "utf-8");
	            httpPost.setEntity(body);
	        }
	        
	        // 设置回调接口接收的消息头
	        httpPost.addHeader("Content-Type", "application/json");
	        CloseableHttpResponse response = null;
	        try {
	            response = httpClient.execute(httpPost);
	            HttpEntity entity = response.getEntity();
	            context = EntityUtils.toString(entity, HTTP.UTF_8);
	            
	        } catch (Exception e) {
	            e.getStackTrace();
	        } finally {
	            try {
	                response.close();
	                httpPost.abort();
	                httpClient.close();
	            } catch (Exception e) {
	                e.getStackTrace();
	            }
	        }
	        
	        return context;
	    }
	 
	
}
