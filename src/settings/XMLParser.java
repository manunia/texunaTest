package settings;

import model.Column;
import model.Settings;

import javax.xml.stream.XMLStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public Settings read() {
        Settings settings = new Settings();
        List<Column> columns = new ArrayList<>();
        try(StaxStreamProcessor processor = new StaxStreamProcessor(
                Files.newInputStream(Paths.get("resourses/settings.xml")))) {
            XMLStreamReader reader = processor.getReader();
            if (processor.startElement("width","page")) {
                settings.setWidth(Integer.parseInt(processor.getText()));
            }
            if (processor.startElement("height","page")) {
                settings.setHeight(Integer.parseInt(processor.getText()));
            }
            while (processor.startElement("column","columns")) {
                Column column = new Column();
                if (processor.startElement("title","column")) {

                    column.setTitle(processor.getText());
                }
                if (processor.startElement("width","column")) {
                    column.setWidth(Integer.parseInt(processor.getText()));
                }
                columns.add(column);
            }
            settings.setColumns(columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settings;
    }


}
