import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mariu on 20-Nov-16.
 */
public class QuizPointsTest {
    @Test
    public void addPoint() throws Exception {
        QuizPoints quizPoints = new QuizPoints();
        quizPoints.addPoint();

        assertEquals(quizPoints.getPoints(), 1);
        quizPoints.addPoint();
        assertEquals(quizPoints.getPoints(), 2);
    }


}