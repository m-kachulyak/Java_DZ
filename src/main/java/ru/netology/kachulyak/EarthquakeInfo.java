package ru.netology.kachulyak;

import java.io.Serializable;
import java.time.LocalDate;

public class EarthquakeInfo implements Serializable {

    public EarthquakeInfo(String averageType,String state, LocalDate date, int depth) {
        this.averageType=averageType;
        this.state = state;
        this.date = date;
        this.depth = depth;
    }

    //тип магнитуда
    private String averageType;

    //штат
    private String state;

    //время
    private LocalDate date;

    //глубина землетрясения в метрах
    private int depth;

    @Override
    public String toString() {
        return "EarthquakeInfo{" +
                "averageType='" + averageType + '\'' +
                ", state='" + state + '\'' +
                ", date=" + date +
                ", depth=" + depth +
                '}';
    }

    public String getAverageType() {
        return averageType;
    }

    public String getState() {
        return state;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDepth() {
        return depth;
    }
}
