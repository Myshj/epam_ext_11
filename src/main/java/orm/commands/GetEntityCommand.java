package orm.commands;

import orm.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class GetEntityCommand<T extends Model> extends QueryCommand<T> {
    public GetEntityCommand(
            Class<T> clazz,
            Connection connection,
            String sql
    ) throws SQLException {
        super(clazz, connection, sql);
    }

    public final Optional<T> execute() throws SQLException {
        ResultSet rs = statement.executeQuery();
        return rs.first() ? converter.apply(rs) : Optional.empty();
    }
}
