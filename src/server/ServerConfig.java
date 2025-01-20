package src.server;

public class ServerConfig {
    private final String outputDirectory;
    private final int port;

    private ServerConfig(Builder builder) {
        this.outputDirectory = builder.outputDirectory;
        this.port = builder.port;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public int getPort() {
        return port;
    }

    public static class Builder {
        private String outputDirectory;
        private int port;

        public Builder setOutputDirectory(String outputDirectory) {
            this.outputDirectory = outputDirectory;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public ServerConfig build() {
            return new ServerConfig(this);
        }
    }
}
