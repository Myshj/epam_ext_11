package orm.repository;

import orm.Model;
import orm.commands.GetEntityCommand;
import orm.commands.ListEntitiesCommand;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository<T extends Model> {

    private InsertCommand<T> insertCommand;
    private UpdateCommand<T> updateCommand;
    private DeleteCommand<T> deleteCommand;
    private GetByIdCommand<T> getByIdCommand;
    private GetAllCommand<T> getAllCommand;

    public Repository(Class<T> clazz, Connection connection) throws SQLException {
        insertCommand = new InsertCommand<>(clazz, connection);
        updateCommand = new UpdateCommand<>(clazz, connection);
        deleteCommand = new DeleteCommand<>(clazz, connection);
        getByIdCommand = new GetByIdCommand<>(clazz, connection);
        getAllCommand = new GetAllCommand<>(clazz, connection);
    }

    public Optional<T> getById(long id) {
        try {
            return get(getByIdCommand.withId(id));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public List<T> getAll() {
        try {
            return filter(getAllCommand);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    public void delete(T entity){
        deleteCommand.execute(entity);
    }

    public List<T> filter(ListEntitiesCommand<T> command) throws SQLException {
        return command.execute();
    }

    public Optional<T> get(GetEntityCommand<T> command) throws SQLException {
        return command.execute();
    }

    public void save(T entity) throws SQLException {
        if (entity.getId().get().isPresent()){
            updateCommand.execute(entity);
        } else {
            insertCommand.execute(entity);
        }
    }
}
