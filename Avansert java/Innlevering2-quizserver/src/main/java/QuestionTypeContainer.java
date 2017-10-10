import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;

/**
 * Container for Question types
 * Creates questions by selecting a random sql string[] containing
 * SQL gets column values
 * Question parameter uses format specifier for the question column
 * Answer uses format specifier for the column with an answer.
 *
 * Created by rikmar15 on 20.11.2016.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class QuestionTypeContainer {

    private ArrayList<String[]> questiontypes = new ArrayList<>();
    private Random rand;

    public QuestionTypeContainer(){
        rand = new Random();
        questiontypes.add(new String[]{"SELECT name, strftime('%Y', released) FROM album ORDER BY RANDOM() LIMIT 1", "Hvilket år kom albumet %1$s ut?", "%2$s"});
        questiontypes.add(new String[]{"SELECT name, strftime('%Y', released) FROM album ORDER BY RANDOM() LIMIT 1", "Hvilket album kom ut i %2$s?", "%1$s"});
        questiontypes.add(new String[]{"SELECT abv.a_name, abv.b_name FROM album_band_view abv WHERE abv.a_name IN " +
                "(SELECT abv.a_name FROM album_band_view abv GROUP BY a_name HAVING count(*) = 1)", "Hvilket band ga ut albumet %1$s?", "%2$s"});

        questiontypes.add(new String[]{"SELECT band.name, num_members FROM band_num_members bnm INNER JOIN band " +
                "ON (bnm.band_id = band.id) ORDER BY RANDOM() LIMIT 1", "Hvor mange medlemmer er det i bandet %1$s?", "%2$s"});

        questiontypes.add(new String[]{"SELECT alb.name, albt.name FROM album alb INNER JOIN album_track albt" +
                " ON (alb.id = albt.album_id) ORDER BY RANDOM() LIMIT 1", "Hvilket album var sangen %2$s på?", "%1$s"});
    }


    /**
     *
     * @param con Connection
     * @return SQLQuestion   Question
     **/
    public Question generateRandomQuestion(Connection con){
        String randomQuestionType[] = questiontypes.get(rand.nextInt(questiontypes.size()));

        Question questionHandler = new SQLQuestion(randomQuestionType[0], randomQuestionType[1], randomQuestionType[2], con);
        return questionHandler;
    }

}