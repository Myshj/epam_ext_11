package orm.repository;

import org.apache.commons.lang3.reflect.FieldUtils;
import orm.Model;
import orm.OrmFieldUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

final class InsertCommand<T extends Model> {
    private PreparedStatement statement;

    private Map<Field, Integer> queryParametersMap;

    InsertCommand(
            Class<T> clazz,
            Connection connection
    ) throws SQLException {
        queryParametersMap = OrmFieldUtils.getUpdateMapping(clazz);

        statement = connection.prepareStatement(
                String.format(
                        "INSERT INTO %s (%s) VALUES (%s);",
                        OrmFieldUtils.getTableName(clazz),
                        String.join(
                                ", ",
                                queryParametersMap.keySet().stream().sorted(
                                        Comparator.comparing(f -> queryParametersMap.get(f))
                                ).map(
                                        OrmFieldUtils.getObjectToRelationalMapping(clazz)::get
                                ).collect(
                                        Collectors.toList()
                                )
                        ),
                        String.join(
                                ", ",
                                Collections.nCopies(
                                        queryParametersMap.size(),
                                        "?"
                                )
                        )
                ),
                Statement.RETURN_GENERATED_KEYS
        );
    }

    void execute(T entity) throws SQLException {
        queryParametersMap.forEach(
                (f, n) -> {
                    try {
                        statement.setObject(n, FieldUtils.readField(f, entity, true).toString());
                    } catch (IllegalAccessException | SQLException e) {
                        //e.printStackTrace();
                    }
                }
        );
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();
        rs.first();
        try {
            FieldUtils.writeField(entity.getId(), "value", rs.getLong(1), true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //return rs.getLong(1);
    }
}
