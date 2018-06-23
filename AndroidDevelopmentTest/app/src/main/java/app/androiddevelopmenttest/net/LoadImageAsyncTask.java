package app.androiddevelopmenttest.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by DELL on 6/23/2018.
 */

public class LoadImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {

    CommunicationManager communicationManager;

    public LoadImageAsyncTask(CommunicationManager communicationManager) {
        this.communicationManager = communicationManager;
    }

    @Override
    protected Bitmap doInBackground(String... param) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(param[0]);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            communicationManager.onProcessNext(bitmap);
        } else {
            communicationManager.onError();
        }
    }
}
