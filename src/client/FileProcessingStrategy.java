package src.client;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface FileProcessingStrategy {
    Map<String, String> processFile(File file, String keyFilterPattern) throws IOException;
}
