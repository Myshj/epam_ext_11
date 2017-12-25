package orm.commands;

import orm.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ListEntitiesCommand<T extends Model> extends QueryCommand<T> {
    public ListEntitiesCommand(
            Class<T> clazz,
            Connection connection,
            String sql
    ) throws SQLException {
        super(clazz, connection, sql);
    }

    public final List<T> execute() {
        List<T> result = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                converter.apply(rs).ifPresent(result::add);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
