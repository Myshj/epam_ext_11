package models;

import orm.Model;
import orm.annotations.Column;
import orm.annotations.Entity;
import orm.fields.ForeignKey;
import orm.fields.StringField;

@Entity(table = "tasks")
public class Task extends Model {
    private StringField description = new StringField(true);

    @Column(name = "employee_id")
    private ForeignKey<Employee> employee = new ForeignKey<>(Employee.class, true);

    public Task(
            String description,
            Employee employee
    ) {
        this.description.set(description);
        this.employee.set(employee);
    }

    public Task() {
    }

    public StringField getDescription() {
        return description;
    }

    public ForeignKey<Employee> getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description=" + description +
                ", employee_id=" + employee.get().orElse(null).getId().get() +
                '}';
    }
}
