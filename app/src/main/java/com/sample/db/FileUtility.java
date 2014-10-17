package com.sample.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtility {
    /**
     * Creates file with the given file name and stores the data bytes.
     *
     * @param ctx        Context
     * @param bytes      data in bytes.
     * @param folderName Folder name to save the file.
     * @param fileName   File name to open or create.
     */
    public static void saveToExternalStorage(Context ctx,
                                             byte[] bytes,
                                             String folderName,
                                             String fileName) throws IOException {
        FileOutputStream fos;
        String dirPath = getFolderPath(ctx, folderName);
        if (dirPath != null) {
            createDirectory(dirPath);

            File file = getNewFile(dirPath, fileName);
            if (file != null) {
                fos = new FileOutputStream(file);
                fos.write(bytes);
            }
        }
    }

    public static String getFolderPath(Context ctx, String folderName) {
        String temp = null;
        if (isMediaMounted() && ctx != null) {
            File file = ctx.getExternalFilesDir(null);
            if (file != null) {
                temp = file.getAbsolutePath() + folderName;
            }
        }
        return temp;
    }

    public static void createDirectory(String folderName) {
        if (isMediaMounted()) {
            File feedFolder = new File(folderName);
            if (!feedFolder.exists()) {
                feedFolder.mkdirs();
            }
        }
    }

    /**
     * Creates/Recreates file.
     *
     * @param dirPath
     * @param fileName
     * @return
     */
    private static File getNewFile(String dirPath, String fileName) {
        File file = null;
        if (dirPath != null) {
            file = new File(dirPath, fileName);
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                //TODO:
            }
        }
        return file;
    }

    private static boolean isMediaMounted() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static void writeBitmapToFile(Context ctx, Bitmap resultBmp, String dir, String fileName) {
        String path = FileUtility.getFolderPath(ctx, dir);
        if (path != null) {
            File imgDir = new File(path);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            String filePath = imgDir.getAbsolutePath() + "/" + fileName;
            saveBitmapToFile(resultBmp, filePath);
        }
    }

    public static void saveBitmapToFile(Bitmap resultBmp, String filePath) {
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            resultBmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String folderName, String fileName, Context ctx) {
        String dirPath = getFolderPath(ctx, folderName);
        boolean isFileDeleted = false;
        if (dirPath != null) {
            File file = new File(dirPath, fileName);
            if (file.exists()) {
                isFileDeleted = file.delete();
            }
        }
        return isFileDeleted;
    }
}