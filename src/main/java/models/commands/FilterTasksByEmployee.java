package models.commands;

import models.Employee;
import models.Task;
import orm.OrmFieldUtils;
import orm.commands.ListEntitiesCommand;

import java.sql.Connection;
import java.sql.SQLException;

public class FilterTasksByEmployee extends ListEntitiesCommand<Task> {

    public FilterTasksByEmployee withEmployee(Employee employee) {
        try {
            statement.setLong(1, employee.getId().get().orElse(0L));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FilterTasksByEmployee(Class<Task> clazz, Connection connection) throws SQLException {
        super(
                clazz, connection,
                String.format(
                        "SELECT * FROM %s WHERE employee_id=?",
                        OrmFieldUtils.getTableName(clazz)
                )
        );
    }
}
