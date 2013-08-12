package com.yyu.fwk.formula;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.csvreader.CsvReader;
import com.yyu.fwk.formula.stackversion.Calculator;
import com.yyu.fwk.formula.stackversion.OperatorEnum;
import com.yyu.fwk.formula.stackversion.expression.ExpressionIterator;
import com.yyu.fwk.util.BeanUtil;

public class CalculatorTest {

	@Ignore
	@Test
	public void test000() {
		try{
			Calculator c = new Calculator("");
			System.out.println(c.getInputExpression());
			System.out.println(c.getParsedExpression());
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void test001() {
		String expStr = "a +     b";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.20");
		
		Double expected = 3.3d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test002() {
		String expStr = "a +     b  -c";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.20");
		vars.put("c", "2.20");
		
		Double expected = 1.1d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test003() {
		String expStr = "a +     b  *c";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2");
		vars.put("c", "2.00");
		
		Double expected = 5.1d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test004() {
		String expStr = "(a +     b)  *c";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Double expected = 6.6d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test005() {
		String expStr = "(a + b)  *c / '2'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Double expected = 3.3d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test006() {
		String expStr = "(a + b)  *(c / '2')";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Double expected = 3.3d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test007() {
		String expStr = "(a + b  *c / '2')";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Double expected = 3.3d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test008() {
		String expStr = "(a + b  *c / '2') * '-1'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Double expected = -3.3d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test009() {
		String expStr = "(a + b  *c / '2' - '1') * '-1'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Double expected = -2.3d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test010() {
		String expStr = "(a + b  *c / '2' - '1') * '-1' = '-2.3'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1.10");
		vars.put("b", "2.2");
		vars.put("c", "2.00");
		
		Boolean expected = true;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test011() {
		String expStr = "!a";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "true");
		
		Boolean expected = false;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test012() {
		String expStr = "!a";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "false");
		
		Boolean expected = true;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test013() {
		String expStr = "!('true')";
		
		Boolean expected = false;
		
		execute(expStr, null, expected);
	}
	
	@Test
	public void test014() {
		String expStr = "!('false')";
		
		Boolean expected = true;
		
		execute(expStr, null, expected);
	}
	
	@Test
	public void test015() {
		String expStr = "a + (b +   (c + (d * ('2'  + '1'))))";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		
		Double expected = 18d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test016() {
		String expStr = "a + (b +   '2') * c + (d * ('2'  + '1'))";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		
		Double expected = 25d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test017() {
		String expStr = "a + (b +   '2') * c + (d * ('2'  + '1'))";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		
		Double expected = 25d;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test018() {
		String expStr = "a + (b +   '2') * c + (d * ('2'  + '1')) > '25'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		vars.put("e", "25");
		
		Boolean expected = false;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test019() {
		String expStr = "a + (b +   '2') * c + (d * ('2'  + '1')) >= '25'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		vars.put("e", "25");
		
		Boolean expected = true;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test020() {
		String expStr = "a + (b +   '2') * c + (d * ('2'  + '1')) <= '25'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		vars.put("e", "25");
		
		Boolean expected = true;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test021() {
		String expStr = "  a +   b";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		
		Double expected = 3.0;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test022() {
		String expStr = "a + (b +   '2') * c + (d * ('2'  + '1')) < '25'";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("a", "1");
		vars.put("b", "2");
		vars.put("c", "3");
		vars.put("d", "4");
		vars.put("e", "25");
		
		Boolean expected = false;
		
		execute(expStr, vars, expected);
	}
	
	@Test
	public void test023() throws Exception {
		String expStr = "(searchEngine='baidu' ) && (product='竞价广告' ) && (#{account}='bj-dafengso' ) && (impressions+clicks<='2000' && impressions+clicks>='1000') ";
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("searchEngine", "baidu");
		vars.put("product", "竞价广告");
		vars.put("#{account}", "bj-dafengso");
		vars.put("impressions", "2001");
		vars.put("clicks", "0");
		
		
//		ExpressionIterator iter = new ExpressionIterator(expStr);
//		while(iter.hasNext()){
//			System.out.println(iter.next());
//		}
//		System.out.println();
		
		Calculator c = new Calculator(expStr);
		Object result = c.execute(vars);
		System.out.println(result);
		System.out.println(c.getParsedExpression());
	}
	
	@Test
	public void jsEngineTest() throws Exception{

		Map<String, String> record = new HashMap<String, String>();

		
//		String expStr = "(account='baidu-金吉列留学2132840-030' ||account='baidu-金吉列留学2132840-034' ||account='baidu-金吉列留学2132840-050' ||account='baidu-金吉列留学2132840-005' ||account='baidu-金吉列留学2132840-014' ||account='baidu-金吉列留学2132840-018' ) && (campaignId='10472001' ||campaignId='10472709' ||campaignId='10028390' ||campaignId='10028393' ||campaignId='10028396' ||campaignId='10028399' ||campaignId='10028402' ||campaignId='10028405' ||campaignId='10028408' ||campaignId='10028411' ||campaignId='10028414' ||campaignId='10028417' ||campaignId='10028420' ||campaignId='10028423' ||campaignId='10028426' ||campaignId='10028429' ||campaignId='10028432' ||campaignId='10028435' ||campaignId='10028438' ||campaignId='10028441' ||campaignId='10028444' ||campaignId='10028447' ||campaignId='10028450' ||campaignId='10028453' ||campaignId='10028456' ||campaignId='10028459' ||campaignId='10028462' ||campaignId='10028465' ||campaignId='10028468' ||campaignId='10028471' ||campaignId='10028474' ||campaignId='10028477' ||campaignId='10028480' ||campaignId='10028483' ||campaignId='10028486' ||campaignId='10028489' ||campaignId='10028492' ||campaignId='10028495' ||campaignId='10028498' ||campaignId='10028501' ||campaignId='10028504' ||campaignId='10028507' ||campaignId='10028510' ||campaignId='10028513' ||campaignId='10028516' ||campaignId='10028519' ||campaignId='10028522' ||campaignId='10028525' ||campaignId='10028528' ||campaignId='10028531' ||campaignId='10028534' ||campaignId='10028537' ||campaignId='9978988' ||campaignId='9978991' ||campaignId='9978994' ||campaignId='9978997' ||campaignId='9979000' ||campaignId='9979003' ||campaignId='9979006' ||campaignId='9979009' ||campaignId='9979012' ||campaignId='9979015' ||campaignId='9979018' ||campaignId='9979021' ||campaignId='9979024' ||campaignId='9979027' ||campaignId='9979030' ||campaignId='9979033' ||campaignId='9979036' ||campaignId='9979039' ||campaignId='9979042' ||campaignId='9979045' ||campaignId='9979048' ||campaignId='9979051' ||campaignId='9979054' ||campaignId='9979057' ||campaignId='9979060' ||campaignId='9979063' ||campaignId='9979066' ||campaignId='9979069' ||campaignId='9979072' ||campaignId='9979075' ||campaignId='9979078' ||campaignId='9979081' ||campaignId='9979084' ||campaignId='9979087' ||campaignId='9979090' ||campaignId='9979093' ||campaignId='9979096' ||campaignId='9979099' ||campaignId='9979102' ||campaignId='9979105' ||campaignId='9979108' ||campaignId='9979111' ||campaignId='9979114' ||campaignId='9979117' ||campaignId='9979120' ||campaignId='9979123' ||campaignId='9979126' ||campaignId='9979129' ||campaignId='9979132' ||campaignId='9979135' ||campaignId='10455086' ||campaignId='10455125' ||campaignId='10455194' ||campaignId='9898870' ||campaignId='9898873' ||campaignId='9898876' ||campaignId='9898879' ||campaignId='9898882' ||campaignId='9898885' ||campaignId='9898888' ||campaignId='9898891' ||campaignId='9898894' ||campaignId='9898897' ||campaignId='9898900' ||campaignId='9898903' ||campaignId='9898906' ||campaignId='9898909' ||campaignId='9898912' ||campaignId='9898915' ||campaignId='9898918' ||campaignId='9898921' ||campaignId='9898924' ||campaignId='9898927' ||campaignId='9898930' ||campaignId='9898933' ||campaignId='9898936' ||campaignId='9898939' ||campaignId='9898942' ||campaignId='9898945' ||campaignId='9898948' ||campaignId='9898951' ||campaignId='9898954' ||campaignId='9898957' ||campaignId='9898960' ||campaignId='9956850' ||campaignId='9956853' ||campaignId='9956856' ||campaignId='9956859' ||campaignId='9956862' ||campaignId='9956865' ||campaignId='9956868' ||campaignId='9956871' ||campaignId='9956874' ||campaignId='9956877' ||campaignId='9956880' ||campaignId='9956883' ||campaignId='9956886' ||campaignId='9956889' ||campaignId='9956892' ||campaignId='9956895' ||campaignId='9956898' ||campaignId='9956901' ||campaignId='9956904' ||campaignId='9956907' ||campaignId='9956910' ||campaignId='9956913' ||campaignId='9956916' ||campaignId='9956919' ||campaignId='9956922' ||campaignId='9956925' ||campaignId='9956928' ||campaignId='9956931' ||campaignId='9956934' ||campaignId='9956937' ||campaignId='9956940' ||campaignId='9956943' ||campaignId='9956946' ||campaignId='9956949' ||campaignId='9956952' ||campaignId='9956955' ||campaignId='9956958' ||campaignId='9956961' ||campaignId='9956964' ||campaignId='9956967' ||campaignId='9956970' ||campaignId='9956973' ||campaignId='9956976' ||campaignId='9956979' ||campaignId='9956982' ||campaignId='9956985' ||campaignId='9956988' ||campaignId='9956991' ||campaignId='9956994' ||campaignId='9956997' ||campaignId='9936104' ||campaignId='9936107' ||campaignId='9936110' ||campaignId='9941907' ||campaignId='9942021' ||campaignId='9942024' ||campaignId='9942048' ||campaignId='9942054' ||campaignId='9959898' ||campaignId='9937856' ||campaignId='9937859' ||campaignId='9937862' ||campaignId='9937865' ||campaignId='9937868' ||campaignId='9937871' ||campaignId='9937874' ||campaignId='9937877' ||campaignId='9937880' ||campaignId='9937883' ||campaignId='9937886' ||campaignId='9937889' ||campaignId='9937892' ||campaignId='9937895' ||campaignId='9937898' ||campaignId='9937901' ||campaignId='9937904' ||campaignId='9937907' ||campaignId='9937910' ||campaignId='9937913' ||campaignId='9937916' )";
		String expStr = "(account='baidu-金吉列留学2132840-030') && (campaignId='1.0') && (keywordId == 2 )";
		expStr = processWhereFormulaToNewFormat(expStr);
		System.out.println(">>>>>>" + expStr);
		ScriptEngineManager manager = new ScriptEngineManager();  
	    ScriptEngine engine = manager.getEngineByName("js");
	    for(Entry<String, String> e : record.entrySet()){
	    	String key = e.getKey();
	    	String value = e.getValue();
	    	engine.put(key, value);
	    }
	    Object result = engine.eval(expStr);
	    System.out.println(result);
	}
	
	@Ignore
	@Test
	public void performanceTest() throws Exception{
		String filePath =  "/Users/apple/Documents/18138.txt";
		CsvReader reader = new CsvReader(filePath, '\t', Charset.forName("gbk"));
		reader.readHeaders();
		String[] headers = reader.getHeaders();
		reader.readRecord();
		String[] values = reader.getValues();
		Map<String, String> record = new HashMap<String, String>();
		for(int i = 0; i < headers.length; i++){
			record.put(headers[i], values[i]);
		}
		Map<String, String> newRecord = cloneData(record);
		
		String expStr = "(account='baidu-金吉列留学2132840-030' ||account='baidu-金吉列留学2132840-034' ||account='baidu-金吉列留学2132840-050' ||account='baidu-金吉列留学2132840-005' ||account='baidu-金吉列留学2132840-014' ||account='baidu-金吉列留学2132840-018' ) && (campaignId='10472001' ||campaignId='10472709' ||campaignId='10028390' ||campaignId='10028393' ||campaignId='10028396' ||campaignId='10028399' ||campaignId='10028402' ||campaignId='10028405' ||campaignId='10028408' ||campaignId='10028411' ||campaignId='10028414' ||campaignId='10028417' ||campaignId='10028420' ||campaignId='10028423' ||campaignId='10028426' ||campaignId='10028429' ||campaignId='10028432' ||campaignId='10028435' ||campaignId='10028438' ||campaignId='10028441' ||campaignId='10028444' ||campaignId='10028447' ||campaignId='10028450' ||campaignId='10028453' ||campaignId='10028456' ||campaignId='10028459' ||campaignId='10028462' ||campaignId='10028465' ||campaignId='10028468' ||campaignId='10028471' ||campaignId='10028474' ||campaignId='10028477' ||campaignId='10028480' ||campaignId='10028483' ||campaignId='10028486' ||campaignId='10028489' ||campaignId='10028492' ||campaignId='10028495' ||campaignId='10028498' ||campaignId='10028501' ||campaignId='10028504' ||campaignId='10028507' ||campaignId='10028510' ||campaignId='10028513' ||campaignId='10028516' ||campaignId='10028519' ||campaignId='10028522' ||campaignId='10028525' ||campaignId='10028528' ||campaignId='10028531' ||campaignId='10028534' ||campaignId='10028537' ||campaignId='9978988' ||campaignId='9978991' ||campaignId='9978994' ||campaignId='9978997' ||campaignId='9979000' ||campaignId='9979003' ||campaignId='9979006' ||campaignId='9979009' ||campaignId='9979012' ||campaignId='9979015' ||campaignId='9979018' ||campaignId='9979021' ||campaignId='9979024' ||campaignId='9979027' ||campaignId='9979030' ||campaignId='9979033' ||campaignId='9979036' ||campaignId='9979039' ||campaignId='9979042' ||campaignId='9979045' ||campaignId='9979048' ||campaignId='9979051' ||campaignId='9979054' ||campaignId='9979057' ||campaignId='9979060' ||campaignId='9979063' ||campaignId='9979066' ||campaignId='9979069' ||campaignId='9979072' ||campaignId='9979075' ||campaignId='9979078' ||campaignId='9979081' ||campaignId='9979084' ||campaignId='9979087' ||campaignId='9979090' ||campaignId='9979093' ||campaignId='9979096' ||campaignId='9979099' ||campaignId='9979102' ||campaignId='9979105' ||campaignId='9979108' ||campaignId='9979111' ||campaignId='9979114' ||campaignId='9979117' ||campaignId='9979120' ||campaignId='9979123' ||campaignId='9979126' ||campaignId='9979129' ||campaignId='9979132' ||campaignId='9979135' ||campaignId='10455086' ||campaignId='10455125' ||campaignId='10455194' ||campaignId='9898870' ||campaignId='9898873' ||campaignId='9898876' ||campaignId='9898879' ||campaignId='9898882' ||campaignId='9898885' ||campaignId='9898888' ||campaignId='9898891' ||campaignId='9898894' ||campaignId='9898897' ||campaignId='9898900' ||campaignId='9898903' ||campaignId='9898906' ||campaignId='9898909' ||campaignId='9898912' ||campaignId='9898915' ||campaignId='9898918' ||campaignId='9898921' ||campaignId='9898924' ||campaignId='9898927' ||campaignId='9898930' ||campaignId='9898933' ||campaignId='9898936' ||campaignId='9898939' ||campaignId='9898942' ||campaignId='9898945' ||campaignId='9898948' ||campaignId='9898951' ||campaignId='9898954' ||campaignId='9898957' ||campaignId='9898960' ||campaignId='9956850' ||campaignId='9956853' ||campaignId='9956856' ||campaignId='9956859' ||campaignId='9956862' ||campaignId='9956865' ||campaignId='9956868' ||campaignId='9956871' ||campaignId='9956874' ||campaignId='9956877' ||campaignId='9956880' ||campaignId='9956883' ||campaignId='9956886' ||campaignId='9956889' ||campaignId='9956892' ||campaignId='9956895' ||campaignId='9956898' ||campaignId='9956901' ||campaignId='9956904' ||campaignId='9956907' ||campaignId='9956910' ||campaignId='9956913' ||campaignId='9956916' ||campaignId='9956919' ||campaignId='9956922' ||campaignId='9956925' ||campaignId='9956928' ||campaignId='9956931' ||campaignId='9956934' ||campaignId='9956937' ||campaignId='9956940' ||campaignId='9956943' ||campaignId='9956946' ||campaignId='9956949' ||campaignId='9956952' ||campaignId='9956955' ||campaignId='9956958' ||campaignId='9956961' ||campaignId='9956964' ||campaignId='9956967' ||campaignId='9956970' ||campaignId='9956973' ||campaignId='9956976' ||campaignId='9956979' ||campaignId='9956982' ||campaignId='9956985' ||campaignId='9956988' ||campaignId='9956991' ||campaignId='9956994' ||campaignId='9956997' ||campaignId='9936104' ||campaignId='9936107' ||campaignId='9936110' ||campaignId='9941907' ||campaignId='9942021' ||campaignId='9942024' ||campaignId='9942048' ||campaignId='9942054' ||campaignId='9959898' ||campaignId='9937856' ||campaignId='9937859' ||campaignId='9937862' ||campaignId='9937865' ||campaignId='9937868' ||campaignId='9937871' ||campaignId='9937874' ||campaignId='9937877' ||campaignId='9937880' ||campaignId='9937883' ||campaignId='9937886' ||campaignId='9937889' ||campaignId='9937892' ||campaignId='9937895' ||campaignId='9937898' ||campaignId='9937901' ||campaignId='9937904' ||campaignId='9937907' ||campaignId='9937910' ||campaignId='9937913' ||campaignId='9937916' ) ";
//		String expStr = processWhereFormulaToNewFormat("(account='baidu-金吉列留学2132840-030' ||account='baidu-金吉列留学2132840-034' ||account='baidu-金吉列留学2132840-050' ||account='baidu-金吉列留学2132840-005' ||account='baidu-金吉列留学2132840-014' ||account='baidu-金吉列留学2132840-018' ) && (campaignId='10472001' ||campaignId='10472709' ||campaignId='10028390' ||campaignId='10028393' ||campaignId='10028396' ||campaignId='10028399' ||campaignId='10028402' ||campaignId='10028405' ||campaignId='10028408' ||campaignId='10028411' ||campaignId='10028414' ||campaignId='10028417' ||campaignId='10028420' ||campaignId='10028423' ||campaignId='10028426' ||campaignId='10028429' ||campaignId='10028432' ||campaignId='10028435' ||campaignId='10028438' ||campaignId='10028441' ||campaignId='10028444' ||campaignId='10028447' ||campaignId='10028450' ||campaignId='10028453' ||campaignId='10028456' ||campaignId='10028459' ||campaignId='10028462' ||campaignId='10028465' ||campaignId='10028468' ||campaignId='10028471' ||campaignId='10028474' ||campaignId='10028477' ||campaignId='10028480' ||campaignId='10028483' ||campaignId='10028486' ||campaignId='10028489' ||campaignId='10028492' ||campaignId='10028495' ||campaignId='10028498' ||campaignId='10028501' ||campaignId='10028504' ||campaignId='10028507' ||campaignId='10028510' ||campaignId='10028513' ||campaignId='10028516' ||campaignId='10028519' ||campaignId='10028522' ||campaignId='10028525' ||campaignId='10028528' ||campaignId='10028531' ||campaignId='10028534' ||campaignId='10028537' ||campaignId='9978988' ||campaignId='9978991' ||campaignId='9978994' ||campaignId='9978997' ||campaignId='9979000' ||campaignId='9979003' ||campaignId='9979006' ||campaignId='9979009' ||campaignId='9979012' ||campaignId='9979015' ||campaignId='9979018' ||campaignId='9979021' ||campaignId='9979024' ||campaignId='9979027' ||campaignId='9979030' ||campaignId='9979033' ||campaignId='9979036' ||campaignId='9979039' ||campaignId='9979042' ||campaignId='9979045' ||campaignId='9979048' ||campaignId='9979051' ||campaignId='9979054' ||campaignId='9979057' ||campaignId='9979060' ||campaignId='9979063' ||campaignId='9979066' ||campaignId='9979069' ||campaignId='9979072' ||campaignId='9979075' ||campaignId='9979078' ||campaignId='9979081' ||campaignId='9979084' ||campaignId='9979087' ||campaignId='9979090' ||campaignId='9979093' ||campaignId='9979096' ||campaignId='9979099' ||campaignId='9979102' ||campaignId='9979105' ||campaignId='9979108' ||campaignId='9979111' ||campaignId='9979114' ||campaignId='9979117' ||campaignId='9979120' ||campaignId='9979123' ||campaignId='9979126' ||campaignId='9979129' ||campaignId='9979132' ||campaignId='9979135' ||campaignId='10455086' ||campaignId='10455125' ||campaignId='10455194' ||campaignId='9898870' ||campaignId='9898873' ||campaignId='9898876' ||campaignId='9898879' ||campaignId='9898882' ||campaignId='9898885' ||campaignId='9898888' ||campaignId='9898891' ||campaignId='9898894' ||campaignId='9898897' ||campaignId='9898900' ||campaignId='9898903' ||campaignId='9898906' ||campaignId='9898909' ||campaignId='9898912' ||campaignId='9898915' ||campaignId='9898918' ||campaignId='9898921' ||campaignId='9898924' ||campaignId='9898927' ||campaignId='9898930' ||campaignId='9898933' ||campaignId='9898936' ||campaignId='9898939' ||campaignId='9898942' ||campaignId='9898945' ||campaignId='9898948' ||campaignId='9898951' ||campaignId='9898954' ||campaignId='9898957' ||campaignId='9898960' ||campaignId='9956850' ||campaignId='9956853' ||campaignId='9956856' ||campaignId='9956859' ||campaignId='9956862' ||campaignId='9956865' ||campaignId='9956868' ||campaignId='9956871' ||campaignId='9956874' ||campaignId='9956877' ||campaignId='9956880' ||campaignId='9956883' ||campaignId='9956886' ||campaignId='9956889' ||campaignId='9956892' ||campaignId='9956895' ||campaignId='9956898' ||campaignId='9956901' ||campaignId='9956904' ||campaignId='9956907' ||campaignId='9956910' ||campaignId='9956913' ||campaignId='9956916' ||campaignId='9956919' ||campaignId='9956922' ||campaignId='9956925' ||campaignId='9956928' ||campaignId='9956931' ||campaignId='9956934' ||campaignId='9956937' ||campaignId='9956940' ||campaignId='9956943' ||campaignId='9956946' ||campaignId='9956949' ||campaignId='9956952' ||campaignId='9956955' ||campaignId='9956958' ||campaignId='9956961' ||campaignId='9956964' ||campaignId='9956967' ||campaignId='9956970' ||campaignId='9956973' ||campaignId='9956976' ||campaignId='9956979' ||campaignId='9956982' ||campaignId='9956985' ||campaignId='9956988' ||campaignId='9956991' ||campaignId='9956994' ||campaignId='9956997' ||campaignId='9936104' ||campaignId='9936107' ||campaignId='9936110' ||campaignId='9941907' ||campaignId='9942021' ||campaignId='9942024' ||campaignId='9942048' ||campaignId='9942054' ||campaignId='9959898' ||campaignId='9937856' ||campaignId='9937859' ||campaignId='9937862' ||campaignId='9937865' ||campaignId='9937868' ||campaignId='9937871' ||campaignId='9937874' ||campaignId='9937877' ||campaignId='9937880' ||campaignId='9937883' ||campaignId='9937886' ||campaignId='9937889' ||campaignId='9937892' ||campaignId='9937895' ||campaignId='9937898' ||campaignId='9937901' ||campaignId='9937904' ||campaignId='9937907' ||campaignId='9937910' ||campaignId='9937913' ||campaignId='9937916' ) ");
		System.out.println(expStr);
//		String expStr = "(#{account}='baidu-金吉列留学2132840-030' ||#{account}='baidu-金吉列留学2132840-034' ||#{account}='baidu-金吉列留学2132840-050' ||#{account}='baidu-金吉列留学2132840-014' ||#{account}='baidu-金吉列留学2132840-018' ) &&(#{campaignId}=10472001||#{campaignId}=10472709||#{campaignId}=10028402||#{campaignId}=10028450||#{campaignId}=10028477||#{campaignId}=10028498||#{campaignId}=10028531||#{campaignId}=10028534||#{campaignId}=9979057||#{campaignId}=9979069||#{campaignId}=9979078||#{campaignId}=9979102||#{campaignId}=10455086||#{campaignId}=10455125||#{campaignId}=10455194||#{campaignId}=9936047||#{campaignId}=9936104||#{campaignId}=9936107||#{campaignId}=9936110||#{campaignId}=9941907||#{campaignId}=9942021||#{campaignId}=9942024||#{campaignId}=9942048||#{campaignId}=9942054)";
		
		Calculator c = new Calculator(expStr);
		long startTime = System.currentTimeMillis();
		Object result = c.execute(record);
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "ms    result = " + result);
	}
	
	private static HashMap<String, String> cloneData(Map<String, String> data){
		@SuppressWarnings("unchecked")
		HashMap<String, String> dataClone = (HashMap<String, String>)((HashMap<String, String>)data).clone();
		
		for(Entry<String, String> entry : dataClone.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			value = BeanUtil.isBlank(value) ? "-" : value;

			if(!BeanUtil.isNum(value) || value.trim().equals("-")){
				value = "'" + value + "'";
			}
			dataClone.put(key, value);
		}
		
		return dataClone;
	}
	
	private static String processWhereFormulaToNewFormat(String expr){
		StringBuffer newexpr = new StringBuffer();
		ExpressionIterator ei = new ExpressionIterator(expr, true);
		while(ei.hasNext()){
			String exp = ei.next();
			if(OperatorEnum.isDefine(exp)){ // if it is a symbol
				if(exp.equals(OperatorEnum.EQUAL.getChar())){
					exp = "==";
				}
				newexpr.append(exp);
			}else if(exp.charAt(0) == OperatorEnum.SINGLE_QUOTE.getChar().charAt(0) && exp.charAt(exp.length() - 1) == OperatorEnum.SINGLE_QUOTE.getChar().charAt(0)){ // if it is a literal value
				String tempexp = exp.replaceAll("'", "");
				if(BeanUtil.isNum(tempexp)){
					exp = tempexp;
				}
				newexpr.append(exp);
			}else{ // otherwise, it is a variable
				newexpr.append(exp);
			}
		}
		return newexpr.toString();
	}
	
	@Test
	public void tempTest(){
		String expr = "(#{account}='baidu-?????2132840-030'||#{account}='baidu-?????2132840-034'||#{account}='baidu-?????2132840-050'||#{account}='baidu-?????2132840-014'||#{account}='baidu-?????2132840-018')&&(#{campaignId}=10472001||#{campaignId}=10472709||#{campaignId}=10028402||#{campaignId}=10028450||#{campaignId}=10028477||#{campaignId}=10028498||#{campaignId}=10028531||#{campaignId}=10028534||#{campaignId}=9979057||#{campaignId}=9979069||#{campaignId}=9979078||#{campaignId}=9979102||#{campaignId}=10455086||#{campaignId}=10455125||#{campaignId}=10455194||#{campaignId}=9936047||#{campaignId}=9936104||#{campaignId}=9936107||#{campaignId}=9936110||#{campaignId}=9941907||#{campaignId}=9942021||#{campaignId}=9942024||#{campaignId}=9942048||#{campaignId}=9942054)";
		StringBuffer newexpr = new StringBuffer();
		ExpressionIterator ei = new ExpressionIterator(expr, true);
		while(ei.hasNext()){
			String exp = ei.next();
			if(OperatorEnum.isDefine(exp)){ // if it is a symbol
				newexpr.append(exp);
			}else if(exp.charAt(0) == OperatorEnum.SINGLE_QUOTE.getChar().charAt(0) && exp.charAt(exp.length() - 1) == OperatorEnum.SINGLE_QUOTE.getChar().charAt(0)){ // if it is a literal value
				String tempexp = exp.replaceAll("'", "");
				if(BeanUtil.isNum(tempexp)){
					exp = tempexp;
				}
				newexpr.append(exp);
			}else{ // otherwise, it is a variable
				newexpr.append("#{" + exp + "}");
			}
		}
		System.out.println(newexpr);
	}
	
	private void execute(String expStr, Map<String, String> vars, Object expected) {
		try{
			Calculator c = new Calculator(expStr);
			Object result = c.execute(vars);
			Assert.assertEquals(expected, result);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
}
