package com.yyu.fwk.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yyu.fwk.performance.Performance.Action;


public class PerformanceTest {
	
	@Test
	public void run() throws Exception{
		final ServiceMethod sm = new ServiceMethod();
		List<Map<String, String>> r;
		
		Performance p = Performance.getInstance(PerformanceTest.class);
		r = p.execute("running a test with 10 seconds", new Action<List<Map<String, String>>>(){public List<Map<String, String>> action() throws Exception {
			return sm.doService();
		}});
		
		System.out.println(r);
	}
}


class ServiceMethod{
	public void doService2(){
		
	}
	
	public List<Map<String, String>> doService(){
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("m1", "m1v");
		
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("m2", "m2v");
		
		List<Map<String, String>> l = new ArrayList<Map<String, String>>();
		l.add(m1);
		l.add(m2);
		
		return l;
	}
}