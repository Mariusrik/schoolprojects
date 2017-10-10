import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mariu on 03.12.2016.
 */
public class H2Connection  implements ConnectionProvider{
    Connection con;
    public Connection getConnection(){
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:~/test;DATABASE_TO_UPPER=FALSE", "sa", "" );

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
