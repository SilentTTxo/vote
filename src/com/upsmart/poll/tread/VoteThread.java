package com.upsmart.poll.tread;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by norman on 16-11-20.
 */
public class VoteThread implements Runnable {

    private BlockingQueue<IpInfo> ipInfoQueue;
    private static int successCout;

    public VoteThread(BlockingQueue<IpInfo> ipInfoQueue) {
        successCout = 1;
        this.ipInfoQueue = ipInfoQueue;
    }
    
    private void wintof(String data){
    	/*File file = null;
        BufferedWriter bf = null;
    	file=new File("file.txt");
    	try {
			bf=new BufferedWriter(new PrintWriter(file));
			bf.append(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	System.out.println(data);
    	
    }

    public static int createIPAddress(String ip, int port) {
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
			//System.out.println("失败");
			return 0;
		}
    	//System.out.println("成功");
    	return 1;
    	
	}
    
    @SuppressWarnings("deprecation")
	@Override
    public void run() {
    	/*
    	IpInfo ipInfo = null;
    	while(true){
    		try {
                ipInfo = ipInfoQueue.take();
                //System.out.println("Queue's size is " + ipInfoQueue.size());
            } catch (InterruptedException e) {
                //System.out.println("[" + Thread.currentThread().getName() + "]" + "execption : ipInfoQueue has occoured some problem...");
            }
    		if(createIPAddress(ipInfo.getHost(),ipInfo.getPort())==1){
    			System.out.println(ipInfo.getHost()+":"+ipInfo.getPort());
    		}
    	}*/
    	
    	
        IpInfo ipInfo = null;
        int d = 10;
        
        while (true) {
        	
        	d--;
            try {
                ipInfo = ipInfoQueue.take();
                System.out.println("Queue's size is " + ipInfoQueue.size());
            } catch (InterruptedException e) {
                System.out.println("[" + Thread.currentThread().getName() + "]" + "execption : ipInfoQueue has occoured some problem...");
            }
            CloseableHttpClient httpclient = null;
            
            try {
            	
            	for(int a=0;a<10;a++){
            	//获取token
            	httpclient = HttpClients.createDefault();
            	
            	
            	HttpHost target = new HttpHost("http://sj.cbg.cn/cqweb/active/maple/primary");
                HttpHost proxy = new HttpHost(ipInfo.getHost(), ipInfo.getPort(), "http");//.setSocketTimeout(4000).setConnectTimeout(4000)
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                //wintof(ipInfo.getHost()+":"+ipInfo.getPort()+"\n");
                
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
                int y = str.indexOf("6091629\">");
                if(y == -1) break;
                y = Integer.parseInt(str.substring(y+9, y+14));
                //wintof(y+"\n");
                if(y>42000) return;
                String token = "";
                if(i != -1){
                	//System.out.println(str.substring(i-110, i-2));
                	token = str.substring(i-109, i-1);
                	//wintof(token+"\n");
                }
                httpclient.close();
                //投票
                
                	httpclient = HttpClients.createDefault();
                	
                	HttpHost targett = new HttpHost("http://n0brush.cbg.cn:5000/cqweb/maple/vote");
                    HttpHost proxyy = new HttpHost(ipInfo.getHost(), ipInfo.getPort(), "http");
                    RequestConfig configg = RequestConfig.custom().setProxy(proxy).build();
                    
                    HttpPost dap = new HttpPost("http://n0brush.cbg.cn:5000/cqweb/maple/vote");
                    dap.setConfig(configg);
                    dap.setHeader("Host", "m.fangxinbao.com");
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
                    
                    //System.out.println("Executing request " + dap.getRequestLine() +":\r");
                    
                    InputStream instreamm = responses.getEntity().getContent();
                	str = "";
                	while ((len = instreamm.read(tmp)) != -1) {
                        str += new String(tmp).substring(0,len);
                       
                   }
                	wintof(str+"\n");	
                }
            	
            } catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			} finally {
                   System.err.println("结束！！！");
                   
                   
                   try {
					httpclient.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
            }
            
        }

    }
}
