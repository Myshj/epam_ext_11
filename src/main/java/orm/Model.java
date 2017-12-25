package orm;

import orm.fields.IntegerField;

public abstract class Model {
    public Model() {
    }

    private IntegerField id = new IntegerField(false);

    public IntegerField getId() {
        return id;
    }
}
