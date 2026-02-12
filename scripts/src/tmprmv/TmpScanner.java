package tmprmv;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TmpScanner {

    private final int diasAntiguedad;
    private final TmpReport report;

    public TmpScanner(int diasAntiguedad, TmpReport report) {
        this.diasAntiguedad = diasAntiguedad;
        this.report = report;
    }

    public void clean(Path dir) {

        Instant limite = Instant.now()
                .minus(diasAntiguedad, ChronoUnit.DAYS);

        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult preVisitDirectory(
                        Path dir,
                        BasicFileAttributes attrs) {
                    if (isProtected(dir)) {
                    	report.addProtected(dir);
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(  Path file,BasicFileAttributes attrs) {

                    try {
                        if (!attrs.isRegularFile()) 
                        {
                            return FileVisitResult.CONTINUE;
                        }

                        if (attrs.lastModifiedTime().toInstant().isBefore(limite)) 
                        {
                            long size = attrs.size();
                            Files.delete(file);
                            report.addFile(size);
                        }

                    } catch (AccessDeniedException e) {
                       
                    } catch (IOException e) {
                        report.addError(file);
                    }

                    return FileVisitResult.CONTINUE;
                }

              
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {

                    if (isProtected(file)) {
                        report.addProtected(file);
                        return FileVisitResult.CONTINUE;
                    }

                    report.addError(file);
                    return FileVisitResult.CONTINUE;
                }

            });

        } catch (IOException e) {
            System.err.println("Error recorriendo " + dir);
        }
    }

    private boolean isProtected(Path path) {
        String name = path.getFileName().toString();
        return name.startsWith("systemd-private-")
            || name.startsWith("snap-private");
    }
}

