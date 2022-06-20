package com.zainaftab.coronavirustracker.services;

import com.zainaftab.coronavirustracker.models.CoronaStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.ElementType;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CoronaDataService {
    private static String confirmedUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static String recoveredUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private static String deathUrl = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";

    private ArrayList<CoronaStat> confirmedStats = new ArrayList<>();
    private ArrayList<CoronaStat> recoveredStats = new ArrayList<>();
    private ArrayList<CoronaStat> deathStats = new ArrayList<>();

    @PostConstruct
    public void fetchConfirmedStats() throws IOException, InterruptedException {
        this.confirmedStats = fetchVirusData(confirmedUrl);
    }

    @PostConstruct
    @DependsOn(value = "fetchConfirmedStats")
    public void fetchRecoveredStats() throws IOException, InterruptedException {
        this.recoveredStats = fetchVirusData(recoveredUrl);
        this.recoveredStats.stream().forEach(s->System.out.println(s));;
        this.confirmedStats.stream().forEach(stat -> {
            Optional<CoronaStat> d = this.recoveredStats.stream().filter(rs -> rs.getState().equals(stat.getState()) && rs.getCountry().equals(stat.getCountry())).findFirst();
            stat.setRecoveredToll(d.map(CoronaStat::getCurrentToll).orElse(0));
            stat.setRecoveredTollDiff(d.map(CoronaStat::getCurrentTollDiff).orElse(0));
        });
    }

    @PostConstruct
    @DependsOn(value = "fetchConfirmedStats")
    public void fetchDeathStats() throws IOException, InterruptedException {
        this.deathStats = fetchVirusData(deathUrl);
        this.confirmedStats.stream().forEach(stat -> {
            Optional<CoronaStat> d = this.deathStats.stream().filter(ds -> ds.getState().equals(stat.getState()) && ds.getCountry().equals(stat.getCountry())).findFirst();
            stat.setDeathToll(d.map(CoronaStat::getCurrentToll).orElse(0));
            stat.setDeathTollDiff(d.map(CoronaStat::getCurrentTollDiff).orElse(0));
        });
    }

    public ArrayList<CoronaStat> fetchVirusData(String url) throws IOException, InterruptedException {
        ArrayList<CoronaStat> cStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new StringReader(response.body()));

        for (CSVRecord record : records) {
            CoronaStat stat = new CoronaStat();
            stat.setCountry(record.get("Country/Region"));
            stat.setState(record.get("Province/State"));
            int currToll = Integer.parseInt(record.get(record.size() - 1));
            int lastToll = Integer.parseInt(record.get(record.size() - 1));
            stat.setCurrentToll(currToll);
            stat.setCurrentTollDiff(currToll - lastToll);
            cStats.add(stat);
        }
        return cStats;
    }

    public ArrayList<CoronaStat> getConfirmedStats() {
        return confirmedStats;
    }

    public ArrayList<CoronaStat> getRecoveredStats() {
        return recoveredStats;
    }

    public ArrayList<CoronaStat> getDeathStats() {
        return deathStats;
    }
}
