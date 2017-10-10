import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rikmar15 on 30.11.2016.
 *
 * File reader that splits metadata and data into two ArrayList<String[]>
 * Reads files with format:
 * COLUMN_NAME/COLUMN_NAME/..._...
 * DATATYPE/DATATYPE/..._...
 * DATATYPE_SIZE/DATATYPE_SIZE/..._...
 * ROW_VALUE/ROW_VALUE/..._...
 * ROW_VALUE/ROW_VALUE/..._...
 * ..._.../..._.../..._...
 *
 * @author  Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class FileReader {

    /**
     * Reads from text file and creates an arraylist for metadata and one for rows.
     *
     * @param filename
     * @return Object[] Object array containing ArrayList with metadata and table rows
     */
    public List<List<String[]>> readTextFile(String filename) {
        List<String[]> metadata = new ArrayList<>();
        List<String[]> rows = new ArrayList<>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new java.io.FileReader("src/main/resources/" + filename))) {
            String line = br.readLine();
            while (line != null) {
                i++;
                if (i <= 3)
                    metadata.add(line.split("/"));
                else
                    rows.add(line.split("/"));

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Unable to read file. " + e.getMessage());
            e.printStackTrace();
        }

        return new ArrayList<List<String[]>>() {{add(metadata); add(rows);}};

    }
}