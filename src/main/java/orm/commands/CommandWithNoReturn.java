package orm.commands;

import orm.Model;
import orm.OrmFieldUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public abstract class CommandWithNoReturn<T extends Model> extends DbCommand<T> {
    protected Map<Field, Integer> queryParametersMap;
    public CommandWithNoReturn(Class<T> clazz, Connection connection, String sql) throws SQLException {
        super(clazz, connection, sql);
        queryParametersMap = OrmFieldUtils.getUpdateMapping(clazz);
    }

    public abstract void execute(T entity);
}
