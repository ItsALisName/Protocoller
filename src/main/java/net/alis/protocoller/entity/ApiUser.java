package net.alis.protocoller.entity;

import net.alis.protocoller.ProtocollerApi;

public interface ApiUser {

    ProtocollerApi api();

    String getName();

    String getVersion();

    String[] getAuthors();

    boolean equals(Object object);

    boolean equals(ApiUser user);

}
