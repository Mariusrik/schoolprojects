import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
/**
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut ifra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene
 *
 * Created by rikmar15 on 07.11.2016.
 */
public class Connection {

    public ConnectionSource createConnection() throws SQLException{
        String databaseUrl = "jdbc:mysql://localhost:3306/world?useSSL=false";

        ConnectionSource connectionSource = new JdbcConnectionSource(
                databaseUrl, "root", "toor");
        return connectionSource;
    }

}
