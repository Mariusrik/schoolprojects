import java.sql.*;


/**
 * Created by rikmar15 on 09.12.2016.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class DBService {

    /**
     * Gets columns from SQL
     * @param sql
     * @param con DBConnection
     * @return String[] Columns from from resultset
     */
    public String[] questionExpander(String sql, Connection con){
        ResultSet resultSet;

        try(Statement statement = con.createStatement())
        {
            resultSet = statement.executeQuery(sql);

            int colCnt = resultSet.getMetaData().getColumnCount();

            String[] columns = new String[colCnt];
            for (int i = 0; i < colCnt; i++)
                columns[i] = resultSet.getString(i+1);

            return columns;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}