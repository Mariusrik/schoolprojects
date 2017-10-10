import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Made to test ClientApplication
 */
public class ClientTest implements Client{

    private boolean isDone = false;
    private int counter = 0;


    /**
     * makes sure test is only run one loop.
     */
    public Object getServerResponse() throws IOException, ClassNotFoundException{
        counter++;
        if(counter == 3)
            return ".bye";
        return "ServerResponseTest";
    }

    public void sendAnswer(String message) throws IOException{
        System.out.println(message);
    }

    public boolean isDone(){
        return this.isDone;
    }

    public void stopClient(){
        this.isDone = true;
    }
}
