package settings;

import model.Settings;

import javax.xml.stream.XMLStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLParser {

    public Settings read() {
        Settings settings = new Settings();
        try(StaxStreamProcessor processor = new StaxStreamProcessor(
                Files.newInputStream(Paths.get("resourses/settings.xml")))) {
            XMLStreamReader reader = processor.getReader();
            if (processor.startElement("width","page")) {
                settings.setWidth(Integer.parseInt(processor.getText()));
            }
            if (processor.startElement("height","page")) {
                settings.setHeight(Integer.parseInt(processor.getText()));
            }
            while (processor.startElement("title","column")) {
                if (processor.getText().equals("Номер")) {
                    if (processor.startElement("width","column")) {
                        settings.setNumber(Integer.parseInt(processor.getText()));
                    }
                }
            }
            while (processor.startElement("title","column")) {
                if (processor.getText().equals("Дата")) {
                    if (processor.startElement("width","column")) {
                        settings.setDate(Integer.parseInt(processor.getText()));
                    }
                }
            }
            while (processor.startElement("title","column")) {
                if (processor.getText().equals("ФИО")) {
                    if (processor.startElement("width","column")) {
                        settings.setFio(Integer.parseInt(processor.getText()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settings;
    }


}
