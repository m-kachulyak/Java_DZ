package ru.netology.kachulyak;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/earthquake")
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    @PostMapping ("/averageMagnitude")
    public double getAverageMagnitudeByState (@RequestParam String state) {
       return earthquakeService.getAverageMagnitudeByState(state);
    }

    @PostMapping ("/infoByDate")
    public List <EarthquakeInfo> getInfoInDateRange (@RequestParam String averageType, @RequestParam String dateStart, @RequestParam String dateEnd) {
        return earthquakeService.getInfoInDateRange(averageType,dateStart,dateEnd);
    }

    @PostMapping ("/infoLessDepth")
    public List <Earthquake> getLessDepth (@RequestParam String depth, String date) {
        return earthquakeService.getInfoByDepthInDate(Integer.parseInt(depth),date);
    }

    @DeleteMapping ()
    public void deleteItem (@RequestBody String id) {
        earthquakeService.deleteItem(id);
    }

    @PostMapping
    public Earthquake update (@RequestBody Earthquake earthquake) {
        return earthquakeService.update(earthquake);
    }

}
