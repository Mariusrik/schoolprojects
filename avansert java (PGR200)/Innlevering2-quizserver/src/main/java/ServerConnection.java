import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;

/**
 * Handles connections with I/O from/to client
 * <p>
 * Created by rikmar15 on 20-Nov-16.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class ServerConnection implements Runnable {

    private Socket clientSocket;
    private Connection con;
    private final int NUMBER_OF_QUESTIONS = 12;

    public ServerConnection(Socket clientSocket, Connection con) {
        this.clientSocket = clientSocket;
        this.con = con;
    }

    /**
     * I/O form ClientCommunication
     */
    public void run() {

        try (ObjectInputStream input =
                     new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output =
                     new ObjectOutputStream(clientSocket.getOutputStream())) {
            output.flush();
            output.writeObject("You are connected to the server.");

            clientCommunication(output, input);

            System.out.println("Connection closed");
        } catch (SocketException s) {
            System.out.println("Lost connection to client. " + s.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    private void clientCommunication(ObjectOutputStream output, ObjectInputStream input) throws IOException, ClassNotFoundException {
        QuizPoints quizPoints = new QuizPoints();
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            String quizAnswer;
            QuestionTypeContainer questionTypeContainer = new QuestionTypeContainer();
            Question question = questionTypeContainer.generateRandomQuestion(con);

            //Gets a question
            String quizQuestion = question.getQuestion();
            //writes question to client
            output.writeObject(quizQuestion);

            //Gets the answer to the question
            quizAnswer = input.readObject().toString();
            if (quizAnswer.equals(".bye") || quizAnswer.equals(".quit")) {
                output.writeObject(".bye");
                break;
            }

            //Checks if question is correct
            boolean correctAnswer = question.getAnswer().toLowerCase().trim().equals(quizAnswer.toLowerCase().trim());
            if (correctAnswer) {
                quizPoints.addPoint();
                output.writeObject("Correct answer. You have #" + quizPoints.getPoints() + " points \n");
            } else
                output.writeObject("Wrong answer, answer is " + question.getAnswer() + "\n");

            output.flush();
        }
        output.writeObject("You got " + quizPoints.getPoints() + "points. \nDo you want to try again? y/n");
        if (input.readObject().toString().toLowerCase().equals("y")) {
            output.writeObject("New round!");
            clientCommunication(output, input);
        }
        output.writeObject(".bye");
    }
}
