package src.server;

import java.io.*;
import java.util.*;

public class PropertiesFileReconstructionStrategy implements FileReconstructionStrategy {
    @Override
    public void reconstructFile(String fileName, Map<String, String> filteredMap, String outputDirectory)
            throws IOException {
        Properties props = new Properties();
        props.putAll(filteredMap);
        try (FileOutputStream fos = new FileOutputStream(new File(outputDirectory, fileName))) {
            props.store(fos, null);
        }
    }
}
