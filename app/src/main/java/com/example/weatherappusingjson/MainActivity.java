package com.example.weatherappusingjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadJsonFromOpenWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            URL url;
            HttpURLConnection httpURLConnection;
            String result = null;
            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;

                    data = inputStreamReader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.i("Website content", result);
            try {
                JSONObject weatherJsonObject = new JSONObject(result);

//                String weatherInfo = weatherJsonObject.getString("weather");
//                Log.i("weather:", weatherInfo);
//                JSONArray weatherInfoArray = new JSONArray(weatherInfo);
//
//                for (int i = 0; i < weatherInfoArray.length(); i++) {
//                    JSONObject jsonPart = weatherInfoArray.getJSONObject(i);
//                    Log.i("main:", jsonPart.getString("main"));
//                    Log.i("description:", jsonPart.getString("description"));
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadJsonFromOpenWeather task = new DownloadJsonFromOpenWeather();
        task.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
    }
}
