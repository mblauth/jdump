package jdump.agent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MallInfo {
    private final long arena;
    private final long ordblks;
    private final long smblks;
    private final long hblks;
    private final long hblkhd;
    private final long fsmblks;
    private final long uordblks;
    private final long fordbkls;
    private final long keepcost;

    public MallInfo(long arena, long ordblks, long smblks, long hblks, long hblkhd, long fsmblks,
                    long uordblks, long fordbkls, long keepcost) {
        this.arena = arena;
        this.ordblks = ordblks;
        this.smblks = smblks;
        this.hblks = hblks;
        this.hblkhd = hblkhd;
        this.fsmblks = fsmblks;
        this.uordblks = uordblks;
        this.fordbkls = fordbkls;
        this.keepcost = keepcost;
    }

    private static native MallInfo get0();

    private List<String> message() {
        List<String> result = new LinkedList<>();
        result.add("Non-mmaped space allocated (bytes): " + arena);
        result.add("Number of free chunks: " + ordblks);
        result.add("Number of free mmapped regions: " + smblks);
        result.add("Number of mmapped regions: " + hblks);
        result.add("Space allocated in mmapped regions (bytes): " + hblkhd);
        result.add("Space in freed fastbin blocks (bytes): " + fsmblks);
        result.add("Total allocated space (bytes): " + uordblks);
        result.add("Total free space (bytes): " + fordbkls);
        result.add("Top-most, releasable space (bytes): " + keepcost);
        return result;
    }

    public static void writeTo(String filename) throws IOException {
        loadNativeLibrary();
        var file = new File(filename);
        Files.write(file.toPath(), get0().message());
    }

    private static void loadNativeLibrary() throws IOException {
        try (var stream = MallInfo.class.getResourceAsStream("/libagent.so")) {
            File tempFile = File.createTempFile("libagent", ".so");
            Files.copy(Objects.requireNonNull(stream), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.load(tempFile.getAbsolutePath());
        }

    }
}
