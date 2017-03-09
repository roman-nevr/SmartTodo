package org.berendeev.roma.smarttodo.domain.model;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
abstract public class PurchasingList {
    public abstract int id();
    public abstract List<Product> list();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_PurchasingList.Builder();
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(int id);

        public abstract Builder list(List<Product> list);

        public abstract PurchasingList build();
    }
}
