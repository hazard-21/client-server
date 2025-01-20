# Client-Server File Monitor

## Structure

client-server/  
│  
│── src/  
│ │── client/  
│ │ │── Client.java  
│ │ │── ClientConfig.java  
│ │ │── FileProcessingStrategy.java  
│ │ │── PropertiesFileProcessingStrategy.java  
│ │  
│ │── server/  
│ │ │── Server.java  
│ │ │── ServerConfig.java  
│ │ │── FileReconstructionStrategy.java  
│ │ │── PropertiesFileReconstructionStrategy.java  
│ │ │── ClientHandlerCommand.java  
│ │ │── Command.java  
│  
|── config/  
│ |── client_config.properties  
│ |── server_config.properties  
│  
|── README.md

# Setup
- Create an **input** directory in the project root. This is where the client will monitor for new properties files.
- Create an **output** directory in the project root. This is where the server will write reconstructed files.

## Building the Project

javac -d bin src/client/\*.java src/server/\*.java

## Start the Server

java -cp bin src.server.Server config/server_config.properties

## Start the Client

java -cp bin src.client.Client config/client_config.properties

## Client Configuration

Location: **config/client_config.properties**

### Configurations:

- **monitoredDirectory:** _Path to the directory that will be monitored for new Java properties files_
- **keyFilterPattern:** _Regular expression pattern for filtering keys_
- **serverAddress:** _Address of the corresponding server program_
- **serverPort:** _Port number of the server_

## Server Configuration

Location: **config/server_config.properties**

### Configurations:

- **outputDirectory:** _Path to the directory where reconstructed files will be written_
- **port:** _Port number on which the server will listen for client connections_

## To modify these(server and client) configurations:

1. Open the respective configuration file (client_config.properties or server_config.properties) in a text editor.
2. Edit the values as needed, ensuring to maintain the correct format (key=value).
3. Save the file after making changes.

Note: **To modify configurations, edit the respective properties files in the config directory. The monitoredDirectory and outputDirectory paths are relative to the project root by default.**
