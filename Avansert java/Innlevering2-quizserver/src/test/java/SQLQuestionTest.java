import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by mariu on 20-Nov-16.
 */
public class SQLQuestionTest {

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
    public void getQuestion() throws Exception {

        SQLQuestion sqlQuestion = new SQLQuestion("SELECT name, released FROM album LIMIT 1", "Hvilket år kom albumet %1$s ut?", "%2$s", con);

        assertEquals(sqlQuestion.getQuestion(), "Hvilket år kom albumet Kill em all ut?");
    }

    @Test
    public void getAnswer() throws Exception {
        SQLQuestion sqlQuestion = new SQLQuestion("SELECT name, strftime('%Y', released) FROM album LIMIT 1", "Hvilket år kom albumet %1$s ut?", "%2$s", con);

        assertEquals(sqlQuestion.getAnswer(), "1983");
    }

}