package temp;

import java.util.Map;
import java.util.Map.Entry;

import com.yyu.fwk.util.CsvFileParser;


public class JDXX {
	public static void main(String[] args) throws Exception {
		String filePath = "/Users/apple/Downloads/jdxx/1-200W.csv";
		CsvFileParser parser = new CsvFileParser(filePath);
		int count = 0;
		while(parser.hasNext()){
			Map<String, String> data = null;
			try{
				data = parser.next();
			}catch(Exception e){
				System.out.println(e.getMessage());
				continue;
			}
			// insert into yy_jdxx(Address) values('Addressaa')
			String insertSql = "insert into yy_jdxx(thekeys) values(thevalues)";
			String keys = "";
			String values = "";
			boolean firstTime = true;
			for(Entry<String, String> entry : data.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				if(firstTime){
					keys += key;
					values += "'" + value + "'";
					firstTime = false;
				}else{
					keys += ", " + key;
					values += ", '" + value + "'";
				}
				String sql = insertSql.replaceAll("thekeys", keys).replaceAll("thevalues", values);
			}
			System.out.println(++count);
		}
		System.out.println(filePath + " inserts over.");
	}
}
