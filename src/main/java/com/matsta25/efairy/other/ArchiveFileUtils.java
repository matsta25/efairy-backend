package com.matsta25.efairy.other;

import java.io.File;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiveFileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveFileUtils.class);

    public static final String DESTINATION_PATH = "/tmp/";

    public static void unzip(File file) {
        try {
            ZipFile zipFile = new ZipFile(file);
            zipFile.extractAll(DESTINATION_PATH);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
