package site.taki_lab.n_tak;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by n_tak on 2018/01/05.
 */

public class CountingLog {
    private AppCompatActivity _activity;
    public CountingLog(AppCompatActivity activity){
        _activity = activity;
    }

    public void WriteLog(int Count) {
        Date date = new Date();
        SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd kk.mm.ss");
        String writeData = dataFormat.format(date) + "\t" + String.valueOf(Count) + "\n";
        String filename = fileFormat.format(date);
        try{
            FileOutputStream fileOutputStream;
            fileOutputStream = _activity.openFileOutput(filename, Context.MODE_APPEND);
            fileOutputStream.write(writeData.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }
}
