package models;

import orm.fields.ForeignKey;
import orm.Model;
import orm.fields.StringField;
import orm.annotations.Column;
import orm.annotations.Entity;

@Entity(table = "employees")
public class Employee extends Model {
    @Column(name = "last_name")
    private StringField lastName = new StringField( true);

    @Column(name = "first_name")
    private StringField firstName = new StringField( true);

    private StringField position = new StringField( true);

    @Column(name = "department_id")
    private ForeignKey<Department> department = new ForeignKey<>(Department.class,true);

    public Employee(String lastName, String firstName, String position, Department department) {
        this.lastName.set(lastName);
        this.firstName.set(firstName);
        this.position.set(position);
        this.department.set(department);
    }

    public Employee() {
    }

    public StringField getLastName() {
        return lastName;
    }

    public StringField getFirstName() {
        return firstName;
    }

    public StringField getPosition() {
        return position;
    }

    public ForeignKey<Department> getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "lastName=" + lastName +
                ", firstName=" + firstName +
                ", position=" + position +
                '}';
    }
}
