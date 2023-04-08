package net.alis.protocoller.samples.network.chat;

import net.alis.protocoller.plugin.data.ClassesContainer;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;

import javax.annotation.Nullable;
import java.util.BitSet;

public class FilterMask implements ObjectSample {

    public static final FilterMask PASS_THROUGH = new FilterMask(new BitSet(0), FilterMaskType.PASS_THROUGH);
    
    private BitSet mask;
    private FilterMaskType type;

    public FilterMask(Object filterMask) {
        AccessedObject object = new AccessedObject(filterMask);
        this.mask = object.read(0, BitSet.class);
        this.type = FilterMaskType.getById(((Enum<?>)object.read(0, ClassesContainer.get().getFilterMaskTypeEnum())).ordinal());
    }

    public FilterMask(BitSet mask, FilterMaskType type) {
        this.mask = mask;
        this.type = type;
    }

    public FilterMask(BitSet mask) {
        this.mask = mask;
        this.type = FilterMaskType.PARTIALLY_FILTERED;
    }

    public FilterMask(int i) {
        this(new BitSet(i), FilterMaskType.PARTIALLY_FILTERED);
    }

    public void setFiltered(int index) {
        this.mask.set(index);
    }

    public BitSet getMask() {
        return mask;
    }

    public void setMask(BitSet mask) {
        this.mask = mask;
    }

    public FilterMaskType getType() {
        return type;
    }

    public void setType(FilterMaskType type) {
        this.type = type;
    }

    @Nullable
    public String apply(String raw) {
        String s;
        switch (this.type) {
            case PASS_THROUGH:
                s = raw;
                break;
            case FULLY_FILTERED:
                s = null;
                break;
            case PARTIALLY_FILTERED:
                char[] achar = raw.toCharArray();
                for(int i = 0; i < achar.length && i < this.mask.length(); ++i) {
                    if (this.mask.get(i)) {
                        achar[i] = '#';
                    }
                }
                s = new String(achar);
                break;
            default:
                throw new IncompatibleClassChangeError();
        }
        return s;
    }

    @Override
    public Object createOriginal() {
        return Reflect.callConstructor(
                Reflect.getConstructor(ClassesContainer.get().getFilterMaskClass(), BitSet.class, ClassesContainer.get().getFilterMaskTypeEnum()),
                this.mask, this.type.original()
        );
    }
}
