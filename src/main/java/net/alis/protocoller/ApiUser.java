package net.alis.protocoller;

public interface ApiUser {

    ProtocollerApi api();

    String getName();

    String getVersion();

    String[] getAuthors();

    boolean equals(Object object);

    boolean equals(ApiUser user);

}
