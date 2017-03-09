package org.berendeev.roma.smarttodo.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Product {
    public abstract int id();
    public abstract String name();
    public abstract boolean bought();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Product.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder name(String name);

        public abstract Builder bought(boolean bought);

        public abstract Product build();
    }
}
