import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mariu on 05.12.2016.
 */
public class GenerateSQLTest {

    private final String TABLENAME = "testtable";
    GenerateSQL generateSQL;

    @Before
    public void setUp() throws Exception {
        generateSQL = new GenerateSQL();
    }

    @Test
    public void createTableSQLFromArray() {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Navn", "Adresse", "Alder", "Status"});
        rows.add(new String[]{"VARCHAR", "VARCHAR", "INT", "VARCHAR"});
        rows.add(new String[]{"128", "128", "3", "128"});

        String createdSQL = generateSQL.createTableSQLFromArray(rows, "testtable");
        String testSQL = "CREATE TABLE testtable ( Navn VARCHAR(128), Adresse VARCHAR(128), Alder INT(3), Status VARCHAR(128))";
        assertEquals(createdSQL, testSQL);
    }

    @Test
    public void createInsertintoPreparedStatement() {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Karl", "Kongeveien", "32", "gift"});

        String insertintoSQL = generateSQL.createInsertintoPreparedStatement(rows, "testtable");
        String testSQL = "INSERT INTO testtable VALUES (?,?,?,?)";
        assertEquals(insertintoSQL, testSQL);
    }

    @Test
    public void showTableSQL() throws SQLException {
        try(Connection con = new H2Connection().getConnection();
            Statement statement = con.createStatement()){

            statement.executeUpdate("DROP TABLE IF EXISTS " + "testtable");
            statement.execute("CREATE TABLE testtable ( Navn VARCHAR(128), Adresse VARCHAR(128), Alder INT(3), status VARCHAR(128))");
            statement.executeUpdate("INSERT INTO testtable VALUES ('Karl','Kongeveien','32','gift')");

            assertEquals(generateSQL.getShowTableSQL(TABLENAME, con) , "SELECT Navn,Adresse,Alder,status FROM testtable");
        }
    }
}