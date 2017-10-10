import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates SQL strings from parameters.
 * Should not handle Exceptions
 * Only return Strings
 *
 * Created by rikmar15 on 30.11.2016.
 * @author  Marius Rikheim (rikmar15)
 * @version 1.0
 */
public class GenerateSQL {

    /**
     * Creates a CREATE TABLE sql string from array.
     */
    public String createTableSQLFromArray(List<String[]> SQLMetaDataArray, String tablename){
        String tableSQL = "CREATE TABLE " + tablename + " ( ";
        for(int i = 0; i < SQLMetaDataArray.get(0).length; i++){
            tableSQL += SQLMetaDataArray.get(0)[i] + " ";
            tableSQL += SQLMetaDataArray.get(1)[i] + "(";
            tableSQL += SQLMetaDataArray.get(2)[i] + ")";
            if(SQLMetaDataArray.get(0).length > i+1)
                tableSQL += ", ";
        }
        tableSQL += ")";
        System.out.println("SQL created: \n" + tableSQL);
        return tableSQL;
    }


    /**
     * Creates prepared statement from array containing sql data
     */
    public String createInsertintoPreparedStatement(List<String[]> sqlArray, String tablename){
        //builds sql based on number of columns
        String sqlString = "INSERT INTO " + tablename + " VALUES (" ;
        for(int i = 0; i < sqlArray.get(0).length; i++ ){
            sqlString += "?";
            if(sqlArray.get(0).length > (i + 1)) {
                sqlString += ",";
            }
        } sqlString += ")";

        System.out.println("Prepared statement created: \n" + sqlString );
        return sqlString;
    }


    /**
     * Prints table to console.
     */
    public String getShowTableSQL(String tablename, Connection con) throws SQLException{
        String sqlSelectString = "SELECT ";
        try (ResultSet resultSet = con.getMetaData().getColumns(null, null, tablename, null)) {
            while (resultSet.next()) {
                sqlSelectString +=  resultSet.getString("COLUMN_NAME");
                sqlSelectString += ",";
            }
            //removes the last ","
            sqlSelectString = sqlSelectString.substring(0, sqlSelectString.length()-1);
            sqlSelectString += " FROM " + tablename;
        }
        return sqlSelectString;
    }
}
