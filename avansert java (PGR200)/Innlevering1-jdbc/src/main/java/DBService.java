

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikmar15 on 30.11.2016.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class DBService {
    private FileReader fileReader = new FileReader();
    private ConnectionProvider connection = new DBConnection();
    private GenerateSQL generateSQL = new GenerateSQL();

    /**
     * Creates database table with values and metadata from file.
     *
     * @param filename  name of the file containing metadata and data
     * @param tablename name of the table to be created
     */
    public void copyFile(String filename, String tablename) {
        //metadata and data read from file
        List<List<String[]>> sqlMetaRowArrays = fileReader.readTextFile(filename);
        List<String[]> metadata = sqlMetaRowArrays.get(0);
        List<String[]> rows = sqlMetaRowArrays.get(1);

        try (Connection con = connection.getConnection();
             Statement statement = con.createStatement()) {

            //drops the table before creating it.
            statement.executeUpdate("DROP TABLE IF EXISTS " + tablename);
            //Creates the table
            statement.executeUpdate(generateSQL.createTableSQLFromArray(metadata, tablename));
            //Inserts values into table.
            insertRows(rows, tablename);

        } catch (SQLException e) {
            System.out.println("Could not Create database from file " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Insetts rows to table from ArrayList with table rows.
     * @param rows ArrayList<String[]> table rows
     * @param tablename String name of table
     */
    public void insertRows(List<String[]> rows, String tablename) {
        String insertSQL = generateSQL.createInsertintoPreparedStatement(rows, tablename);
        executeWithParameters(insertSQL, rows);
        System.out.println("Database updated");
    }


    /**
     * Executes prepared statement with parameters.
     *
     * @param sql      prepared statement sql
     * @param sqlArray Array containing parameters.
     */
    private void executeWithParameters(String sql, List<String[]> sqlArray) {
        System.out.println("Adding Values:");
        try (Connection con = connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (String[] row : sqlArray) {
                for (int i = 0; i < row.length; i++)
                    ps.setString(1 + i, row[i]);
                System.out.println(ps.toString());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Could not execute prepared statements with values " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Prints table to console.
     */
    public void showTable(String tablename) {
        try (Connection con = connection.getConnection();
             Statement statement = con.createStatement()
        ) {
            String sqlSelectString = generateSQL.getShowTableSQL(tablename, con);
            printResultset(statement.executeQuery(sqlSelectString));

        } catch (SQLException e) {
            System.out.println("Could not print table to console. " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Prints resultSet table to console.
     *
     * @param resultSet table resultset
     */
    private void printResultset(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            System.out.print(metaData.getColumnName(i) + " ");
        }
        System.out.println();
        while (resultSet.next()) {
            for (int i = 1; metaData.getColumnCount() >= i; i++)
                System.out.print(resultSet.getString(i) + " ");
            System.out.println();
        }
    }

    public SQLTable getTable(String tablename){
        return new SQLTable(tablename);
    }

    public void setConnectionProvider(ConnectionProvider connectionProvider) {
        connection = connectionProvider;
    }
}
