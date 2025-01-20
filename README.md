# Client-Server File Monitor

## Structure

project_root/
│
├── src/
│ ├── client/
│ │ ├── Client.java
│ │ ├── ClientConfig.java
│ │ └── FileProcessingStrategy.java
│ │ └── PropertiesFileProcessingStrategy.java
│ │
│ └── server/
│ ├── Server.java
│ ├── ServerConfig.java
│ └── FileReconstructionStrategy.java
│ └── PropertiesFileReconstructionStrategy.java
│ └── ClientHandlerCommand.java
│ └── Command.java
│
├── config/
│ ├── client_config.properties
│ └── server_config.properties
│
└── README.md

## Building the Project

javac -d bin src/client/_.java src/server/_.java

## Start the Server

java -cp bin src.server.Server config/server_config.properties

## Start the Client

java -cp bin src.client.Client config/client_config.properties

## Client Configuration

Location: _config/client_config.properties_

### Configurations:

_monitoredDirectory: Path to the directory that will be monitored for new Java properties files_
_keyFilterPattern: Regular expression pattern for filtering keys_
_serverAddress: Address of the corresponding server program_
_serverPort: Port number of the server_

## Server Configuration

Location: _config/server_config.properties_

### Configurations:

_outputDirectory: Path to the directory where reconstructed files will be written_
_port: Port number on which the server will listen for client connections_

## To modify these(server and client) configurations:

1. Open the respective configuration file (client_config.properties or server_config.properties) in a text editor.
2. Edit the values as needed, ensuring to maintain the correct format (key=value).
3. Save the file after making changes.
