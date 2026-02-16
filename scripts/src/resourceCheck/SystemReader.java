package resourceCheck;

import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Files;

import java.io.IOException;

public class SystemReader {



	public static MemoryInfo leerMemoria() throws IOException {
		Path path = Path.of("/proc/meminfo");
		long memDisponible = 0;
		long memTotal = 0;

		try (Stream<String> lines = Files.lines(path)) {
			for (String line : (Iterable<String>) lines::iterator) 
			{

				if (line.startsWith("MemTotal:")) 
				{
					memTotal = ParseAKb(line);
				}    else if (line.startsWith("MemAvailable:")) 
				{
					memDisponible = ParseAKb(line);
				}

				if (memTotal > 0 && memDisponible > 0) {
					break; 
				}
			}
			return new MemoryInfo(memTotal,memDisponible);
		}

	}


	public static CpuInfo leerCpu() throws IOException, InterruptedException{
		long[] first = LeerStatCpu();
		Thread.sleep(500);
		long[] second = LeerStatCpu();

		long idleDiff = second[3] - first[3];
		long totalDiff = sum(second) - sum(first);

		double uso = 100.0 * (totalDiff - idleDiff) / totalDiff;

		return new CpuInfo(uso);
	}




	private static long[] LeerStatCpu() throws IOException {
		String line = Files.readAllLines(Path.of("/proc/stat")).get(0);
		String[] parts = line.split("\\s+");

		long[] values = new long[parts.length - 1];

		for (int i = 1; i < parts.length; i++) 
		{
			values[i - 1] = Long.parseLong(parts[i]);
		}

		return values;
	}

	private static long sum(long[] arr) {
		long total = 0;
		for (long l : arr) total += l;
		return total;
	}

	private static long ParseAKb(String line) {
		return Long.parseLong(line.replaceAll("\\D+", ""));
	}

}
