package orm.commands;

import orm.Model;
import orm.ResultSetToEntityConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DbCommand<T extends Model> {
    protected PreparedStatement statement;
    ResultSetToEntityConverter<T> converter;

    public DbCommand(
            Class<T> clazz,
            Connection connection,
            String sql
    ) throws SQLException {
        statement = connection.prepareStatement(sql);
        converter = new ResultSetToEntityConverter<>(clazz);
    }
}
