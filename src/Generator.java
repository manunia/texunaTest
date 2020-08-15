import data.TSVParser;
import model.Column;
import model.Data;
import model.Settings;
import org.apache.commons.lang3.StringUtils;
import settings.XMLParser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    private static Settings settings;
    private static List<Data> data;
    private static PrintStream fileOut;

    private static int currentLine = 0;
    private static int currentPage = 0;
    private static String dottedLineSeparator = "";

    public static void main(String[] args) {
        settings = new XMLParser().read();
        data = new TSVParser().read();

        try {
            geterateReport(settings,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void geterateReport(Settings settings, List<Data> data) throws Exception {
        fileOut = new PrintStream("resourses/report.txt", "UTF-16");

        for (Data d:data) {
            String[] dataline = {d.getNumber(),d.getDate(),d.getName()};
            writeFormattedDataLine(dataline);
        }
    }

    public static void writeFormattedDataLine(String[] dataLine) {
        ArrayList<String[]> trimmedDataLines = spliceDataToFitColumnWidth(dataLine);

        if (!isThereEnoughSpaceFor(trimmedDataLines.size()))
            printPageSeparator(fileOut);

        if (currentLine == 0)
            printHeader();

        printDottedLineSeparator();
        for (String[] trimmedDataLine : trimmedDataLines)
            printSingleLine(trimmedDataLine);
    }

    private static void printHeader() {
        String header = "";
        int columnsCount = settings.getColumns().size();
        String[] headerData = new String[columnsCount];
        for (int i = 0; i < columnsCount; i++) {
            headerData[i] = settings.getColumns().get(i).getTitle();
        }
        ArrayList<String[]> trimmedHeaderLines = spliceDataToFitColumnWidth(headerData);

        for (int i = 0; i < trimmedHeaderLines.size(); i++) {
            header += renderSingleLine(trimmedHeaderLines.get(i));
            if (i < trimmedHeaderLines.size() - 1)
                header += System.lineSeparator();
        }
        fileOut.print(header);
    }

    private static void printPageSeparator(PrintStream fileOut) {
        fileOut.println(System.lineSeparator() + "~");
        currentLine = 0;
        currentPage++;
    }

    private static ArrayList<String[]> spliceDataToFitColumnWidth(String[] dataLine) {
        ArrayList<String[]> trimmedDataLines = new ArrayList<>();
        List<Column> columns = settings.getColumns();
        boolean fitInSingleLine;

        do {
            fitInSingleLine = true;
            String[] trimmedDataLine = new String[columns.size()];
            String[] overheadDataLine = new String[columns.size()];

            for (int i = 0; i < columns.size(); i++) {
                Column column = columns.get(i);
                String data = dataLine[i];

                if (data == null || data.length() <= column.getWidth())
                    trimmedDataLine[i] = data;
                else {
                    int cutIndex = getCutIndex(data, column.getWidth());
                    trimmedDataLine[i] = data.substring(0, cutIndex);
                    overheadDataLine[i] = data.substring(cutIndex).trim();
                    fitInSingleLine = !(overheadDataLine[i].length() > 0);
                }
            }
            trimmedDataLines.add(trimmedDataLine);
            if (!fitInSingleLine) dataLine = overheadDataLine;

        } while (!fitInSingleLine);

        return trimmedDataLines;
    }

    private static boolean isThereEnoughSpaceFor(int linesCount) {
        return currentLine + linesCount + 1 <= settings.getHeight();
    }

    private static void printSingleLine(String[] dataLine) {
        fileOut.print(System.lineSeparator() + renderSingleLine(dataLine));
    }

    private static String renderSingleLine(String[] dataLine) {
        StringBuilder textline = new StringBuilder("|");

        for (int i = 0; i < settings.getColumns().size(); i++) {
            Column column = settings.getColumns().get(i);
            String data = dataLine[i];
            if (data != null)
                textline.append(" " + StringUtils.rightPad(data, column.getWidth()) + " |");
            else textline.append(StringUtils.rightPad("", 1 + column.getWidth()) + " |");
        }
        currentLine++;
        return textline.toString();
    }

    private static void printDottedLineSeparator() {
        fileOut.print(System.lineSeparator());
        for (int i = 0; i < settings.getWidth(); i++) {
            fileOut.print("-");
        }
    }

    private static int getCutIndex(String data, int width) {
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
