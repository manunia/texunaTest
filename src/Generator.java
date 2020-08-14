import data.TSVParser;
import model.Data;
import model.Settings;
import settings.XMLParser;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    private static Settings settings;
    private static List<Data> data;

    public static void main(String[] args) {
        settings = new XMLParser().read();
        data = new TSVParser().read();

        try {
            geterateReport(settings,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void geterateReport(Settings settings, List<Data> data) throws Exception {
        PrintStream fileOut = new PrintStream("resourses/report.txt", "UTF-16");
        boolean fitInSingleLine;
        for (Data d:data) {
            //ReportGenerator
//            do {
//                fitInSingleLine = true;
//                String[] trim
//            }


//            fileOut.print("| ");
//
//            if (settings.getNumber()>=d.getNumber().length()) {
//                for (int i = 0; i < settings.getNumber(); i++) {
//                    fileOut.print(d.getNumber());
//                }
//            }
//            printLine(settings.getWidth(),fileOut);
        }

    }

    private static void printLine(int width, PrintStream fileOut) {
        fileOut.println();
        for (int i = 0; i < width; i++) {
            fileOut.print("-");
        }
        fileOut.println();
    }

    private int getCutIndex(String data, int width) {

        if (data.length() <= width) {
            throw new IllegalArgumentException("Nothing to cut. There is enough width for data");
        }

        String firstPart = data.substring(0, width);

        boolean wordCutted = data.substring(width - 1, width + 1).matches("\\w{2}");

        if (!wordCutted) return width;
        else {
            String s = firstPart.replaceAll("\\W", " ");
            int separator = s.lastIndexOf(" ");
            return separator != -1 ? separator + 1 : width;
        }
    }
}
