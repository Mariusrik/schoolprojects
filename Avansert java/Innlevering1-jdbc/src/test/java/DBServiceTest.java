import org.h2.jdbc.JdbcSQLException;
import org.junit.*;
import org.h2.tools.Server;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mariu on 03.12.2016.
 */
public class DBServiceTest {

    static Server server;
    DBService dbService;
    static H2Connection h2Connection;
    static Connection con;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final String TABLENAME = "testtable";

    @BeforeClass
    public static void setUpOnce() throws SQLException {
        h2Connection = new H2Connection();
        con = h2Connection.getConnection();
        System.out.println("Starting H2 server");
        server = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers").start();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Stopping H2 server");
        server.stop();
        con.close();
    }

    @Before
    public void setup() throws SQLException {
        dbService = new DBService();
        dbService.setConnectionProvider(h2Connection);
        Statement statement = con.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS " + TABLENAME);
        statement.execute("CREATE TABLE "+ TABLENAME +" ( Navn VARCHAR(128), Adresse VARCHAR(128), Alder INT(3), status VARCHAR(128))");
    }

    @After
    public void teardown() throws SQLException {
        String sqlDropTable = "DROP TABLE IF EXISTS " + TABLENAME;
        Statement statement = con.createStatement();
        statement.executeUpdate(sqlDropTable);
    }



    @Test
    public void copyFaultyFileTest() throws SQLException {

        dbService.copyFile("faultyEksempel.txt", TABLENAME);
        boolean tableExists = false;
        ResultSet tables = con.getMetaData().getTables(null, null, TABLENAME, null);
        if (tables.next())
            tableExists = true;
        assertFalse(tableExists);
    }

    @Test
    public void copyFileTest() throws SQLException {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Donald", "veien", "60"});
        rows.add(new String[]{"Anton", "veito", "50"});
        rows.add(new String[]{"Dolly", "veitre", "23"});
        dbService.copyFile("copyFileTest.txt",TABLENAME);

        ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM " + TABLENAME);
        compareResultsetwithArrayRow(resultSet, rows);
    }

    @Test
    public void insertRowsTest() throws SQLException {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Karl", "Kongeveien", "32", "gift"});
        rows.add(new String[]{"Per", "Svinglia", "29", "ugift"});
        dbService.insertRows(rows, TABLENAME);

        ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM " + TABLENAME);
        compareResultsetwithArrayRow(resultSet, rows);
    }

    private void compareResultsetwithArrayRow(ResultSet resultSet, ArrayList<String[]> rows) throws SQLException {
        int j = 0;
        while (resultSet.next()) {
            for (int i = 1; resultSet.getMetaData().getColumnCount() >= i; i++) {
                String addedRowValue = rows.get(j)[i - 1];
                String databaseRowValue = resultSet.getString(i);
                assertEquals(databaseRowValue, addedRowValue);
            }
            j++;
        }
    }

    @Test
    public void showTable() throws SQLException {
        PrintStream old = System.out;

        Statement statement = con.createStatement();
        statement.executeUpdate("INSERT INTO " + TABLENAME + " VALUES ('Karl','Kongeveien','32','gift')");

        System.setOut(new PrintStream(outContent));

        dbService.showTable(TABLENAME);
        String tableString = "Navn Adresse Alder status \r\n" + "Karl Kongeveien 32 gift \r\n";
        assertEquals(tableString, outContent.toString());

        System.out.flush();
        System.setOut(old);
    }
}