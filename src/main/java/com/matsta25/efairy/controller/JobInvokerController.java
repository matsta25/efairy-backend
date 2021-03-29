package com.matsta25.efairy.controller;

import com.matsta25.efairy.service.BatchJobService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/batch")
public class JobInvokerController {

    BatchJobService batchJobService;

    public JobInvokerController(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }

    //    TODO: fix swagger select file bug
    @PostMapping("/invoke-import-horoscope")
    @ApiOperation("This endpoint is providing import of the csv file with horoscopes to db with spring batch.")
    public String invokeImportHoroscope(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        this.batchJobService.invokeImportHoroscope(file);
        return "Job - import horoscope has been invoked.";
    }
}
