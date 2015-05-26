package rafakob.multiedip;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import de.greenrobot.event.EventBus;
import rafakob.multiedip.bus.LoadDataFinishedEvent;
import rafakob.multiedip.idsys.DataStatistics;
import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.others.ContentBox;

public class StatisticsFragment extends Fragment {
    private EventBus mBus = EventBus.getDefault();
    private ContentBox mSourceBox;
    private ContentBox mProcessedBox;
    private Context mContext;
    private DataStatistics mStatistics;
    private IdData dataSource;
    private IdData dataProcessed;

    public StatisticsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        dataSource = ((GlobalApp) mContext).getDataSource();
        dataProcessed = ((GlobalApp) mContext).getDataProcessed();
        mStatistics = new DataStatistics();
        mBus.register(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setupView(inflater.inflate(R.layout.fragment_statistics, container, false));
    }

    private View setupView(View view) {
        mSourceBox = new ContentBox(view, R.id.box_statistics_source);
        mSourceBox.setButtonVisibility(View.GONE);
        mSourceBox.setTitle(R.string.box_stat_source);

        mProcessedBox = new ContentBox(view, R.id.box_statistics_processed);
        mProcessedBox.setButtonVisibility(View.GONE);
        mProcessedBox.setTitle(R.string.box_stat_processed);

        updateStatisticsBox();

        return view;
    }

    private void updateStatisticsBox(){
        mSourceBox.cleatGrid();
        mProcessedBox.cleatGrid();

        if(!dataSource.isNull()) {
            if(dataSource.isSiso()) {
                addToStatisticsBox(mSourceBox, 0, R.string.stats_input_signal, dataSource.getInput());
            }
            addToStatisticsBox(mSourceBox, 6, R.string.stats_output_signal, dataSource.getOutput());
        }

        if(!dataProcessed.isNull()) {
            if(dataProcessed.isSiso()) {
                addToStatisticsBox(mProcessedBox, 0, R.string.stats_input_signal, dataProcessed.getInput());
            }
            addToStatisticsBox(mProcessedBox, 6, R.string.stats_output_signal, dataProcessed.getOutput());
        }

    }


    private void addToStatisticsBox(ContentBox box, int rowStart, int resSignalStringId, double[] values) {
        mStatistics.calculate(values);

        box.addToGrid(rowStart + 1, 0, R.string.stats_mean);
        box.addToGrid(rowStart + 2, 0, R.string.stats_stddev);
        box.addToGrid(rowStart + 3, 0, R.string.stats_skewness);
        box.addToGrid(rowStart + 4, 0, R.string.stats_kurtosis);
        box.addToGrid(rowStart + 5, 0, R.string.stats_peakcoef);

        box.addToGrid(rowStart , 1, resSignalStringId, Gravity.END);
        box.addToGrid(rowStart + 1, 1, mStatistics.getMean() + "", Gravity.END);
        box.addToGrid(rowStart + 2, 1, mStatistics.getStddev() + "", Gravity.END);
        box.addToGrid(rowStart + 3, 1, mStatistics.getSkewness() + "", Gravity.END);
        box.addToGrid(rowStart + 4, 1, mStatistics.getKurtosis() + "", Gravity.END);
        box.addToGrid(rowStart + 5, 1, mStatistics.getPeakcoef() + "", Gravity.END);
    }


    /**
     * Calculate statistics
     */
    public void onEvent(LoadDataFinishedEvent event) {
        updateStatisticsBox();
    }

}
