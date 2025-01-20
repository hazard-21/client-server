package src.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandlerCommand implements Command {
    private final Socket socket;
    private final FileReconstructionStrategy reconstructionStrategy;
    private final String outputDirectory;

    public ClientHandlerCommand(Socket socket, FileReconstructionStrategy reconstructionStrategy,
            String outputDirectory) {
        this.socket = socket;
        this.reconstructionStrategy = reconstructionStrategy;
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void execute() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            String fileName = (String) ois.readObject();
            @SuppressWarnings("unchecked")
            Map<String, String> filteredMap = (Map<String, String>) ois.readObject();
            reconstructionStrategy.reconstructFile(fileName, filteredMap, outputDirectory);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
