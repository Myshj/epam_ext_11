package orm.repository;

import orm.Model;
import orm.OrmFieldUtils;
import orm.commands.ListEntitiesCommand;

import java.sql.Connection;
import java.sql.SQLException;

final class GetAllCommand<T extends Model> extends ListEntitiesCommand<T> {
    GetAllCommand(Class<T> clazz, Connection connection) throws SQLException {
        super(
                clazz, connection,
                String.format(
                        "SELECT * FROM %s;",
                        OrmFieldUtils.getTableName(clazz)
                )
        );
    }
}
