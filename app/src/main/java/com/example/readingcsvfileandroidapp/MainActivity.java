package com.example.readingcsvfileandroidapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 2;

    private TextView textViewData;
    private Button buttonDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.textViewData);
        buttonDownload = findViewById(R.id.buttonDownload);

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    downloadAndDisplayCSV();
                }
            }
        });
    }



    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
            return false;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }

    //Using Local Csv File
    private void downloadAndDisplayCSV() {
        // Replace the following file path with the actual path of your CSV file
        try {
            InputStream inputStream = getAssets().open("sample.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

            StringBuilder csvData = new StringBuilder();
            for (CSVRecord record : parser) {
                for (String column : record) {
                    csvData.append(column).append(" ");
                }
                csvData.append("\n");
            }

            parser.close();
            reader.close();
            inputStream.close();

            textViewData.setText(csvData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }
    }

    //DownOAD CSV From-Internet

    private void downloadAndDisplayCSVFromInternet() {
        // Replace the URL with the actual URL of the CSV file
        String fileUrl = "https://example.com/sample.csv";

        // Replace the file name with the desired file name
        String fileName = "sample.csv";

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        long downloadId = downloadManager.enqueue(request);
    }

    //Using File Path

    private void downloadAndDisplayCSVUsingFilepath() {
        // Replace the following file path with the actual path of your CSV file
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/sample.csv";

        File csvFile = new File(filePath);
        if (csvFile.exists()) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(Uri.fromFile(csvFile));
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);

                StringBuilder csvData = new StringBuilder();
                for (CSVRecord record : parser) {
                    for (String column : record) {
                        csvData.append(column).append(" ");
                    }
                    csvData.append("\n");
                }

                parser.close();
                reader.close();
                inputStream.close();

                textViewData.setText(csvData.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "CSV file not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE || requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadAndDisplayCSV();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    }
