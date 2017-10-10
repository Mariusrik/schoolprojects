import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by mariu on 10-Dec-16.
 */
public class ClientApplicationTest {

    PrintStream old;

    @Before
    public void setUp() throws Exception {
        old = System.out;
        ByteArrayInputStream in = new ByteArrayInputStream("InputTest".getBytes());
        System.setIn(in);
    }

    @After
    public void tearDown() throws Exception {
        System.setIn(System.in);
        System.out.flush();
        System.setOut(old);
    }

    @Test
    public void runTest() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ClientTest clienTest = new ClientTest();
        ClientApplication clientApplication = new ClientApplication(clienTest);

        System.setOut(new PrintStream(outContent));

        clientApplication.run();

        //checks if the ClientApplication prints what it recieves from the client.
        assertEquals("ServerResponseTest\r\n" +
                "\r\n" +
                "ServerResponseTest\r\n" +
                "InputTest\r\n" +
                ".bye\r\n", outContent.toString());

        //checks if ClientApplication chacnges isDone to true when told to stop
        assertTrue(clienTest.isDone());

    }

}