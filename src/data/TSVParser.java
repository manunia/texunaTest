package data;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import model.Data;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class TSVParser {

    public List<Data> read() {

        TsvParserSettings settings = new TsvParserSettings();
        TsvParser parser = new TsvParser(settings);

        List<String[]> allRows = parser.parseAll(getReader("resourses/source-data.tsv"));
        List<Data> data = new ArrayList<>();
        for (String[] arr:allRows) {
            data.add(new Data(arr[0],arr[1],arr[2]));
        }
        return data;
    }

    public Reader getReader(String path) {
        try {
            return new InputStreamReader(new FileInputStream(path),"UTF-16");
        } catch (Exception e) {
            throw new IllegalStateException("Unable to read input", e);
        }

    }

}
