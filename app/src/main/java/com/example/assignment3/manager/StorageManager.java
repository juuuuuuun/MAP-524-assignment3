package com.example.assignment3.manager;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageManager {

    public void resetInternalStorage(Activity activity) {
        try(FileOutputStream fileOutputStream = activity.openFileOutput("numberOfCorrect.txt", Context.MODE_PRIVATE)) {
            File file = activity.getFilesDir();
            fileOutputStream.write("".getBytes());
            Toast.makeText(activity, "Reset", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInternalStorage(Activity activity, String numberOfCorrect) {
        try(FileOutputStream fileOutputStream = activity.openFileOutput("numberOfCorrect.txt", Context.MODE_APPEND)) {
            File file = activity.getFilesDir();
            fileOutputStream.write(numberOfCorrect.getBytes());
            Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadFromInternalStorage(Activity activity){
        String loadedData = "";

        try(FileInputStream fileInputStream = activity.openFileInput("numberOfCorrect.txt")) {
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while ((read = fileInputStream.read()) != -1){
                buffer.append((char) read);
            }
            loadedData = buffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return loadedData;
    }
}
