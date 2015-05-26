package rafakob.multiedip;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import rafakob.multiedip.bus.IdentificationFinishedEvent;
import rafakob.multiedip.bus.LoadDataFinishedEvent;
import rafakob.multiedip.bus.SelectFileEvent;
import rafakob.multiedip.bus.SettingsChangedEvent;
import rafakob.multiedip.filebrowser.FilebrowserDialogFragment;
import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.identification.IdentificationModel;
import rafakob.multiedip.idsys.processing.DataProcessing;
import rafakob.multiedip.idsys.processing.DataProcessingInterface;
import rafakob.multiedip.prefs.PrefManager;
import rafakob.multiedip.others.ContentBox;
import rafakob.multiedip.others.LoadDataFromFileTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    private TextView txtFilename;
    private TextView txtPath;
    private TextView txtDataType;
    private TextView txtLength;
    private TextView txtInfo;
    private Context mContext;
    private Button btnRun;
    private PrefManager mPrefManager;
    private ArrayList<DataProcessingInterface> mPreprocessingTasks;
    private IdentificationModel mIdentificationModel;

    private DialogFragment filePickerDialogFragment;
    private String currentBrowseStartPath;

    private boolean mFlagFileLoaded = false;

    private EventBus mBus = EventBus.getDefault();
    private IdData iddata, dataProcessed;
    private ContentBox mBox1, mBox2, mBox3;



    public DashboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBus.register(this);

        mContext = getActivity().getApplicationContext();
        mPrefManager = new PrefManager(mContext);
        txtFilename = new TextView(mContext);
        txtPath = new TextView(mContext);
        txtDataType = new TextView(mContext);
        txtLength = new TextView(mContext);
        txtInfo = new TextView(mContext);
        iddata = ((GlobalApp) mContext).getDataSource();
        dataProcessed = ((GlobalApp) mContext).getDataProcessed();
        // Create file browser instance
        filePickerDialogFragment = new FilebrowserDialogFragment();
        // Set up path to sd card
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            currentBrowseStartPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setupView(inflater.inflate(R.layout.fragment_dashboard, container, false));
    }

    private View setupView(View view) {

        /** Create content boxes: **/
        mBox1 = new ContentBox(view, R.id.box_datasource);
        mBox1.setTitle(R.string.box_datasource);
        mBox1.setButtonText(R.string.box_load);

        mBox2 = new ContentBox(view, R.id.box_preprocessing);
        mBox2.setTitle(R.string.box_preprocessing);
        mBox2.setButtonText(R.string.box_settings);

        mBox3 = new ContentBox(view, R.id.box_identification);
        mBox3.setTitle(R.string.box_identification);
        mBox3.setButtonText(R.string.box_settings);

        /** Setup their listeners: **/
        mBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadDatasourceClick();
            }
        });
        mBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreprocessingsClick();
            }
        });
        mBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIdentificationClick();
            }
        });

        /** Add TextViews to grid layout: **/
        mBox1.addToGrid(0, 0, R.string.lbl_filename);
        mBox1.addToGrid(1, 0, R.string.lbl_path);
        mBox1.addToGrid(2, 0, R.string.lbl_data_type);
        mBox1.addToGrid(3, 0, R.string.lbl_length_source);

        mBox1.addToGrid(0, 1, txtFilename, "");
        mBox1.addToGrid(1, 1, txtPath, "");
        mBox1.addToGrid(2, 1, txtDataType, "");
        mBox1.addToGrid(3, 1, txtLength, "");

        /** Others **/
        btnRun = (Button) view.findViewById(R.id.btn_run);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRunClick();
            }
        });

        txtInfo = (TextView) view.findViewById(R.id.lbl_info);
        txtInfo.setText("");

        updateTasksAndModels();
        updateSettingsBoxes();

        return view;
    }


    public void onLoadDatasourceClick() {
//        // Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
        filePickerDialogFragment.show(getActivity().getFragmentManager(), "Filebrowser");
        Bundle dialogParameters = new Bundle(1);
        dialogParameters.putString("START_PATH", currentBrowseStartPath);
        filePickerDialogFragment.setArguments(dialogParameters);

    }

    public void onPreprocessingsClick() {
        Intent intent = new Intent(getActivity(), ProjectPrefsActivity.class);
        intent.putExtra("PROJECT_SETTINGS_MODE", "preprocessing");
        startActivity(intent);
    }

    public void onIdentificationClick() {
        Intent intent = new Intent(getActivity(), ProjectPrefsActivity.class);
        intent.putExtra("PROJECT_SETTINGS_MODE", "identification");
        startActivity(intent);

    }

    public void onRunClick() {
        /****** RUN CLICK ******/
        new RunTask().execute("");
    }


    private void updateTasksAndModels() {
        mPreprocessingTasks = mPrefManager.getPreprocesingConfig();
        if (mFlagFileLoaded)
            mIdentificationModel = mPrefManager.getIdentificationConfig(iddata.getType());
    }

    private void updateSettingsBoxes() {
        mBox2.cleatGrid();
        if (!mPreprocessingTasks.isEmpty()) {
            for (int i = 0; i < mPreprocessingTasks.size(); i++) {
                mBox2.addToGrid(i, 0, new TextView(mContext), mPreprocessingTasks.get(i).getFunctionDescription());
            }
        }


        mBox3.cleatGrid();
        if (mFlagFileLoaded)
            mBox3.addToGrid(0, 0, new TextView(mContext), mIdentificationModel.getFunctionDescription());
    }

    /**
     * Catches event from FilebrowserDialogFragment
     */
    public void onEvent(SelectFileEvent event) {
        txtFilename.setText(event.path.substring(event.path.lastIndexOf("/") + 1));
        currentBrowseStartPath = event.path.substring(0, event.path.lastIndexOf("/"));
        txtPath.setText(currentBrowseStartPath);

        iddata.setPath(currentBrowseStartPath + "/" + txtFilename.getText().toString());
        // load data from file to iddata object
        new LoadDataFromFileTask(txtInfo).execute(iddata);
        mFlagFileLoaded = true;

        // todo: odświeżac boksy po wczytaniu pliku
    }

    /**
     * Update iddata object
     */
    public void onEvent(LoadDataFinishedEvent event) {
        txtLength.setText(event.iddata.getLength() + "");
        txtDataType.setText(event.iddata.getType());
        txtInfo.setText("");
    }

    /**
     * When ProjectPrefsActivity is closed
     */
    public void onEvent(SettingsChangedEvent event) {
        updateTasksAndModels();
        updateSettingsBoxes();
    }

    /**
     * Run task
     */
    private class RunTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtInfo.setText("Performing calculations...");
            btnRun.setEnabled(false);
            btnRun.setBackgroundColor(getResources().getColor(R.color.btn_run_disabled));

        }

        @Override
        protected String doInBackground(String... params) {
            Stopwatch stopwatch = Stopwatch.createStarted();


            try{
                DataProcessing dp = new DataProcessing();
                dataProcessed.cloneFromIddata(iddata);
                dp.process(dataProcessed, mPreprocessingTasks); // perform preprocessing
                mIdentificationModel.execute(dataProcessed); // perform identification
            }
            catch(OutOfMemoryError e) {
                return "Error! Out of memory!";
            }
            catch(NullPointerException e) {
                return "Error! No data or settings!";
            }
            catch (Exception e){
                return "An unexpected error occurred!";
            }

            stopwatch.stop();
            return "Finished in " + stopwatch;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(!s.contains("error")) {
                EventBus bus = EventBus.getDefault();
                bus.post(new IdentificationFinishedEvent(mIdentificationModel));
            }

            txtInfo.setText(s.trim());
            btnRun.setEnabled(true);
            btnRun.setBackgroundColor(getResources().getColor(R.color.btn_run_enabled));

        }
    }

}
