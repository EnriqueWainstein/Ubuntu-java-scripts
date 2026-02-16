package resourceCheck;

public class MemoryInfo {
   private long kbTotal;
   private long kbDisponible;
   
   
   
   public MemoryInfo(long kbTotal, long kbDisponible) {
	   this.kbTotal=kbTotal;
	   this.kbDisponible=kbDisponible;
	   
   }



   public long getKbTotal() {
	return kbTotal;
   }



   public long getKbUsado() {
	return kbDisponible;
   }
	
   
   public double getPorcentaje() {
	   
	   return 100.0 * (kbTotal - kbDisponible) / kbTotal;
	   
   }
	
	
}
