import controllers.MainController;

import java.sql.SQLException;

abstract class Main {


    public static void main(String[] args) throws SQLException {
        new MainController().run();
    }
}
