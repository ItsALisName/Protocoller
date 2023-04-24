package net.alis.protocoller.samples.authlib.properties;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.IndexedParam;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public class PropertyMap extends ForwardingMultimap<String, Property> implements ObjectSample {

    private final Multimap<String, Property> properties = LinkedHashMultimap.create();

    public PropertyMap() {
        Utils.checkClassSupportability(clazz(), "PropertyMap", false);
    }

    public PropertyMap(Object propertyMap) {
        Utils.checkClassSupportability(clazz(), "PropertyMap", false);
        for(Map.Entry<String, Collection<Object>> e : ((Multimap<String, Object>)new AccessedObject(propertyMap).readField(0, Multimap.class)).asMap().entrySet()) {
            for(Object o : e.getValue()) {
                this.properties.put(e.getKey(), new Property(o));
            }
            //this.properties.put((String) e.getKey(), new Property(e.getValue()));
        }
    }

    public @NotNull Multimap<String, Property> delegate() {
        return this.properties;
    }

    @Override
    public Object createOriginal() {
        Object pMap = null;
        Multimap<String, Object> pFMap = LinkedHashMultimap.create();
        for(Map.Entry<String, Collection<Property>> e : properties.asMap().entrySet())  {
            for(Property p : e.getValue()) {
                pFMap.put(e.getKey(), p.createOriginal());
            }
        }
        pMap = Reflect.callEmptyConstructor(clazz(), new IndexedParam[]{new IndexedParam<>(pFMap, 0)});
        return pMap;
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getPropertyMapClass();
    }

}
