package orm.commands;

import orm.Model;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class QueryCommand<T extends Model> extends DbCommand<T> {
    public QueryCommand(
            Class<T> clazz,
            Connection connection,
            String sql
    ) throws SQLException {
        super(clazz, connection, sql);
    }
}
