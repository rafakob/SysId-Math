package rafakob.multiedip;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import rafakob.multiedip.bus.LoadDataFinishedEvent;
import rafakob.multiedip.bus.SelectFileEvent;
import rafakob.multiedip.bus.SettingsChangedEvent;
import rafakob.multiedip.filebrowser.FilebrowserDialogFragment;
import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.identification.IdentificationModel;
import rafakob.multiedip.idsys.processing.DataProcessingFunction;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private TextView txtFilename;
    private TextView txtPath;
    private TextView txtDataType;
    private TextView txtLength;
    private TextView txtInfo;
    private Context mContext;
    private PrefManager mPrefManager;
    private ArrayList<DataProcessingFunction> mPreprocessingTasks;
    private IdentificationModel mIdentificationModel;


    private DialogFragment filePickerDialogFragment;
    private String currentBrowseStartPath;

    private EventBus bus = EventBus.getDefault();
    private IdData iddata;
    private ContentBox mBox1, mBox2, mBox3;

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DashboardFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DashboardFragment newInstance(String param1, String param2) {
//        DashboardFragment fragment = new DashboardFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bus.register(this);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        mContext = getActivity().getApplicationContext();
        mPrefManager = new PrefManager(mContext);
        txtFilename = new TextView(mContext);
        txtPath = new TextView(mContext);
        txtDataType = new TextView(mContext);
        txtLength = new TextView(mContext);
        txtInfo = new TextView(mContext);
        iddata = new IdData();

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
        mBox1.addToGrid(new TextView(mContext), R.string.lbl_filename, 0, 0);
        mBox1.addToGrid(new TextView(mContext), R.string.lbl_path, 1, 0);
        mBox1.addToGrid(new TextView(mContext), R.string.lbl_data_type, 2, 0);
        mBox1.addToGrid(new TextView(mContext), R.string.lbl_length_source, 3, 0);

        mBox1.addToGrid(txtFilename, "", 0, 1);
        mBox1.addToGrid(txtPath, "", 1, 1);
        mBox1.addToGrid(txtDataType, "", 2, 1);
        mBox1.addToGrid(txtLength, "", 3, 1);

        /** Others **/
        Button btnRun = (Button) view.findViewById(R.id.btn_run);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRunClick();
            }
        });

        txtInfo = (TextView) view.findViewById(R.id.lbl_info);
        txtInfo.setText("");

        updatePreprocessingTasks();
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
//        new Normalization().execute(iddata);

//        DataProcessing dp = new DataProcessing();
//        ArrayList mPreprocessingTasks = new ArrayList<DataProcessingFunction>();
//        mPreprocessingTasks.add(new Normalization());
//        mPreprocessingTasks.add(new Normalization());


//        Toast.makeText(mContext, dp.process(iddata,mPreprocessingTasks).getOutput()[0] + "", Toast.LENGTH_SHORT).show();
    }

    private void updatePreprocessingTasks() {
        mPreprocessingTasks = mPrefManager.getPreprocesingConfig();
    }

    private void updateSettingsBoxes() {
        mBox2.cleatGrid();
        if (!mPreprocessingTasks.isEmpty()) {
            for (int i = 0; i < mPreprocessingTasks.size(); i++) {
                mBox2.addToGrid(new TextView(mContext), mPreprocessingTasks.get(i).getFunctionDescription(), i, 0);
            }
        }

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
     *
     * @param event
     */
    public void onEvent(SettingsChangedEvent event) {
        updatePreprocessingTasks();
        updateSettingsBoxes();
    }


}
