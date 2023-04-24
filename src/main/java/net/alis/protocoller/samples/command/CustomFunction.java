package net.alis.protocoller.samples.command;

import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.managers.LogsManager;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.samples.resources.MinecraftKey;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomFunction implements ObjectSample {

    private CustomFunction.CommandEntry[] entries;
    private MinecraftKey id;

    public CustomFunction(Object customFunction) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(customFunction);
        Object[] ent = object.readField(0, ClassAccessor.arrayOfClass(ClassAccessor.get().getCustomFunctionEntryInterface()));
        if(ent != null && ent.length > 0) {
            this.entries = new CommandEntry[ent.length];
            for(int i = 0; i < ent.length; i++) {
                this.entries[i] = new CommandEntry(CommandEntry.clazz().cast(ent[i]));
            }
        }
        Object mKey = object.readField(0, MinecraftKey.clazz());
        if(mKey != null) this.id = new MinecraftKey(mKey);
    }

    public CustomFunction(MinecraftKey id, CustomFunction.CommandEntry[] entries) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.id = id;
        this.entries = entries;
    }

    public MinecraftKey getId() {
        return this.id;
    }

    public CustomFunction.CommandEntry[] getEntries() {
        return this.entries;
    }

    public void setEntries(CommandEntry[] entries) {
        this.entries = entries;
    }

    public void setId(MinecraftKey id) {
        this.id = id;
    }

    @Override
    public Object createOriginal() {
        List<Object> ents = new ArrayList<>();
        for(CommandEntry entry : entries) ents.add(entry.createOriginal());
        return Reflect.callConstructor(
                Reflect.getConstructor(clazz(), false, MinecraftKey.clazz(), ClassAccessor.get().getCustomFunctionEntryInterface()),
                this.id != null ? this.id.createOriginal() : null, ents.toArray(new Object[0])
        );
    }

    public static class CacheableFunction implements ObjectSample{
        public static final CustomFunction.CacheableFunction NONE = new CustomFunction.CacheableFunction((MinecraftKey) null);
        @Nullable private MinecraftKey id;
        private boolean resolved;
        private Optional<CustomFunction> function = Optional.empty();

        public CacheableFunction(Object cacheableFunction) {
            Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
            AccessedObject object = new AccessedObject(cacheableFunction);
            Object mKey = object.readField(0, MinecraftKey.clazz());
            if(mKey != null) this.id = new MinecraftKey(mKey);
            this.resolved = object.readField(0, boolean.class);
            Optional<Object> func = object.readField(0, Optional.class);
            if(func != null && func.isPresent()) this.function = Optional.of(new CustomFunction((Object) func.get()));
        }

        public CacheableFunction(@Nullable MinecraftKey id) {
            Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
            this.id = id;
        }

        public CacheableFunction(CustomFunction function) {
            Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
            this.resolved = true;
            this.id = null;
            this.function = Optional.of(function);
        }

        @Nullable
        public MinecraftKey getId() {
            return this.function.map((function) -> function.id).orElse(this.id);
        }

        public void setFunction(Optional<CustomFunction> function) {
            this.function = function;
        }

        public void setId(@Nullable MinecraftKey id) {
            this.id = id;
        }

        public boolean isResolved() {
            return resolved;
        }

        public void setResolved(boolean resolved) {
            this.resolved = resolved;
        }

        @Override
        public Object createOriginal() {
            if(this.function.isPresent()) {
                return Reflect.callConstructor(
                        Reflect.getConstructor(clazz(), false, CustomFunction.clazz()),
                        this.function.get().createOriginal()
                );
            } else {
                return Reflect.callConstructor(
                        Reflect.getConstructor(clazz(), false, MinecraftKey.clazz()),
                        this.id != null ? this.id.createOriginal() : null
                );
            }
        }

        public static Class<?> clazz() {
            return ClassAccessor.get().getCacheableFunctionClass();
        }

    }

    public static class CommandEntry implements ObjectSample {
        private Object parse$ParseResults$CommandListenerWrapper$;

        public CommandEntry(Object parseOrOriginal) {
            Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
            if(parseOrOriginal.getClass().equals(clazz())) {
                AccessedObject object = new AccessedObject(parseOrOriginal);
                this.parse$ParseResults$CommandListenerWrapper$ = object.readField(0, ClassAccessor.get().getParseResultsClass());
                return;
            }
            if(!parseOrOriginal.getClass().equals(ClassAccessor.get().getParseResultsClass())) {
                ExceptionBuilder.throwException(new UnsupportedOperationException("Class \"" + this.getClass().getName() + "\" can be created only with \"" + ClassAccessor.get().getParseResultsClass().getName() + "\""), true);
            }
            this.parse$ParseResults$CommandListenerWrapper$ = parseOrOriginal;
        }

        public Object getParse() {
            return parse$ParseResults$CommandListenerWrapper$;
        }

        public void setParse(Object parse) {
            this.parse$ParseResults$CommandListenerWrapper$ = parse;
        }

        @Override
        public Object createOriginal() {
            return Reflect.callConstructor(
                    Reflect.getConstructor(clazz(), false, ClassAccessor.get().getParseResultsClass()),
                    this.parse$ParseResults$CommandListenerWrapper$
            );
        }

        public static Class<?> clazz() {
            return ClassAccessor.get().getCustomFunctionCommandEntryClass();
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getCustomFunctionClass();
    }

}
