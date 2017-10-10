import java.io.IOException;


/**
 * Created by mariu on 10-Dec-16.
 */
public interface Client {

        public Object getServerResponse() throws IOException, ClassNotFoundException;

        public void sendAnswer(String message) throws IOException;

        public boolean isDone();

        public void stopClient();
}
