import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by rikmar15 on 20-Nov-16.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class DBConnector {
    private final String dbURL = "jdbc:sqlite:db\\innlevering2db.db";

    public Connection connect() {

        Connection con = null;
        try {
            con = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            System.out.println("Could not connect to database. " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Database connection established.");
        return con;
    }
}
