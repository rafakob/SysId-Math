package rafakob.sysidmath;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.greenrobot.event.EventBus;
import rafakob.sysidmath.bus.IdentificationFinishedEvent;
import rafakob.sysidmath.others.ContentBox;


public class ResultFragment extends Fragment {
    private EventBus mBus = EventBus.getDefault();
    private ContentBox mResultBox;
    private Context mContext;

    public ResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus.register(this);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setupView(inflater.inflate(R.layout.fragment_result, container, false));
    }

    private View setupView(View view) {
        mResultBox = new ContentBox(view, R.id.box_result);
        mResultBox.setButtonVisibility(View.GONE);
        mResultBox.setTitle(R.string.box_result);
        return view;
    }

    /**
     * Update box when identification has been finished
     */
    public void onEvent(IdentificationFinishedEvent event) {
        mResultBox.cleatGrid();
        mResultBox.addToGrid(0, 0, event.idModel.getResult());

        if(event.idModel.getFunctionDescription().equals("Nonparametric model"))
            mResultBox.addButtonToGrid(1, 0, "Plots");
    }

}
