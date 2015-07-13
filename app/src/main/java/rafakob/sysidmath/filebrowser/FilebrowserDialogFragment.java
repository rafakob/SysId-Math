package rafakob.sysidmath.filebrowser;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import rafakob.sysidmath.R;
import rafakob.sysidmath.bus.SelectFileEvent;


public class FilebrowserDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
    private FilebrowserAdapter mFilesAdapter = new FilebrowserAdapter();
    private ListView mFilesListView;
    public String selectedPath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        selectedPath = getArguments().getString("START_PATH");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle(R.string.btn_select_source_file);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e5e5e5")));
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {

                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    if(keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        // Button.onRelease()
                        goUp();
                    }
                }
                return true;
            }
        });


        View listView = inflater.inflate(R.layout.fragment_dialog_filebrowser, container);
        mFilesListView = (ListView) listView.findViewById(R.id.filepicker_list);

        Button cancel = (Button) listView.findViewById(R.id.filepicker_btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return listView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mFilesAdapter.getFilteredContentOfDirectory(selectedPath);
        mFilesListView.setAdapter(mFilesAdapter);
        mFilesListView.setOnItemClickListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        Window dialogWindow = getDialog().getWindow();
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialogWindow.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(mFilesAdapter.getSelectedFileName(i).endsWith(".txt")){
            EventBus bus = EventBus.getDefault();
            bus.post(new SelectFileEvent(selectedPath+"/"+mFilesAdapter.getSelectedFileName(i)));

            this.dismiss();
        }
        else{
            selectedPath += "/" + mFilesAdapter.getSelectedFileName(i);
            refreshDirContent();
        }
     }

    private void goUp(){
        if(!selectedPath.equals(ROOT_PATH)) {
            selectedPath = selectedPath.substring(0, selectedPath.lastIndexOf("/"));
            refreshDirContent();
        }
        else{
            Toast.makeText(this.getActivity(), R.string.error_root_browse, Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshDirContent(){
        mFilesAdapter.getFilteredContentOfDirectory(selectedPath);
        mFilesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
