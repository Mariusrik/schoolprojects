import javax.imageio.IIOException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by mariu on 10-Dec-16.
 */
public class ClientApplication {

    private Client client;

    ClientApplication(){
        try{
            this.client = new ClientCommunication();
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    ClientApplication(Client clientProvider){
        this.client = clientProvider;
    }

    public void run() {
        try (Scanner in = new Scanner(System.in)) {
            //get server response
            System.out.println(client.getServerResponse());
            System.out.println();

            while (!client.isDone()) {
                //gets question
                System.out.println(client.getServerResponse());

                //answer question
                String message = in.nextLine();
                client.sendAnswer(message);

                //reads result
                String result = client.getServerResponse().toString();
                System.out.println(result);

                if (result.equals(".bye"))
                    client.stopClient();
            }

            //client.stopClient();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not connect client " + e.getMessage());
            e.printStackTrace();
        }
    }
}
