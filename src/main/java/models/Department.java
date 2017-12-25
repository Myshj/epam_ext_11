package models;

import orm.Model;
import orm.fields.StringField;
import orm.annotations.Entity;

@Entity(table = "departments")
public class Department extends Model {
    private StringField name = new StringField( true);
    private StringField phone = new StringField(false);

    public Department() {
    }

    public StringField getName() {
        return name;
    }

    public StringField getPhone() {
        return phone;
    }

    public Department(String name, String phone) {
        this.name.set(name);
        this.phone.set(phone);
    }

    @Override
    public String toString() {
        return "Department{" +
                "name=" + name +
                ", phone=" + phone +
                '}';
    }
}
