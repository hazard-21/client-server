package src.client;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class PropertiesFileProcessingStrategy implements FileProcessingStrategy {
    @Override
    public Map<String, String> processFile(File file, String keyFilterPattern) throws IOException {
        Properties props = new Properties();
        Map<String, String> filteredMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("#") || line.trim().isEmpty())
                    continue;
                int equalIndex = line.indexOf('=');
                if (equalIndex != -1) {
                    String key = line.substring(0, equalIndex).trim();
                    String value = line.substring(equalIndex + 1).trim();
                    props.setProperty(key, value);
                }
            }
        }

        Pattern pattern = Pattern.compile(keyFilterPattern);
        for (String key : props.stringPropertyNames()) {
            if (pattern.matcher(key).matches()) {
                filteredMap.put(key, props.getProperty(key));
            }
        }

        return filteredMap;
    }
}
