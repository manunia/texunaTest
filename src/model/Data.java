package model;

public class Data {
    private String number;
    private String date;
    private String name;

    public Data(String number, String date, String name) {
        this.number = number;
        this.date = date;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "number = " + number + "\n" +
                "date = " + date + "\n" +
                "name = " + name;
    }
}
