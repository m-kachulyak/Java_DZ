package ru.netology.kachulyak;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class EarthquakeService {

    private List<Earthquake> earthquakeList;

    private final String FILE_NAME = "src/main/resources/Землетрясения.csv";

    public EarthquakeService() {

    }

    @PostConstruct
    private void loadData () {
        startAsyncOperationProcessing();
    }

    public void startAsyncOperationProcessing() {
        Thread t = new Thread() {
            @Override
            public void run() {
                initData();
            }
        };
        t.start();
    }

    private void initData () {
        //надo считывать файл из ресурсов и построчно выводить результат в консоль
        earthquakeList = new ArrayList<Earthquake>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.contains("\"")) {
                    String [] strings = line.split("\"");
                    String [] firstString = strings[0].split(",");

                    Earthquake earthquake = new Earthquake (firstString[0],
                            Integer.parseInt(firstString[1]),
                            firstString[2],
                            Double.parseDouble(firstString[3]),
                            strings[1],
                            LocalDate.parse(strings[2].replace(",", "").substring(0,10),formatter));
                    System.out.println(earthquake);
                    earthquakeList.add(earthquake);
                } else {
                    String [] strings = line.split(",");

                    Earthquake earthquake = new Earthquake(strings[0],
                            Integer.parseInt (strings[1]),
                            strings[2],
                            Double.parseDouble(strings[3]),
                            strings[4],
                            LocalDate.parse(strings[5].substring(0,10),formatter));
                    System.out.println(earthquake);
                    earthquakeList.add(earthquake);
                }
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Метод, который принимает название штата и возвращает среднюю магнитуду.
    public double getAverageMagnitudeByState (String state) {
        double sum = 0.0;
        int count = 0;
        for (Earthquake e: earthquakeList) {
            if (e.getState().toLowerCase(Locale.ROOT).equals(state.toLowerCase())) {
                sum += e.getAverageMagnitude();
                count++;
            }
        }

        if (count != 0) {
            return sum/count;
        } else return 0;

    }

    //Метод, который принимает в качестве параметра тип магнитуды и две задающие диапазон даты,
    // а возвращает список, который содержит штат, дату, и глубину для землетрясений этого типа в указанном диапазоне дат.

    public List <EarthquakeInfo> getInfoInDateRange (String averageType, String start, String finish) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(start,formatter);
        LocalDate endDate = LocalDate.parse(finish,formatter);

        List <EarthquakeInfo> earthquakeInfoList = new ArrayList<>();
        for (Earthquake e:earthquakeList) {
            if (e.getAverageType().equals(averageType) && e.getDate().isAfter(startDate) && e.getDate().isBefore(endDate)) {
                earthquakeInfoList.add(new EarthquakeInfo(e.getAverageType(),e.getState(), e.getDate(), e.getDepth()));
            }
        }

        return earthquakeInfoList;
    }


    // Собственный метод, предложенный по аналогии с предыдущими. Поясните, для чего используется указанный метод.
    //Метод принимает в качестве аргумента глубину землятрясения и дату
    //а возвращает список землятрясений, с глубиной меньше заданной после указанной даты

    public List <Earthquake> getInfoByDepthInDate (int depth, String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date,formatter);

        List <Earthquake> earthquakes = new ArrayList<>();
        for (Earthquake e: earthquakeList) {
            if (e.getDate().isAfter(localDate) && e.getDepth() < depth) {
                earthquakes.add(e);
            }
        }

        return earthquakes;
    }

    //удаление записи по id
    public void deleteItem (String id) {
        earthquakeList.remove(findEarthquake(id));
        saveFile();
    }

    //редактирование записи
    public Earthquake update (Earthquake earthquake) {
        Earthquake fromFile = findEarthquake(earthquake.getID());

        fromFile.setAverageMagnitude(earthquake.getAverageMagnitude());
        fromFile.setAverageType(earthquake.getAverageType());
        fromFile.setDate(earthquake.getDate());
        fromFile.setDepth(earthquake.getDepth());
        fromFile.setState(earthquake.getState());

        saveFile();

        return findEarthquake(earthquake.getID());
    }

    private Earthquake findEarthquake (String id) {
        for (Earthquake e:earthquakeList) {
            if (e.getID().equals(id)) {
                return e;
            }
        }

        return new Earthquake();
    }

    private void saveFile () {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
            writer.write( "ID,Глубина в метрах,Тип магнитуды,Магнитуда,Штат,Время");
            for (Earthquake e: earthquakeList) {
                writer.newLine();
                writer.write(e.toCSVString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
