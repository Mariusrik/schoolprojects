import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Simple Main for testing functionality of Innlevering1
 *
 * Is supposed to print table object too according to the assignment.
 *
 * Created by rikmar15 on 30.11.2016.
 * @author  Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class Main {

    private static final String TABLENAME = "rikmartabell";

    public static void main( String[] args )  {

        //Create table
        DBService dbService = new DBService();
        dbService.copyFile("eksempel.txt", TABLENAME);

        //printing table from database
        System.out.println("_________\nPrinting table:");
        dbService.showTable(TABLENAME);

        //Creates an object of table with
        SQLTable sqlTable = dbService.getTable(TABLENAME);

        System.out.println("_________\nPrint table object...");
        showTableObjectInformation(sqlTable);

    }

    /**
     * Prints table OObject
     * @param sqlTable SQLTable array
     */
    private static void showTableObjectInformation(SQLTable sqlTable){
        System.out.println("Metadata:\n__________");
        System.out.println(sqlTable.getMetaDataObject().toString());

        ArrayList<ArrayList<Object>> tableRows = sqlTable.getTableRows();
        System.out.println("\nData:\n______");
        for(ArrayList<Object> rows : tableRows){
            System.out.println(rows);
        }
    }
}
