import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by rikmar15 on 30.11.2016.
 * @author  Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class DBConnection implements ConnectionProvider {
    private Connection con;

    public Connection getConnection() {
        Properties prop = new Properties();
        try(InputStream input = new FileInputStream("config.properties")){
            prop.load(input);

            MysqlDataSource ds = new MysqlDataSource();
            ds.setDatabaseName(prop.getProperty("dbname"));
            ds.setServerName(prop.getProperty("servername"));
            ds.setUser(prop.getProperty("dbuser"));
            ds.setPassword(prop.getProperty("dbpassword"));

            con = ds.getConnection();

        } catch (IOException | SQLException e) {
            System.out.println("Could not connect to database. " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
