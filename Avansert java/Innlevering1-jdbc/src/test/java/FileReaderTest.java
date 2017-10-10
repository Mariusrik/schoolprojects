import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

import static org.junit.Assert.*;

/**
 * Created by rikmar15 on 30.11.2016.
 * @author  Marius Rikheim (rikmar15)
 * @version 2.0
 */
public class FileReaderTest {


    @Test
    public void readTextFileTest() {
        String[] metavalues = {"1","2","3"};
        String[] rowValues = {"4","5","6"};
        FileReader reader = new FileReader();

        List<List<String[]>> rowAndMeta= reader.readTextFile("testText.txt");

        List<String[]> testMetadata = rowAndMeta.get(0);
        List<String[]> testRows = rowAndMeta.get(1);

        List<String[]> metadata = new ArrayList<>();
        metadata.add(metavalues);
        metadata.add(metavalues);
        metadata.add(metavalues);

        List<String[]> rows = new ArrayList<>();
        rows.add(rowValues);
        rows.add(rowValues);
        rows.add(rowValues);

        for(int j = 0; j < metadata.size(); j++){
            for(int i = 0; i < metadata.get(j).length; i++){
                assertEquals(metadata.get(j)[i], testMetadata.get(j)[i]);
            }
        }
        for(int j = 0; j < metadata.size(); j++){
            for(int i = 0; i < metadata.get(j).length; i++){
                assertEquals(rows.get(j)[i], testRows.get(j)[i]);
            }
        }
    }

}