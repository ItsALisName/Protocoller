package net.alis.protocoller.bukkit.exceptions;

public class BannedApiUserException extends RuntimeException {

    public BannedApiUserException(String[] params, String[] authors) {
        super("The plugin \" " + params[0] + " tried to use the Api, although it is in the list of prohibited!" +
                "\n[Protocoller] User(Plugin) details: " +
                "\n[Protocoller] Name: " + params[0] +
                "\n[Protocoller] Version: " + params[1] +
                "\n[Protocoller] Authors: " + String.join(", ", authors)
        );
    }

}
