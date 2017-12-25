package orm.fields;

public final class EnumField<T extends Enum<T>> extends SimpleOrmField<T> {
    private Class<T> clazz;
    public EnumField(Class<T> clazz, boolean nullable) {
        super(nullable);
        this.clazz = clazz;
    }
}
