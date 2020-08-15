package model;

import java.util.Arrays;
import java.util.List;

public class Settings {
    private int width;
    private int height;
    List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "width=" + width +
                ", height=" + height +
                ", columns=" + columns.toString() +
                '}';
    }
}
