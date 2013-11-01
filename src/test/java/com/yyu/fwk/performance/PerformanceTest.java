package com.yyu.fwk.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.yyu.fwk.common.DurationExecuter;
import com.yyu.fwk.common.Executer;


public class PerformanceTest {
	
	@Test
	public void run() throws Exception{
		final ServiceMethod sm = new ServiceMethod();
		
		Executer d = new DurationExecuter<String>("service"){
			@Override
			public void action() throws Exception {
				System.out.println(sm.doService());
			}
		};
		d.execute();
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