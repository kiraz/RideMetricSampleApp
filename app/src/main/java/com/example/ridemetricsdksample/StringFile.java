package com.example.ridemetricsdksample;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by bakhtiyor on 3/23/17.
 */
class StringFile {
    private File file;
    private final String TAG = "StringFile";
    private BufferedWriter writer;
    private FileOutputStream fileOutputStream;
    private String fileName;

    public StringFile(String fileName) {
        this.fileName = fileName;
    }

    public void init() throws IOException {
        String dirName = Environment.getExternalStorageDirectory().getPath();
        File sdDir = new File(dirName);
        if (sdDir.exists() && sdDir.canWrite() && sdDir.canRead()) {
            File testDir = new File(sdDir.getAbsolutePath() + "/ridemetric_sample");
            if (!testDir.exists()) {
                testDir.mkdir();
            }

            if (testDir.exists() && testDir.canWrite() && testDir.canRead()) {
                file = new File(testDir.getAbsolutePath() + "/" + this.fileName);
                writer = new BufferedWriter(new FileWriter(this.file, true));
            }

            if ((file == null || this.writer == null)) {
                Log.e("StringFile", "error creating fileoutputstream");
            }
        }

    }

    public synchronized void write(String newContent) {
        try {
            writer.write(newContent);
            writer.flush();
        } catch (IOException e) {
            Log.e(TAG, "Error", e);
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            Log.e(TAG, "Error", e);
        }
    }
}
