package tmprmv;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Tmprmv {
	
	public static void main(String[] args) {
		Path path = Paths.get("/tmp");
		int dias = 3;
		
		TmpReport report = new TmpReport();
        TmpScanner scanner = new TmpScanner(dias, report);
        
		scanner.clean(path);
		report.print();
		
         
	}

}
