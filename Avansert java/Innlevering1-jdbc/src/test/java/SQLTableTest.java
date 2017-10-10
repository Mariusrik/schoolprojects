import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mariu on 04.12.2016.
 */
public class SQLTableTest {
    private final String TABLENAME  = "TESTTABLE";
    SQLTable sqlTable;
    static H2Connection h2Connection;
    static Connection con;


    @BeforeClass
    public static void beforeClass(){
        h2Connection = new H2Connection();
        con = h2Connection.getConnection();
    }
    @AfterClass
    public static void afterClass() throws SQLException{
            con.close();
    }
    @Before
    public void setUp() throws Exception {
        Statement statement = con.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS " + TABLENAME);
        statement.execute("CREATE TABLE " + TABLENAME + " ( Navn VARCHAR(128), Adresse VARCHAR(128), Alder INT(3), status VARCHAR(128))");
        statement.executeUpdate("INSERT INTO testtable VALUES ('Karl','Kongeveien','32','gift')");
        statement.executeUpdate("INSERT INTO testtable VALUES ('Per','Stien','29','ugift')");

        sqlTable = new SQLTable(TABLENAME, h2Connection);
    }


    @Test
    public void getTableRows() throws Exception {
        ArrayList<ArrayList<Object>> tableobjectlist = sqlTable.getTableRows();
        assertEquals(tableobjectlist.toString(), "[[Karl, Kongeveien, 32, gift], [Per, Stien, 29, ugift]]");
    }

    @Test
    public void getMetaDataObject() throws Exception {
        TableMetaData tableMetaData = sqlTable.getMetaDataObject();

        String compareString = "Column Name: \n" +
                "[Navn, Adresse, Alder, status]\n" +
                "Type name: \n" +
                "[TESTTABLE, TESTTABLE, TESTTABLE, TESTTABLE]\n" +
                "Column size: \n" +
                "[128, 128, 10, 128]\n" +
                "Is nullable:\n" +
                "[1, 1, 1, 1]\n" +
                "Is autoincrement: \n" +
                "[NO, NO, NO, NO]\n" +
                "Is Generated Column\n" +
                "[not implemented, not implemented, not implemented, not implemented]";

        assertEquals(tableMetaData.toString(), compareString);
    }

}