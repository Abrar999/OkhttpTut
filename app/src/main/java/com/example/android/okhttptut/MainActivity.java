package com.example.android.okhttptut;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebservice();
            }
        });

        client = new OkHttpClient();
    }

    private void getWebservice() {

       final Request request = new Request.Builder()
                .url("http://hapi.fhir.org/baseR4/Patient/6")
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "443e2795-cb62-4486-b843-ee88fb01df1f")
                .build();


        //final Request request = new Request.Builder().url("http://www.ssaurel.com/tmp/todos").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText("Failure !");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result.setText(response.body().string());
                        } catch (IOException ioe) {
                            result.setText("Error during get body");
                        }
                    }
                });
            }
        });
    }
}
