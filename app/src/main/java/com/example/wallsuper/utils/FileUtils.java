package com.example.wallsuper.utils;

import android.os.Build;
import android.os.Environment;

import java.io.File;

public class FileUtils {
    public static final String FOLDER_NAME = "WALLSUPER/EDITED WALLPAPER";

    public static File createFoler() {
        File baseDir;
        if (Build.VERSION.SDK_INT < 8) {
            baseDir = Environment.getExternalStorageDirectory();
        } else {
            baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
        if (baseDir == null)
            return Environment.getExternalStorageDirectory();

        File anviary = new File(baseDir, FOLDER_NAME);

        if (anviary.exists())
            return anviary;
        if (anviary.isFile())
            anviary.delete();
        if (anviary.mkdir())
            return anviary;
        return Environment.getExternalStorageDirectory();
    }

    public static File getEmptyFile(String name) {
        File folder = FileUtils.createFoler();
        if (folder != null) {
            if (folder.exists()) {
                File file = new File(folder, name);
                return file;
            }
        }
        return null;
    }

    public static File getEditFile() {
        return FileUtils.getEmptyFile("WALLSUPER" + System.currentTimeMillis() + ".JPEG");
    }
}
