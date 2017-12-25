package orm;

import utils.ResourceManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum  ConnectionManager {
    INSTANCE;

    private Connection connection;

    ConnectionManager() {
        ResourceManager rm = ResourceManager.APPLICATION;
        try {
            Class.forName(
                    rm.get("db.driver")
            );
            connection = DriverManager.getConnection(
                    rm.get("db.url"),
                    rm.get("db.user"),
                    rm.get("db.password")
            );
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection get(){
        return connection;
    }
}
