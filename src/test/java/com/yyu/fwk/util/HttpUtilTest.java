package com.yyu.fwk.util;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.reflect.TypeToken;

@SuppressWarnings("unchecked")
public class HttpUtilTest {
	private static Log log = LogFactory.getLog(HttpUtilTest.class);
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		work();
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "ms costs for working.");
	}
	
	private static void work() throws Exception {
		
		Map<String, String> startEndDate = getStartEndDate();
		double total = startEndDate.size();
		double count = 0;
		for(Entry<String, String> entry : startEndDate.entrySet()){
			double startTime = System.currentTimeMillis();
			String startDate = entry.getKey();
			String endDate = entry.getValue();
			log.info("start generating report from " + startDate + " to " + endDate + ".");
			String filePath = generateFile(startDate, endDate);
			log.info("report generates over from " + startDate + " to " + endDate + ".");
			try{
				String downloadFilePath = downloadFile(startDate, filePath);
			}catch(Exception e){
				log.error(e.getMessage());
			}
			
			double endTime = System.currentTimeMillis();
			double executePercent = ++count / total;
			log.info("execute generating and downloading " + (executePercent * 100) + "%, cost " + (endTime - startTime) + "ms.");
		}
	}
	
	private static String generateFile(String startDate, String endDate) throws Exception {
		String url = "http://218.244.176.201:6080/datacenter/datamining/createReport.json";
		Map<String, String> param = new HashMap<String, String>();
		param.put("searchEngine", "baidu");
		param.put("roiType", "account_roi");
		param.put("orderby", "tenantId");
		param.put("groupField", "tenantId");
		param.put("selectField", "cost");
		
//		param.put("tenantId", "10464,10466");
		param.put("tenantId", getTenantIds());
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		String result = HttpUtil.request(url, param);
		Type type = new TypeToken<Map<String, String>>(){}.getType();
		Map<String, String> resultType = (Map<String, String>)JSONUtil.fromJson(result, type);
		String filePath = resultType.get("filePath");
		return filePath;
	}
	
	private static String downloadFile(String startDate, String filePath) throws Exception {
		String url = "http://218.244.176.201:6080/datacenter/datamining/downloadReport.json";
		Map<String, String> paramForDownlaoding = new HashMap<String, String>();
		paramForDownlaoding.put("filePath", filePath);
		String destFilePath = "/Users/apple/Downloads/account_monthly_cost/" + startDate + ".csv";
		File descFile = new File(destFilePath);
		if(!descFile.getParentFile().exists()){
			descFile.getParentFile().mkdirs();
		}
		HttpUtil.download(url, paramForDownlaoding, destFilePath);
		return destFilePath;
	}
	
	private static Map<String, String> getStartEndDate(){
		Map<String, String> startAndEnd = new LinkedHashMap<String, String>();
		for(int i = -4; i <= 1; i++){
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, i);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DAY_OF_MONTH, -1);
			String endDate = DateUtil.getDate(c.getTime());
			c.set(Calendar.DAY_OF_MONTH, 1);
			String startDate = DateUtil.getDate(c.getTime());
			startAndEnd.put(startDate, endDate);
		}
//		startAndEnd.put("2013-09-15", "2013-09-15");
		return startAndEnd;
	}
	
	private static String getTenantIds(){
		StringBuffer sb = new StringBuffer();
		String filePath = "/Users/apple/Downloads/account_agent.txt";
		CsvFileParser p = new CsvFileParser(filePath);
		boolean isFirst = true;
		while(p.hasNext()){
			Map<String, String> data = p.next();
			if(isFirst){
				sb.append(data.get("id"));
				isFirst = false;
			}else{
				sb.append("," + data.get("id"));
			}
		}
		return sb.toString();
	}
}
