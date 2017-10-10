import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by mariu on 09-Dec-16.
 */
public class QuestionTypeContainerTest {


    //Testing random result, so i just test that the object contains something.
    @Test
    public void generateRandomQuestion() throws Exception {
        Connection con = new DBConnector().connect();
        QuestionTypeContainer QTC = new QuestionTypeContainer();

        Question sqlQuestion = QTC.generateRandomQuestion(con);
        String answerTest = sqlQuestion.getAnswer();
        String questionTest = sqlQuestion.getQuestion();

        assertFalse(answerTest.isEmpty());
        assertFalse(questionTest.isEmpty());
    }

}