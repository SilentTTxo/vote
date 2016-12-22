package com.upsmart.poll.tread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by norman on 16-11-19.
 */
public class app {
	public static void createIPAddress(String ip, int port) {
		HttpClient httpclient = HttpClients.createDefault();
		
		String proxip = ip;
    	HttpHost target = new HttpHost("http://sj.cbg.cn/cqweb/active/maple/primary");
        HttpHost proxy = new HttpHost(proxip, port, "http");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(4000).setConnectTimeout(4000).build();
        
    	HttpGet da = new HttpGet("http://sj.cbg.cn/cqweb/active/maple/primary");
    	da.setConfig(config);
    	try {
			HttpResponse response = httpclient.execute(da);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("GG");
			return;
		}
    	System.out.println("ok");
	}
    
    public static void main(String[] args) {
    	
        
        int d = 0;
        //createIPAddress("127.0.0.1",8888);

        while (d != 0) {
        	d-=1;
            try {

            	//获取token
            	HttpClient httpclient = HttpClients.createDefault();
            	
            	String proxip = "120.92.3.127";
            	int port = 80;
            	HttpHost target = new HttpHost("http://sj.cbg.cn/cqweb/active/maple/primary");
                HttpHost proxy = new HttpHost(proxip, port, "http");
                RequestConfig config = RequestConfig.custom().setProxy(proxy).setSocketTimeout(4000).setConnectTimeout(4000).build();
                
            	HttpGet da = new HttpGet("http://sj.cbg.cn/cqweb/active/maple/primary");
            	da.setConfig(config);
            	HttpResponse response = httpclient.execute(da);
            	InputStream instream = response.getEntity().getContent();
                int len;
                byte[] tmp = new byte[2048];
                OutputStream out = System.out;
                String str = "";
                while ((len = instream.read(tmp)) != -1) {
                     str += new String(tmp);
                    
                }
                //System.out.println(str);
                int i = str.indexOf(",6091629");
                String token = "";
                if(i != -1){
                	//System.out.println(str.substring(i-110, i-2));
                	token = str.substring(i-109, i-1);
                	System.out.println(token);
                }
                //投票
                
                HttpHost targett = new HttpHost("http://n0brush.cbg.cn:5000/cqweb/maple/vote");
                HttpHost proxyy = new HttpHost(proxip, port, "http");
                RequestConfig configg = RequestConfig.custom().setProxy(proxyy).setSocketTimeout(4000).setConnectTimeout(4000).build();
                
                HttpPost dap = new HttpPost("http://n0brush.cbg.cn:5000/cqweb/maple/vote");
                dap.setConfig(configg);
                dap.setHeader("Host", "n0brush.cbg.cn:5000");
                dap.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
                dap.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                dap.setHeader("Connection", "keep-alive");
                dap.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
                dap.setHeader("Accept-Encoding", "gzip, deflate");
                dap.setHeader("X-Requested-With", "XMLHttpRequest");
                
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("token", token));
                //url格式编码
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
                dap.setEntity(uefEntity);
                
                HttpResponse responses = httpclient.execute(dap);
                
                System.out.println("Executing request " + dap.getRequestLine() +":\r");
                
                InputStream instreamm = responses.getEntity().getContent();
            	str = "";
            	while ((len = instreamm.read(tmp)) != -1) {
                    str += new String(tmp).substring(0,len);
                   
               }
            	 System.out.println(str);	
            	Thread.sleep(1000);
            	
            } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
                   System.err.println("结束！！！");
            }
        }
    }
}
