package net.alis.protocoller.bukkit.exceptions;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String configurationFileName, String pathNotFound) {
        super("The value on the path was not found!" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] File name: " + configurationFileName +
                "\n[Protocoller] Path to the value: " + pathNotFound
        );
    }

    public ConfigurationException(String configurationFileName, String path, String[] types) {
        super("The value from the config has the wrong type!" +
                "\n[Protocoller] Details: " +
                "\n[Protocoller] File name: " + configurationFileName +
                "\n[Protocoller] Path to the value: " + path +
                "\n[Protocoller] Expected: '" + types[0] + "'; Received: '" + types[1] + "';"
        );
    }

}
