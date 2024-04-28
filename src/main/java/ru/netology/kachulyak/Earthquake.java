package ru.netology.kachulyak;


import java.io.Serializable;
import java.time.LocalDate;


public class Earthquake implements Serializable {

    public Earthquake(String ID, int depth, String averageType, double averageMagnitude, String state, LocalDate date) {
        this.ID = ID;
        this.depth = depth;
        this.averageType = averageType;
        this.averageMagnitude = averageMagnitude;
        this.state = state;
        this.date = date;
    }

    public Earthquake() {

    }

    //ID

    private String ID;

    //глубина землетрясения в метрах
    private int depth;

    //тип магнитуды
    private String averageType;

    //магнитуда
    private double averageMagnitude;

    //штат
    private String state;

    //время
    private LocalDate date;

    public String getID() {
        return ID;
    }

    public int getDepth() {
        return depth;
    }

    public String getAverageType() {
        return averageType;
    }

    public double getAverageMagnitude() {
        return averageMagnitude;
    }

    public String getState() {
        return state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setAverageType(String averageType) {
        this.averageType = averageType;
    }

    public void setAverageMagnitude(double averageMagnitude) {
        this.averageMagnitude = averageMagnitude;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "ID='" + ID + '\'' +
                ", depth=" + depth +
                ", averageType='" + averageType + '\'' +
                ", averageMagnitude=" + averageMagnitude +
                ", state='" + state + '\'' +
                ", time='" + date + '\'' +
                '}';
    }

    public String toCSVString() {
        String stateStr = "";
        if (state.contains(",")) {
            stateStr = "\"" + state + "\"";
        } else {
            stateStr = state;
        }

        return ID + "," +
                depth + "," +
                averageType + "," +
                averageMagnitude + "," +
                stateStr + "," +
                date;
    }
}
