package resourceCheck;

public class ResourceCheckApp {

	public static void main(String[] args) throws Exception{
		
		
		
		while(true) {
			
			CpuInfo cpu = SystemReader.leerCpu();
			MemoryInfo memory = SystemReader.leerMemoria();
			
			System.out.println(Formatter.format(cpu,memory));
			
		}
		

	}

}
