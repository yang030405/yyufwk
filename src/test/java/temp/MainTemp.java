package temp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainTemp {
	
	public static void main(String[] args) throws Exception {
		byte[] bs = "1111111".getBytes();
		File file = new File("/Users/apple/Downloads/javacreated.csv");
		OutputStream os = new FileOutputStream(file);
		os.write(bs);
		os.flush();
		os.close();
		System.out.println("done");
	}
	
	
	
	
	
}