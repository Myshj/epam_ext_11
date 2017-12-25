package orm.repository;

import org.apache.commons.lang3.reflect.FieldUtils;
import orm.Model;
import orm.OrmFieldUtils;
import orm.commands.CommandWithNoReturn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;

final class UpdateCommand<T extends Model> extends CommandWithNoReturn<T> {


    public UpdateCommand(
            Class<T> clazz,
            Connection connection
    ) throws SQLException {

        super(
                clazz, connection,
                String.format(
                        "UPDATE %s SET %s WHERE id=?",
                        OrmFieldUtils.getTableName(clazz),
                        String.join(
                                ", ",
                                String.join(
                                        ", ",
                                        OrmFieldUtils.getUpdateMapping(clazz).keySet().stream().map(
                                                f -> OrmFieldUtils.getObjectToRelationalMapping(clazz).get(f) + "= ?"
                                        ).collect(Collectors.toList())
                                )
                        )
                )
        );
    }

    public final void execute(T entity) {
        queryParametersMap.forEach(
                (f, n) -> {
                    try {
                        statement.setObject(n, FieldUtils.readField(f, entity, true).toString());
                    } catch (IllegalAccessException | SQLException e) {
                        //e.printStackTrace();
                    }
                }
        );
        try {
            statement.setLong(queryParametersMap.size() + 1, entity.getId().get().get());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
