package net.alis.protocoller.event.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketEventHandler {

    PacketEventPriority eventPriority() default PacketEventPriority.NORMAL;

    boolean ignoreCancelled() default false;

}
