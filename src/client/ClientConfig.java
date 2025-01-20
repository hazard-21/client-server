package src.client;

public class ClientConfig {
    private final String monitoredDirectory;
    private final String keyFilterPattern;
    private final String serverAddress;
    private final int serverPort;

    private ClientConfig(Builder builder) {
        this.monitoredDirectory = builder.monitoredDirectory;
        this.keyFilterPattern = builder.keyFilterPattern;
        this.serverAddress = builder.serverAddress;
        this.serverPort = builder.serverPort;
    }

    public String getMonitoredDirectory() {
        return monitoredDirectory;
    }

    public String getKeyFilterPattern() {
        return keyFilterPattern;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public static class Builder {
        private String monitoredDirectory;
        private String keyFilterPattern;
        private String serverAddress;
        private int serverPort;

        public Builder setMonitoredDirectory(String monitoredDirectory) {
            this.monitoredDirectory = monitoredDirectory;
            return this;
        }

        public Builder setKeyFilterPattern(String keyFilterPattern) {
            this.keyFilterPattern = keyFilterPattern;
            return this;
        }

        public Builder setServerAddress(String serverAddress) {
            this.serverAddress = serverAddress;
            return this;
        }

        public Builder setServerPort(int serverPort) {
            this.serverPort = serverPort;
            return this;
        }

        public ClientConfig build() {
            return new ClientConfig(this);
        }
    }
}
