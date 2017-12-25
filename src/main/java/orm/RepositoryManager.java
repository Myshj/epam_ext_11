package orm;

import orm.repository.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public enum  RepositoryManager {
    INSTANCE;

    private Map<Class, Repository> repositories = new HashMap<>();

    public <T extends Model> Repository<T> get(Class<T> clazz){
        try {
            Repository<T> r = new Repository<>(clazz, ConnectionManager.INSTANCE.get());
            repositories.putIfAbsent(clazz, r);
            return repositories.get(clazz);
        } catch (SQLException e) {
            //e.printStackTrace();
            return null;
        }
    }
}
