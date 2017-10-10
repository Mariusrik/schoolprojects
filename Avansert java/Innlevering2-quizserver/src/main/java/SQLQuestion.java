import java.sql.Connection;

/**
 * Expands question lazily
 * Created by rikmar15 on 20-Nov-16.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class SQLQuestion implements Question {

    private String sql;
    private String question;
    private String answer;

    private String expandedQuestion;
    private String expandedAnswer;

    private Connection con;

    public SQLQuestion(String sql, String question, String answer, Connection con){
        this.con = con;
        this.sql = sql;
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion(){
        if(expandedQuestion == null)
            generateData();

        return expandedQuestion;
    }

    public String getAnswer(){
        if (expandedAnswer == null)
            generateData();

        return expandedAnswer;
    }

    /**
     * Expands the questions with column vales from database
     */
    private void generateData(){
        DBService db = new DBService();
        String[] columns = db.questionExpander(sql, con);

        this.expandedQuestion = String.format(this.question, columns);
        this.expandedAnswer = String.format(this.answer, columns);
    }
}