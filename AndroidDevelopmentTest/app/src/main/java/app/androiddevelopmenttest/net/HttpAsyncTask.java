package app.androiddevelopmenttest.net;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DELL on 6/22/2018.
 */

public class HttpAsyncTask extends AsyncTask<String, Integer, String> {

    private CommunicationManager communicationManager;
    private String response;

    public HttpAsyncTask(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
    }

    @Override
    protected String doInBackground(String... param) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(param[0]);
            Log.d("URL", url.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response = result.toString();
        Log.i("response", response);

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (TextUtils.isEmpty(response) ||
                response.equals("{}") ||
                response.equals("[]")) {
            communicationManager.onError();
        } else {
            communicationManager.onResponse(response);
        }
    }
}

