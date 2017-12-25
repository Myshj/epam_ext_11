package orm.fields;

import orm.Model;
import orm.RepositoryManager;

import java.util.Optional;

public final class ForeignKey<T extends Model> extends SimpleOrmField<T> {

    private Long id;
    private Class<T> clazz;

    public ForeignKey(Class<T> clazz, boolean nullable) {
        super(nullable);
        this.clazz = clazz;
        if (value == null){
            this.id = null;
        } else {
            this.id = value.getId().get().orElse(null);
        }
    }

    @Override
    protected void setValue(T value) {
        if (value == null){
            this.id = null;
            this.value = null;
        } else {
            this.value = value;
            this.id = value.getId().get().orElse(null);
        }
    }

    @Override
    public Optional<T> get() {
        if (id == null){
            return Optional.empty();
        } else if (value == null){
            value = RepositoryManager.INSTANCE.get(clazz).getById(id).orElse(null);
        }
        return Optional.ofNullable(value);
    }

    @Override
    public String toString() {
        return "" + (
                value == null ? null : value.getId().get().orElse(null)
        );
    }
}
