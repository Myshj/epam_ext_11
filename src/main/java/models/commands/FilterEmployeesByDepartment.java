package models.commands;

import models.Department;
import models.Employee;
import orm.OrmFieldUtils;
import orm.commands.ListEntitiesCommand;

import java.sql.Connection;
import java.sql.SQLException;

public class FilterEmployeesByDepartment extends ListEntitiesCommand<Employee> {

    public FilterEmployeesByDepartment withDepartment(Department department){
        try {
            statement.setLong(1, department.getId().get().orElse(0L));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public FilterEmployeesByDepartment(Class<Employee> clazz, Connection connection) throws SQLException {
        super(
                clazz, connection,
                String.format(
                        "SELECT * FROM %s WHERE department_id=?",
                        OrmFieldUtils.getTableName(clazz)
                )
        );
    }
}
