package orm.fields;

import java.util.Optional;

public abstract class SimpleOrmField<T> extends OrmField<T> {
    protected T value;

    @Override
    public Optional<T> get() {
        return Optional.ofNullable(value);
    }

    @Override
    public final SimpleOrmField<T> set(T value) {
        checkNullableConstraint(value);
        setValue(value);
        return this;
    }

    protected void setValue(T value) {
        this.value = value;
    }

    private void checkNullableConstraint(T value) {
        if (!isNullable() && value == null) {
            throw new NullPointerException("Tried to assign null to non-null field!");
        }
    }

    public SimpleOrmField(boolean nullable) {
        super(nullable);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
