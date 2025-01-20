package src.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Client {
    private final ClientConfig config;
    private final FileProcessingStrategy fileProcessingStrategy;

    public Client(ClientConfig config, FileProcessingStrategy fileProcessingStrategy) {
        this.config = config;
        this.fileProcessingStrategy = fileProcessingStrategy;
    }

    public void startMonitoring() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(config.getMonitoredDirectory());
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path fileName = (Path) event.context();

                        if (fileName.toString().endsWith(".properties")) {
                            processFile(fileName.toFile());
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processFile(File file) {
        try {
            File fullPath = new File(config.getMonitoredDirectory(), file.getName());
            String absolutePath = fullPath.getPath();
            Map<String, String> filteredMap = fileProcessingStrategy.processFile(new File(absolutePath),
                    config.getKeyFilterPattern());
            sendToServer(filteredMap, file.getName());

            fullPath.delete();
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendToServer(Map<String, String> filteredMap, String fileName) {
        try (Socket socket = new Socket(config.getServerAddress(), config.getServerPort());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject(fileName);
            oos.writeObject(filteredMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Client <configFilePath>");
            return;
        }

        ClientConfig config = loadConfig(args[0]);
        FileProcessingStrategy strategy = new PropertiesFileProcessingStrategy();
        Client client = new Client(config, strategy);
        client.startMonitoring();
    }

    private static ClientConfig loadConfig(String configFilePath) {
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            config.load(fis);
            return new ClientConfig.Builder()
                    .setMonitoredDirectory(config.getProperty("monitoredDirectory"))
                    .setKeyFilterPattern(config.getProperty("keyFilterPattern"))
                    .setServerAddress(config.getProperty("serverAddress"))
                    .setServerPort(Integer.parseInt(config.getProperty("serverPort")))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
