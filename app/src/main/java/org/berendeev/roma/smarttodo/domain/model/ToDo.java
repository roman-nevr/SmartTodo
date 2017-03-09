package org.berendeev.roma.smarttodo.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ToDo {
    public abstract int id();
    public abstract String name();
    public abstract String description();
    public abstract int categoryId();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_ToDo.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder description(String description);

        public abstract Builder categoryId(int categoryId);

        public abstract ToDo build();
    }
}
