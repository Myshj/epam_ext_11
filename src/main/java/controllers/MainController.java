package controllers;

import models.Department;
import models.Employee;
import models.Task;
import models.commands.FilterEmployeesByDepartment;
import models.commands.FilterTasksByEmployee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import orm.ConnectionManager;
import orm.RepositoryManager;
import orm.repository.Repository;
import views.ModelListView;

import java.sql.SQLException;

public class MainController {
    private final Logger logger = LogManager.getLogger(MainController.class);
    private final RepositoryManager repositoryManager = RepositoryManager.INSTANCE;
    private final ConnectionManager connectionManager = ConnectionManager.INSTANCE;

    public void run() throws SQLException {
        logger.trace("Entering application.");

        Repository<Department> departmentRepository = repositoryManager.get(Department.class);
        logger.trace("Constructing department repository.");
        new ModelListView<>("Departments:", departmentRepository.getAll()).display();

        Repository<Employee> employeeRepository = repositoryManager.get(Employee.class);
        logger.trace("Constructing employee repository.");
        new ModelListView<>("Employees:", employeeRepository.getAll()).display();

        Repository<Task> taskRepository = repositoryManager.get(Task.class);
        logger.trace("Constructing task repository.");
        new ModelListView<>("Tasks:", taskRepository.getAll()).display();

        new ModelListView<>("Employees of first department: ", employeeRepository.filter(
                new FilterEmployeesByDepartment(
                        Employee.class,
                        connectionManager.get()
                ).withDepartment(departmentRepository.getById(1).get())
        )).display();


        logger.trace("Adding task for third employee.");
        taskRepository.save(
                new Task(
                        "Do something!",
                        employeeRepository.getById(3).get()
                )
        );
        new ModelListView<>("Tasks:", taskRepository.getAll()).display();

        new ModelListView<>("Tasks of third employee: ", taskRepository.filter(
                new FilterTasksByEmployee(
                        Task.class,
                        connectionManager.get()
                ).withEmployee(employeeRepository.getById(3).get())
        )).display();

        logger.trace("Adding temporary employee.");
        Employee toDelete = new Employee(
                "To delete",
                "to delete",
                "to_delete",
                departmentRepository.getById(1).get()
        );
        employeeRepository.save(toDelete);
        new ModelListView<>("Employees: ", employeeRepository.getAll()).display();

        logger.trace("Removing temporary employee");
        employeeRepository.delete(toDelete);
        new ModelListView<>("Employees: ", employeeRepository.getAll()).display();


        logger.trace("Leaving application.");
    }
}
