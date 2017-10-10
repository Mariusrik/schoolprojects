import java.sql.*;
import java.util.ArrayList;

/**
 * The data in the object is lazily loaded, so initialization is very cheap
 *
 * Created by rikmar15 on 30.11.2016.
 *
 * @author Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class SQLTable {

    ConnectionProvider connection;
    private String tableName;

    private TableMetaData tableMetaData = null;
    private ArrayList<ArrayList<Object>> rows = null;

    /**
     * Default constructor uses the default connection provider
     * @param tableName name of the table to be created
     */
    public SQLTable(String tableName) {
        this.connection = new DBConnection();
        this.tableName = tableName;
        if(!tableExists())
            throw new IllegalArgumentException("No such table exists");
    }

    /**
     * Constructor that also sets a custom connectionProvider
     * @param tableName name of the table to be created
     * @param connectionProvider custom connectionProvider
     */
    public SQLTable(String tableName, ConnectionProvider connectionProvider) {
        this.connection = connectionProvider;
        this.tableName = tableName;
        if(!tableExists())
            throw new IllegalArgumentException("No such table exists");
    }

    /**
     * checks if table exists
     * @return boolean
     */
    private boolean tableExists() {
        try (Connection con = connection.getConnection();
        ) {
            DatabaseMetaData dbmeta = con.getMetaData();
            try (ResultSet resultSet = dbmeta.getTables(null, null, this.tableName, null);
            ) {
                if (resultSet.next())
                    return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets table data array.
     * @return ArrayList<ArrayList<Object>> table rows
     */
    public ArrayList<ArrayList<Object>> getTableRows() {
        if (rows == null)
            createTableArray();
        return rows;
    }

    /**
     * Returns the tables metadata.
     * @return TableMetaData metadata object
     */
    public TableMetaData getMetaDataObject() {
        if (tableMetaData == null)
            createTableMetaData();
        return tableMetaData;
    }

    /**
     * Creates a metadata object from a tablename.
     */
    private void createTableMetaData() {
        ResultSet resultSet;
        tableMetaData = new TableMetaData();
        try (Connection con = connection.getConnection()) {
            DatabaseMetaData dbmeta = con.getMetaData();
            resultSet = dbmeta.getColumns(null, null, this.tableName, null);

            String isGeneratedColumn;
            while (resultSet.next()) {
                if(dbmeta.getDatabaseProductName().equals("H2"))
                    isGeneratedColumn = "not implemented";
                else
                    isGeneratedColumn = resultSet.getString("IS_GENERATEDCOLUMN");
                tableMetaData.addColumn(resultSet.getString("COLUMN_NAME"), resultSet.getString("TABLE_NAME"), resultSet.getInt("COLUMN_SIZE"),
                        resultSet.getInt("NULLABLE"), resultSet.getString("IS_AUTOINCREMENT"), isGeneratedColumn);
            }
        } catch (SQLException e) {
            System.out.println("Could not create metadata. " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Creates ArrayList for a table containing ArrayList containing objects with table rows.
     * This loads the entire dataset into memory. Therefore be careful loading big tables.
     */
    private void createTableArray() {
        TableMetaData tableMetaData = getMetaDataObject();
        rows = new ArrayList<>();
        try (Connection con = connection.getConnection();
             Statement statement = con.createStatement()) {

            ResultSet resultSet = statement.executeQuery(tableSelectSQL(tableMetaData));
            while (resultSet.next()) {
                ArrayList<Object> row = new ArrayList<>();
                for (int i = 0; i < tableMetaData.getSize(); i++)
                    row.add(resultSet.getObject(tableMetaData.getColumnName().get(i)));
                rows.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Could not create table array from sql table. " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates table SQL from table metadata object.
     *
     * @return sqlSelectString
     */
    private String tableSelectSQL(TableMetaData tableMetaData) {
        String sqlSelectString = "SELECT ";
        for (int i = 0; i < tableMetaData.getSize(); i++) {
            sqlSelectString += tableMetaData.getColumnName().get(i);

            if (i < tableMetaData.getSize() - 1)
                sqlSelectString += ", ";
        }
        sqlSelectString += " FROM " + this.tableName;
        return sqlSelectString;
    }

    public void setConnectionProvider(ConnectionProvider connectionProvider){
        connection = connectionProvider;
    }
}
