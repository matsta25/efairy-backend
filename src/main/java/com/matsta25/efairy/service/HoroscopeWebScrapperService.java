package com.matsta25.efairy.service;

import com.matsta25.efairy.other.ArchiveFileUtils;
import com.matsta25.efairy.other.RestTemplateCustom;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.matsta25.efairy.other.ArchiveFileUtils.DESTINATION_PATH;

@Service
public class HoroscopeWebScrapperService {

    private static final String ZIP_DIRECTORY_NAME = "scrapedData/";

    @Value("${horoscope-web-scraper.url}")
    private String horoscopeWebScraperUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(HoroscopeWebScrapperService.class);

    RestTemplateCustom restTemplateCustom;
    BatchJobService batchJobService;

    public HoroscopeWebScrapperService(RestTemplateCustom restTemplateCustom, BatchJobService batchJobService) {
        this.restTemplateCustom = restTemplateCustom;
        this.batchJobService = batchJobService;
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void doSomethingAfterStartup() {
//        startHoroscopeWebScraper();
//    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void startHoroscopeWebScraper() {
        LOGGER.info("HoroscopeWebScrapperService: HoroscopeWebScraper has started!");

        LocalDate firstDayOfWeek = LocalDate.now();
        LocalDate lastDayOfWeek = LocalDate.now().with(DayOfWeek.SUNDAY);

        File file = makeRequestAndGetFile(firstDayOfWeek, lastDayOfWeek);

        ArchiveFileUtils.unzip(file);
        invokeImportHoroscopeForEachFileInDirectory();
        deleteDirectoryWithContent();
    }

    private void deleteDirectoryWithContent() {
        try {
            FileUtils.deleteDirectory(new File(DESTINATION_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invokeImportHoroscopeForEachFileInDirectory() {
        File folder = new File(DESTINATION_PATH + ZIP_DIRECTORY_NAME);
        File[] listOfFiles = folder.listFiles();

        for (File file : Objects.requireNonNull(listOfFiles)) {
            if (file.isFile() && !file.getName().equals(".gitkeep")) {
                try {
                    this.batchJobService.invokeImportHoroscope(
                            new MockMultipartFile( file.getName(),
                                    new FileInputStream(file.getPath())
                            )
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File makeRequestAndGetFile(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        Map<String, String> params = getParamsForRequest(firstDayOfWeek, lastDayOfWeek);
        LOGGER.info("HoroscopeWebScrapperService: Made request for {} with {}", horoscopeWebScraperUrl, params);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(horoscopeWebScraperUrl)
                .queryParam("endDate", params.get("endDate"))
                .queryParam("startDate", params.get("startDate"));


        return restTemplateCustom.getRestTemplate().execute(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                clientHttpResponse -> {
                    File ret = File.createTempFile("download", "tmp");
                    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                    return ret;
                }
        );
    }

    private Map<String, String> getParamsForRequest(LocalDate firstDayOfWeek, LocalDate lastDayOfWeek) {
        Map<String, String> params = new HashMap<>();
        params.put("endDate", firstDayOfWeek.toString());
        params.put("startDate", lastDayOfWeek.toString());
        return params;
    }
}
