import com.j256.ormlite.logger.LocalLog;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Dette er ment som en enkel demonstrasjon av ORM
 * Exeption handling er heller ikke gjort mye ut av ettersom dette ikke er produksjonskode.
 * Går ut ifra at det er nok for oppgaven å bare vise at man får til CRUD med ORMlite og at hoved-vektingen går på innleveringene
 *
 * Created by rikmar15 on 07.11.2016.
 */
public class Main {
    public static void main(String args[]) {
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
        try{
            CRUDExample ce = new CRUDExample();
            ce.showcase();
          
            ce.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
