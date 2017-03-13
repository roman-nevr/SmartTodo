package org.berendeev.roma.smarttodo.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ToDoCategory {
    public abstract int id();
    public abstract String name();
    public abstract boolean isExpanded();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_ToDoCategory.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder isExpanded (boolean isExpanded);

        public abstract ToDoCategory build();
    }
}
