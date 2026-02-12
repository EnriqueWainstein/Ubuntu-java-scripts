package tmprmv;

import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
public class TmpReport {

	private long filesDeleted = 0;
    private long bytesFreed = 0;
    private final List<Path> errors = new ArrayList<>();
    private final List<Path> privado = new ArrayList<>();

    public void addFile(long size) {
        filesDeleted++;
        bytesFreed += size;
    }

    public void addError(Path file) {
        errors.add(file);
    }
    
    public void addProtected(Path file) {
    	privado.add(file);
    }

    public void print() {
        System.out.println("Limpieza completada");
        System.out.println("Archivos borrados: " + filesDeleted);
        System.out.printf("Espacio liberado: %.2f MB%n",
                bytesFreed / 1024.0 / 1024.0);

        if (!errors.isEmpty()) {
            System.out.println("Archivos con error:");
            errors.forEach(p -> System.out.println(" - " + p));
        }
        if (!privado.isEmpty()) {
        	System.out.println("Archivos privados");
        	privado.forEach(p -> System.out.println(" - " + p));
        }
    }
	
	
}
