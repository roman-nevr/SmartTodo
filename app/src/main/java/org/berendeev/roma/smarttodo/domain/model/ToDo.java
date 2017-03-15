package org.berendeev.roma.smarttodo.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ToDo {

    public static ToDo EMPTY = create(0, "", "", false, 0);

    public abstract int id();
    public abstract String name();
    public abstract String description();
    public abstract boolean isChecked();
    public abstract int categoryId();

    public abstract Builder toBuilder();

    @Override public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= id();
        h *= 1000003;
        h ^= name().hashCode();
        return h;
    }

    public static ToDo create(int id, String name, String description, boolean isChecked, int categoryId) {
        return builder()
                .id(id)
                .name(name)
                .description(description)
                .isChecked(isChecked)
                .categoryId(categoryId)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_ToDo.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder description(String description);

        public abstract Builder isChecked(boolean isChecked);

        public abstract Builder categoryId(int categoryId);

        public abstract ToDo build();
    }


}
