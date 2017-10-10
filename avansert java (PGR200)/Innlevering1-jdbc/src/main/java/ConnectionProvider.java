import java.sql.Connection;

/**
 * Created by mariu on 03.12.2016.
 */
public interface ConnectionProvider {
    public Connection getConnection();
}
