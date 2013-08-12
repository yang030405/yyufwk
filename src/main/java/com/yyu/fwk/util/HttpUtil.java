package com.yyu.fwk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtil {

//	public static String request(String url, Map<String, String> params) throws Exception {
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		for(Entry<String, String> entry : params.entrySet()){
//			String key = entry.getKey();
//			String value = entry.getValue();
//			nvps.add(new BasicNameValuePair(key, value));
//		}
//		
//		BufferedReader br = null;
//		try{
//			HttpClient hc = HttpClientManager.getInstance();
//			HttpPost post = new HttpPost(url);
//			post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
//			HttpResponse response = hc.execute(post);
//			int statusCode = response.getStatusLine().getStatusCode();
//			if (statusCode == 200) {
//				HttpEntity entity = response.getEntity();
//				if (entity != null) {
//					br = new BufferedReader(new InputStreamReader(entity.getContent()));
//					StringBuffer sb = new StringBuffer();
//					String line = "";
//					while ((line = br.readLine()) != null) {
//						sb.append(line).append("\r\n");
//					}
//					return sb.toString();
//				}
//				else {
//					post.abort();
//					throw new RuntimeException("error:no content");
//				}
//			}else {
//				post.abort();
//				throw new RuntimeException("error: httpResponse status " + statusCode + "!!!");
//			}
//		}finally{
//			if(br != null){
//				try{
//					br.close();
//				}catch(Exception e){
//					throw new RuntimeException("cannot close reader for url[" + url + "]." + e);
//				}
//			}
//		}
//	}
//	
//	public static void download(String url, Map<String, String> params, String filePath) throws Exception {
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		for(Entry<String, String> entry : params.entrySet()){
//			String key = entry.getKey();
//			String value = entry.getValue();
//			nvps.add(new BasicNameValuePair(key, value));
//		}
//		
//		BufferedReader br = null;
//		try{
//			HttpClient hc = HttpClientManager.getInstance();
//			HttpPost post = new HttpPost(url);
//			post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
//			HttpResponse response = hc.execute(post);
//			int statusCode = response.getStatusLine().getStatusCode();
//			if (statusCode == 200) {
//				HttpEntity entity = response.getEntity();
//				if (entity != null) {
//					File file = new File(filePath);
//					if(!file.getParentFile().exists()){
//						file.getParentFile().mkdirs();
//					}
//					writeToFile(entity.getContent(), new FileOutputStream(file));
//				}
//				else {
//					post.abort();
//					throw new RuntimeException("error:no content");
//				}
//			}else {
//				post.abort();
//				throw new RuntimeException("error: httpResponse status " + statusCode + "!!!");
//			}
//		}finally{
//			if(br != null){
//				try{
//					br.close();
//				}catch(Exception e){
//					throw new RuntimeException("cannot close reader for url[" + url + "]." + e);
//				}
//			}
//		}
//	}
	
	public static String request(String url, Map<String, String> params) throws Exception {
		return call(url, params, new Callback<String>(){
			@Override
			public String callback(Object... params) throws Exception {
				BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)params[0]));
				StringBuffer sb = new StringBuffer();
				String line = "";
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\r\n");
				}
				return sb.toString();
			}
		});
	}
	
	public static void download(String url, Map<String, String> params, final String filePath) throws Exception {
		call(url, params, new Callback<String>(){
			@Override
			public String callback(Object... params) throws Exception {
				File file = new File(filePath);
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				FileUtil.writeToFile((InputStream)params[0], new FileOutputStream(file));
				return filePath;
			}
		});
	}
	
	private static String call(String url, Map<String, String> params, Callback<String> callback) throws Exception {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for(Entry<String, String> entry : params.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			nvps.add(new BasicNameValuePair(key, value));
		}
		
		BufferedReader br = null;
		try{
			HttpClient hc = HttpClientManager.getInstance();
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			HttpResponse response = hc.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return callback.callback(entity.getContent());
				}
				else {
					post.abort();
					throw new RuntimeException("error:no content");
				}
			}else {
				post.abort();
				throw new RuntimeException("error: httpResponse status " + statusCode + "!!!");
			}
		}finally{
			if(br != null){
				try{
					br.close();
				}catch(Exception e){
					throw new RuntimeException("cannot close reader for url[" + url + "]." + e);
				}
			}
		}
	}
}
