package net.alis.protocoller.samples.authlib.properties;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.alis.protocoller.util.AccessedObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PropertyMap extends ForwardingMultimap<String, Property> {

    private final Multimap<String, Property> properties = LinkedHashMultimap.create();

    public PropertyMap() {}

    public PropertyMap(Object propertyMap) {
        for(Map.Entry<?, ?> e : ((Multimap<?, ?>)new AccessedObject(propertyMap).read(0, Multimap.class)).asMap().entrySet()) {
            this.properties.put((String) e.getKey(), new Property(e.getValue()));
        }
    }

    public @NotNull Multimap<String, Property> delegate() {
        return this.properties;
    }

}
