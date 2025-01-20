package src.server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    private static Server instance;
    private final ServerConfig config;
    private final ExecutorService executor;
    private final FileReconstructionStrategy reconstructionStrategy;

    private Server(ServerConfig config, FileReconstructionStrategy reconstructionStrategy) {
        this.config = config;
        this.executor = Executors.newFixedThreadPool(10);
        this.reconstructionStrategy = reconstructionStrategy;
    }

    public static synchronized Server getInstance(ServerConfig config,
            FileReconstructionStrategy reconstructionStrategy) {
        if (instance == null) {
            instance = new Server(config, reconstructionStrategy);
        }
        return instance;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {
            System.out.println("Server is listening on port " + config.getPort());
            while (true) {
                Socket socket = serverSocket.accept();
                Command command = new ClientHandlerCommand(socket, reconstructionStrategy, config.getOutputDirectory());
                executor.execute(command::execute);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Server <configFilePath>");
            return;
        }

        ServerConfig config = loadConfig(args[0]);
        FileReconstructionStrategy strategy = new PropertiesFileReconstructionStrategy();
        Server server = Server.getInstance(config, strategy);
        server.start();
    }

    private static ServerConfig loadConfig(String configFilePath) {
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            config.load(fis);
            return new ServerConfig.Builder()
                    .setOutputDirectory(config.getProperty("outputDirectory"))
                    .setPort(Integer.parseInt(config.getProperty("port")))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
