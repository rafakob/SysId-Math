package rafakob.multiedip.idsys;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import de.greenrobot.event.EventBus;
import rafakob.multiedip.bus.LoadDataFinishedEvent;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public class LoadDataFromFileTask extends AsyncTask<IdData, Long, IdData> {
    TextView textView;

    public LoadDataFromFileTask(TextView textView) {
        this.textView = textView;
    }


    @Override
    protected IdData doInBackground(IdData... params) {
        long numberOfRows = 0;

        for (IdData iddata : params) {
            try {
                publishProgress(numberOfRows);
                BufferedReader br = new BufferedReader(new FileReader(iddata.getPath()));
                String line;
                while ((line = br.readLine()) != null) {

                    numberOfRows++;

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            iddata.setLength(numberOfRows);
            return iddata;
        }

        return null;

    }

    @Override
    protected void onPostExecute(IdData idData) {
        super.onPostExecute(idData);
        EventBus bus = EventBus.getDefault();
        bus.post(new LoadDataFinishedEvent(idData));
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        textView.setText("Loading...");
    }
}
