import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by mariu on 20-Nov-16.
 */
public class DBServiceTest {

    Connection con;
    @Before
    public void setUp() throws Exception {
        DBConnector dbConnector = new DBConnector();
        con = dbConnector.connect();
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

    @Test
    public void questionExpander() throws Exception {
        DBService dbService = new DBService();
        dbService.questionExpander("SELECT name, released FROM album LIMIT 1", con);

        String[] columns = { "Kill em all", "1983"};
        String [] testColumns = dbService.questionExpander("SELECT name, strftime('%Y', released) FROM album LIMIT 1", con);
        assertEquals(testColumns[1], columns[1]);
        assertEquals(testColumns[0], columns[0]);
    }

}