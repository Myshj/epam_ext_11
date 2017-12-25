package orm.repository;

import orm.Model;
import orm.OrmFieldUtils;
import orm.commands.CommandWithNoReturn;

import java.sql.Connection;
import java.sql.SQLException;

final class DeleteCommand<T extends Model> extends CommandWithNoReturn<T> {
    @Override
    public final void execute(T entity) {
        try {
            statement.setLong(1, entity.getId().get().get());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DeleteCommand(Class<T> clazz, Connection connection) throws SQLException {
        super(
                clazz, connection,
                String.format(
                        "DELETE FROM %s WHERE id=?;",
                        OrmFieldUtils.getTableName(clazz)
                )
        );
    }
}
