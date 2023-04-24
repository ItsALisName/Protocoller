package net.alis.protocoller.samples.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import net.alis.protocoller.plugin.exception.ExceptionBuilder;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CriterionProgress implements ObjectSample {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
    @Nullable
    private Date obtained;

    public CriterionProgress() {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
    }

    public CriterionProgress(Object criterionProgress) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.obtained = new AccessedObject(criterionProgress).readField(0, Date.class);
    }

    public boolean isDone() {
        return this.obtained != null;
    }

    public void grant() {
        this.obtained = new Date();
    }

    public void revoke() {
        this.obtained = null;
    }

    @Nullable
    public Date getObtained() {
        return this.obtained;
    }

    public String toString() {
        return "CriterionProgress{obtained=" + (this.obtained == null ? "false" : this.obtained) + "}";
    }

    public JsonElement serializeToJson() {
        return this.obtained != null ? new JsonPrimitive(DATE_FORMAT.format(this.obtained)) : JsonNull.INSTANCE;
    }

    public static CriterionProgress fromJson(String json) {
        CriterionProgress criterionprogress = new CriterionProgress();
        try {
            criterionprogress.obtained = DATE_FORMAT.parse(json);
            return criterionprogress;
        } catch (ParseException parseexception) {
            return ExceptionBuilder.throwException(new JsonSyntaxException("Invalid datetime: " + json, parseexception), true);
        }
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getCriterionProgressClass();
    }

    @Override
    public Object createOriginal() {
        AccessedObject o = new AccessedObject(Reflect.callConstructor(Reflect.getConstructor(clazz(), false)));
        if(this.obtained != null) o.write(0, this.obtained);
        return o.getOriginal();
    }
}
