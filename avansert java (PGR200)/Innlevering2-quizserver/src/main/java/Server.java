import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by rikmar15 on 20-Nov-16.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 *
 * Simple server
 */
public class Server {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public  void runServer(){

        DBConnector dbConnector = new DBConnector();
        Connection con = dbConnector.connect();

        try(ServerSocket serverSocket = new ServerSocket(8000)){
            System.out.println("Server starting.");

            while(true){
                System.out.println("waiting for connection");
                try {
                    Socket clientSocket = serverSocket.accept();
                    executorService.submit(new ServerConnection(clientSocket, con));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
