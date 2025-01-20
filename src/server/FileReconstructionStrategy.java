package src.server;

import java.io.IOException;
import java.util.Map;

public interface FileReconstructionStrategy {
    void reconstructFile(String fileName, Map<String, String> filteredMap, String outputDirectory) throws IOException;
}
