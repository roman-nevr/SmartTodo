package org.berendeev.roma.smarttodo.domain.model;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class ToDoCategory {
    public abstract int id();
    public abstract String name();
    public abstract boolean isExpanded();
    public abstract List<ToDo> toDos();

    public abstract Builder toBuilder();

    public static ToDoCategory create(int id, String name, boolean isExpanded, List<ToDo> toDos) {
        return builder()
                .id(id)
                .name(name)
                .isExpanded(isExpanded)
                .toDos(toDos)
                .build();
    }

    @Override public int hashCode() {
        int h = 1;
        h *= 1000003;
        h ^= id();
        h *= 1000003;
        h ^= name().hashCode();
        return h;
    }

    public static Builder builder() {
        return new AutoValue_ToDoCategory.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder isExpanded(boolean isExpanded);

        public abstract Builder toDos(List<ToDo> toDos);

        public abstract ToDoCategory build();
    }
}
