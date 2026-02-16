package resourceCheck;

public class Formatter {

    public static String format(CpuInfo cpu, MemoryInfo memory) {

        return String.format("""
                ============================
                CPU Usado: %.2f%%
                Memoria Usado: %.2f%%
                ============================
                """,
                cpu.getPorcentajeUsado(),
                memory.getPorcentaje()
        );
    }
}
