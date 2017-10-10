import java.util.ArrayList;

/**
 * Stores SQL table meta data
 * Created by rikmar15 on 30.11.2016.
 * @author  Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class TableMetaData {

    private ArrayList<String> COLUMN_NAME = new ArrayList<>();
    private ArrayList<String> TYPENAME = new ArrayList<>();
    private ArrayList<Integer> COLUMN_SIZE = new ArrayList<>();
    private ArrayList<Integer> NULLABLE = new ArrayList<>();
    private ArrayList<String> IS_AUTOINCREMENT = new ArrayList<>();
    private ArrayList<String> IS_GENERATEDCOLUMN = new ArrayList<>();

    private int size;


    public void addColumn(String columnName, String typeName, int columnSize, int nullable, String isAutoIncrement, String isGeneratedColumn) {
        size++;

        COLUMN_NAME.add(columnName);
        TYPENAME.add(typeName);
        COLUMN_SIZE.add(columnSize);
        NULLABLE.add(nullable);
        IS_AUTOINCREMENT.add(isAutoIncrement);
        IS_GENERATEDCOLUMN.add(isGeneratedColumn);
    }


    public ArrayList<String> getColumnName() {
        return COLUMN_NAME;
    }

    public ArrayList<String> getTypename() {
        return TYPENAME;
    }

    public ArrayList<Integer> getColumnSize() {
        return COLUMN_SIZE;
    }

    public ArrayList<Integer> getNullable() {
        return NULLABLE;
    }

    public ArrayList<String> getIsAutoIncrement() {
        return IS_AUTOINCREMENT;
    }

    public ArrayList<String> getIsGeneratedColumn() {
        return IS_GENERATEDCOLUMN;
    }

    public int getSize() {
        return size;
    }


    @Override
    public String toString() {
        return "Column Name: \n"+COLUMN_NAME + "\n" + "Type name: \n"+ TYPENAME + "\n" + "Column size: \n" + COLUMN_SIZE + "\n" + "Is nullable:\n" + NULLABLE + "\n" + "Is autoincrement: \n" + IS_AUTOINCREMENT  + "\n" + "Is Generated Column\n" + IS_GENERATEDCOLUMN;
    }
}
