package net.alis.protocoller.util.annotations;

import net.alis.protocoller.bukkit.enums.Version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface AddedSince {

    Version value();

}
