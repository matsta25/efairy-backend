package com.matsta25.efairy.other;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class ArchiveFileUtils {

    public static final String DESTINATION_PATH = "tmp/";

    public static void unzip(File file){
        try {
            ZipFile zipFile = new ZipFile(file);
            zipFile.extractAll(DESTINATION_PATH);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}
