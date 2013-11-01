package temp;

import java.io.File;

public class MainTemp {
	
	public static void main(String[] args) throws Exception {
		System.out.println(getStr() == "abc");
	}
	
	private static String getStr(){
		String ab = "ab";
		String c = "c";
		return ab + c;
	}
}