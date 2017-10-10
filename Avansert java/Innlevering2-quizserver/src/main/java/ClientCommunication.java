import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by rikmar15 on 20-Nov-16.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */

public class ClientCommunication implements Client{

    private Socket serverContact;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    private boolean isDone = false;

    public ClientCommunication() throws IOException{
        serverContact = new Socket("127.0.0.1", 8000);
        output = new ObjectOutputStream(serverContact.getOutputStream());
        input = new ObjectInputStream(serverContact.getInputStream());
    }

    public Object getServerResponse() throws IOException, ClassNotFoundException{
        return input.readObject();
    }

    public void sendAnswer(String message) throws IOException{
        output.writeObject(message);
    }

    public boolean isDone(){
        return this.isDone;
    }

    public void stopClient(){
        this.isDone = true;
        try{
            if (output != null)
                output.close();
            if(input != null)
                input.close();
            if(serverContact != null)
                serverContact.close();
        }catch (IOException e){
            System.out.println("Problem closing client " + e.getMessage());
            e.printStackTrace();
        }
    }
}

