package orm.repository;

import orm.Model;
import orm.OrmFieldUtils;
import orm.commands.GetEntityCommand;

import java.sql.Connection;
import java.sql.SQLException;

final class GetByIdCommand<T extends Model> extends GetEntityCommand<T> {

    //private long id;

    GetByIdCommand<T> withId(long id) {
        //this.id = id;
        try {
            statement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    GetByIdCommand(
            Class<T> clazz,
            Connection connection
    ) throws SQLException {
        super(
                clazz, connection,
                String.format(
                        "SELECT * from %s WHERE id=?;",
                        OrmFieldUtils.getTableName(clazz)
                )
        );
    }
}
