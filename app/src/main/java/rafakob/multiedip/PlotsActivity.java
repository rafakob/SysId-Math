package rafakob.multiedip;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rafakob.multiedip.idsys.Correlation;
import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.Psd;


public class PlotsActivity extends ActionBarActivity implements
        OnChartGestureListener, OnChartValueSelectedListener {

    private static final Integer ID_SOURCE = 0;
    private static final Integer ID_PROCESSED = 1;
    private static final int COLOR_SOURCE = Color.RED;
    private static final int COLOR_PROCESSED = Color.BLUE;
    private String lblSource;
    private String lblProcessed;

    private FloatingActionMenu fabPlotMenu;

    private LineChart mChart;
    private IdData gDataSource;
    private IdData gDataProcessed;
    private LineData mLineData;
    private String mTitlePre;

    HashMap<Integer, PlotDataset> plotDataSets;
    List<LineDataSet> visibleDataSets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enter fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plots);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabPlotMenu = (FloatingActionMenu) findViewById(R.id.fab_menu_plots);
        mTitlePre = getString(R.string.activity_plots_title_pre);
        lblSource = getString(R.string.plot_lbl_source);
        lblProcessed = getString(R.string.plot_lbl_processed);

        // get reference to global app data objects
        gDataSource = ((GlobalApp) getApplicationContext()).getDataSource();
        gDataProcessed = ((GlobalApp) getApplicationContext()).getDataProcessed();

        // init
        setTitle(mTitlePre + getString(R.string.plot_name_1));
        mLineData = new LineData();
        plotDataSets = new HashMap<>();


        initChart();
        initOutputSignal();
        updateChartData(true, false);
    }


    /**
     * ******************************************************************************
     * Chart initialization
     * ******************************************************************************
     */
    private void initChart() {
        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataText(getString(R.string.plot_info_no_chart));
        mChart.setNoDataTextDescription(getString(R.string.plot_info_provide_data));

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // enable/disable highlight indicators (the lines that indicate the highlighted Entry)
        mChart.setHighlightIndicatorEnabled(false);

        // axis limit values
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setStartAtZero(false);
        mChart.getAxisRight().setEnabled(false);
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
    }


    /**
     * ******************************************************************************
     * Setting data
     * ******************************************************************************
     */
    private void initOutputSignal() {
        plotDataSets.clear();
        if (!gDataSource.isNull())
            plotDataSets.put(ID_SOURCE, new PlotDataset(gDataSource.getSamples(), gDataSource.getOutput(), lblSource, COLOR_SOURCE));

        if (!gDataProcessed.isNull())
            plotDataSets.put(ID_PROCESSED, new PlotDataset(gDataProcessed.getSamples(), gDataProcessed.getOutput(), lblSource, COLOR_PROCESSED));
    }

    private void initInputSignal() {
        plotDataSets.clear();
        if (!gDataSource.isNull() && gDataSource.isSiso())
            plotDataSets.put(ID_SOURCE, new PlotDataset(gDataSource.getSamples(), gDataSource.getInput(), lblSource, COLOR_SOURCE));

        if (!gDataProcessed.isNull() && gDataProcessed.isSiso())
            plotDataSets.put(ID_PROCESSED, new PlotDataset(gDataProcessed.getSamples(), gDataProcessed.getInput(), lblProcessed, COLOR_PROCESSED));
    }


    private void initAutocorr() {
        plotDataSets.clear();

        if (!gDataSource.isNull()) {
            Correlation.auto(gDataSource.getOutput(), "biased");

            PlotDataset tmp = new PlotDataset(gDataSource.getSamples(), Correlation.auto, lblSource, COLOR_SOURCE);
            tmp.getSet().setCircleSize(4f);
            tmp.getSet().setLineWidth(0f);
            tmp.getSet().setDrawCircles(true);
            plotDataSets.put(ID_SOURCE, tmp);
        }

        if (!gDataProcessed.isNull()) {
            Correlation.auto(gDataProcessed.getOutput(), "biased");
            PlotDataset tmp = new PlotDataset(gDataProcessed.getSamples(), Correlation.auto, lblProcessed, COLOR_PROCESSED);
            tmp.getSet().setCircleSize(4f);
            tmp.getSet().setLineWidth(0f);
            tmp.getSet().setDrawCircles(true);
            plotDataSets.put(ID_PROCESSED, tmp);
        }
    }

    private void initPsd() {
        plotDataSets.clear();
        if (!gDataSource.isNull()) {
            Correlation.auto(gDataSource.getOutput(), "biased");
            Psd.periodogram(Correlation.auto);
            plotDataSets.put(ID_SOURCE, new PlotDataset(Psd.freq, Psd.vals, lblSource, COLOR_SOURCE));
        }

        if (!gDataProcessed.isNull()) {
            Correlation.auto(gDataProcessed.getOutput(), "biased");
            Psd.periodogram(Correlation.auto);
            plotDataSets.put(ID_PROCESSED, new PlotDataset(Psd.freq, Psd.vals, lblProcessed, COLOR_PROCESSED));
        }
    }


    private void updateChartData(boolean redraw, boolean leftAxisAtZero) {
        mLineData.clearValues();
        mChart.clear();
        visibleDataSets.clear();
        ArrayList<String> xVals = new ArrayList<>();

        for (PlotDataset plot : plotDataSets.values()) {
            if (plot.isVisible()) {

                if (plot.getxVals().size() > xVals.size())
                    xVals = plot.getxVals();

                visibleDataSets.add(plot.getSet());
            }
        }

        if (visibleDataSets.isEmpty())
            return;

        mLineData = new LineData(xVals, visibleDataSets);
        if (redraw) {
            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setAxisMaxValue((float) (mLineData.getYMax() + Math.abs(mLineData.getYMax() * 0.15)));
            leftAxis.setAxisMinValue((float) (mLineData.getYMin() - Math.abs(mLineData.getYMin() * 0.15)));
            leftAxis.setStartAtZero(leftAxisAtZero);
        }

        mChart.setData(mLineData);

        if (redraw)
            mChart.animateX(1000, Easing.EasingOption.EaseInOutQuart);

        mChart.invalidate();
    }


    /**
     * ******************************************************************************
     * Plots listeners
     * ******************************************************************************
     */
    @Override
    public void onChartLongPressed(MotionEvent me) {
//        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
//        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
//        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
//        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//        Log.i("Entry selected", e.toString());
//        Log.i("", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {
//        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * ******************************************************************************
     * Button actions
     * ******************************************************************************
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    public void onPlotsMenu1Click(View v) {
        setTitle(mTitlePre + getString(R.string.plot_name_1));
        initOutputSignal();
        updateChartData(true, false);
        fabPlotMenu.close(true);
    }

    public void onPlotsMenu2Click(View v) {
        setTitle(mTitlePre + getString(R.string.plot_name_2));
        initInputSignal();
        updateChartData(true, false);
        fabPlotMenu.close(true);
    }

    public void onPlotsMenu3Click(View v) {
        setTitle(mTitlePre + getString(R.string.plot_name_3));
        initAutocorr();
        updateChartData(true, false);
        fabPlotMenu.close(true);
    }

    public void onPlotsMenu4Click(View v) {
        setTitle(mTitlePre + getString(R.string.plot_name_4));
        initPsd();
        updateChartData(true, true);
        fabPlotMenu.close(true);
    }

    public void toggleSource(View v) {
        plotDataSets.get(ID_SOURCE).setVisible(!plotDataSets.get(ID_SOURCE).isVisible());
        updateChartData(false, false);
    }

    public void toggleProcessed(View v) {
        plotDataSets.get(ID_PROCESSED).setVisible(!plotDataSets.get(ID_PROCESSED).isVisible());
        updateChartData(false, false);
    }


    /**
     * ******************************************************************************
     * Menu on ActionBar
     * ******************************************************************************
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            case R.id.actionToggleValues: {
                for (DataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
                        .getDataSets();

                for (LineDataSet set : sets) {
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {

                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
                        .getDataSets();

                for (LineDataSet set : sets) {
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChart.invalidate();
                break;
            }

            case R.id.actionToggleCubic: {
                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mChart.getData()
                        .getDataSets();

                for (LineDataSet set : sets) {
                    if (set.isDrawCubicEnabled())
                        set.setDrawCubic(false);
                    else
                        set.setDrawCubic(true);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(3000, 3000);
                break;
            }

            case R.id.actionSave: {
                if (mChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.plot_saving_success),
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.plot_saving_failed), Toast.LENGTH_SHORT)
                            .show();

                // mChart.saveToGallery("title"+System.currentTimeMillis())
                break;
            }
        }
        return true;
    }


}

