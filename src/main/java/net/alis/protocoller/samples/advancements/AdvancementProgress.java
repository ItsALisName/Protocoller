package net.alis.protocoller.samples.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.alis.protocoller.plugin.memory.ClassAccessor;
import net.alis.protocoller.plugin.util.Utils;
import net.alis.protocoller.plugin.util.reflection.Reflect;
import net.alis.protocoller.util.AccessedObject;
import net.alis.protocoller.util.ObjectSample;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class AdvancementProgress implements Comparable<AdvancementProgress>, ObjectSample {
    private Map<String, CriterionProgress> criteria;
    private String[][] requirements = new String[0][];

    public AdvancementProgress(Object advProgress) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        AccessedObject object = new AccessedObject(advProgress);
        Map<String, Object> crt = object.readField(0, Map.class);
        this.criteria = new HashMap<>();
        if(crt != null && crt.size() > 0) {
            for(Map.Entry<String, Object> e : crt.entrySet()) {
                this.criteria.put(e.getKey(), new CriterionProgress(e.getValue()));
            }
        }
        this.requirements = object.readField(0, ClassAccessor.arrayOfClass(String[].class));
    }

    private AdvancementProgress(Map<String, CriterionProgress> criteria) {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.criteria = criteria;
    }

    public AdvancementProgress() {
        Utils.checkClassSupportability(clazz(), this.getClass().getSimpleName(), false);
        this.criteria = Maps.newHashMap();
    }

    public Map<String, CriterionProgress> getCriteria() {
        return criteria;
    }

    public void setCriteria(Map<String, CriterionProgress> criteria) {
        this.criteria = criteria;
    }

    public String[][] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[][] requirements) {
        this.requirements = requirements;
    }

    public void update(Map<String, Criterion> criteria, String[][] requirements) {
        Set<String> set = criteria.keySet();
        this.criteria.entrySet().removeIf((progressEntry) -> !set.contains(progressEntry.getKey()));
        for (String s : set) {
            if (!this.criteria.containsKey(s)) {
                this.criteria.put(s, new CriterionProgress());
            }
        }
        this.requirements = requirements;
    }

    public boolean isDone() {
        if (this.requirements.length == 0) {
            return false;
        } else {
            for (String[] astring : this.requirements) {
                boolean flag = false;
                for (String s : astring) {
                    CriterionProgress criterionprogress = this.getCriterion(s);
                    if (criterionprogress != null && criterionprogress.isDone()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean hasProgress() {
        for (CriterionProgress criterionprogress : this.criteria.values()) {
            if (criterionprogress.isDone()) return true;
        }
        return false;
    }

    public boolean grantProgress(String criteria) {
        CriterionProgress criterionprogress = this.criteria.get(criteria);
        if (criterionprogress != null && !criterionprogress.isDone()) {
            criterionprogress.grant();
            return true;
        } else {
            return false;
        }
    }

    public boolean revokeProgress(String criteria) {
        CriterionProgress criterionprogress = this.criteria.get(criteria);
        if (criterionprogress != null && criterionprogress.isDone()) {
            criterionprogress.revoke();
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "AdvancementProgress{criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + "}";
    }

    @Nullable
    public CriterionProgress getCriterion(String criteria) {
        return this.criteria.get(criteria);
    }

    public float getPercent() {
        if (this.criteria.isEmpty()) {
            return 0.0F;
        } else {
            float f = (float) this.requirements.length;
            float f1 = (float) this.countCompletedRequirements();
            return f1 / f;
        }
    }

    @Nullable
    public String getProgressText() {
        if (this.criteria.isEmpty()) {
            return null;
        } else {
            int i = this.requirements.length;
            if (i <= 1) {
                return null;
            } else {
                int j = this.countCompletedRequirements();
                return j + "/" + i;
            }
        }
    }

    private int countCompletedRequirements() {
        int i = 0;
        for (String[] astring : this.requirements) {
            boolean flag = false;
            for (String s : astring) {
                CriterionProgress criterionprogress = this.getCriterion(s);
                if (criterionprogress != null && criterionprogress.isDone()) {
                    flag = true;
                    break;
                }
            }
            if (flag) ++i;
        }

        return i;
    }

    public Iterable<String> getRemainingCriteria() {
        List<String> list = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteria.entrySet()) {
            if (!entry.getValue().isDone()) list.add(entry.getKey());
        }

        return list;
    }

    public Iterable<String> getCompletedCriteria() {
        List<String> list = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> entry : this.criteria.entrySet()) {
            if (entry.getValue().isDone()) list.add(entry.getKey());
        }
        return list;
    }

    @Nullable
    public Date getFirstProgressDate() {
        Date date = null;
        for (CriterionProgress criterionprogress : this.criteria.values()) {
            if (criterionprogress.isDone() && (date == null || criterionprogress.getObtained().before(date))) {
                date = criterionprogress.getObtained();
            }
        }
        return date;
    }

    public int compareTo(@NotNull AdvancementProgress progress) {
        Date date = this.getFirstProgressDate();
        Date date1 = progress.getFirstProgressDate();
        if (date == null && date1 != null) {
            return 1;
        } else if (date != null && date1 == null) {
            return -1;
        } else {
            return date == null ? 0 : date.compareTo(date1);
        }
    }

    @Override
    public Object createOriginal() {
        Map<String, Object> crt = new HashMap<>();
        for(Map.Entry<String, CriterionProgress> e : this.criteria.entrySet()) {
            crt.put(e.getKey(), e.getValue().createOriginal());
        }
        AccessedObject f = new AccessedObject(Reflect.classNewInstance(clazz()));
        f.write(0, crt);
        if(this.requirements != null) {
            f.write(0, this.requirements);
        }
        return f.getOriginal();
    }

    public static Class<?> clazz() {
        return ClassAccessor.get().getMinecraftAdvancementProgress();
    }
}
