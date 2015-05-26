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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import java.util.ArrayList;
import java.util.Arrays;

import rafakob.multiedip.idsys.Autocorr;
import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MatrixUtils;


public class PlotsActivity extends ActionBarActivity implements
        OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart mLineChart;
    private IdData gDataSource;
    private IdData gDataProcessed;
    LineData mLineData;
    ArrayList<String> xVals;
    private String mTitlePre = "Plots: ";
    boolean showSource = false;
    boolean showProcessed = false;

    LineDataSet lnsSource;
    LineDataSet lnsProcessed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enter fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_plots);


        gDataSource = ((GlobalApp) getApplicationContext()).getDataSource();
        gDataProcessed = ((GlobalApp) getApplicationContext()).getDataProcessed();
        mLineData = new LineData();
        lnsSource = new LineDataSet(new ArrayList<Entry>(),"");
        lnsProcessed = new LineDataSet(new ArrayList<Entry>(),"");

        setTitle(mTitlePre + "Output signal");
        initLineChart();
    }

    /********************************************************************************
     *
     *   Plots initialization
     *
     ********************************************************************************/

    private void initLineChart(){
        mLineChart = (LineChart) findViewById(R.id.linechart);
        mLineChart.setOnChartGestureListener(this);
        mLineChart.setOnChartValueSelectedListener(this);
        mLineChart.setDrawGridBackground(false);

        // no description text
        mLineChart.setDescription(""); // remove
        mLineChart.setNoDataTextDescription("You need to provide data for the chart."); // remove

        // enable value highlighting
//        mLineChart.setHighlightEnabled(true);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);


        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        // set an alternative background color
        // mLineChart.setBackgroundColor(Color.GRAY);

        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mLineChart.setHighlightIndicatorEnabled(false);



        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setAxisMaxValue(10f);
        leftAxis.setAxisMinValue(-10f);
        leftAxis.setStartAtZero(false);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        mLineChart.getAxisRight().setEnabled(false);

        // add data



//        mLineChart.setVisibleXRange(20);
//        mLineChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mLineChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mLineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mLineChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mLineChart.invalidate();



        if(!gDataSource.isNull()) {
            lnsSource = createLineDataSet(gDataSource.getOutput(),"Data source",Color.RED);
            showSource = true;
        }

        if(!gDataProcessed.isNull()) {
            lnsProcessed = createLineDataSet(gDataProcessed.getOutput(),"Data processed",Color.BLUE);
            showProcessed = true;
        }
        setXVals((int) gDataSource.getLength(), new double[0]);
        setLineChartData();

    }


    /********************************************************************************
     *
     *   Setting data
     *
     ********************************************************************************/
    private LineDataSet createLineDataSet (double[] vals, String label, int colorId){

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < vals.length; i++) {
            yVals.add(new Entry( (float) vals[i], i));
        }

        // create a dataset and give it a type
        LineDataSet set = new LineDataSet(yVals, label);

        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(10f, 5f, 0f);
        set.setColor(colorId);
        set.setCircleColor(colorId);
        set.setLineWidth(1f);
        set.setCircleSize(3f);
        set.setDrawCircleHole(false);
        set.setValueTextSize(9f);
        set.setFillAlpha(65);
        set.setFillColor(colorId);

        set.setDrawCircles(false);
        set.setDrawValues(false);
//        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, mLineChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        return set;
    }



    private void setXVals(int length, double[] xvals){
        xVals = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            if(xvals.length > 0)
                xVals.add(xvals[i] + "");
            else
                xVals.add(i + "");

        }
    }

    public void setLineChartData(){
        if (showSource && !showProcessed)
            mLineData = new LineData(xVals, lnsSource);

        if (!showSource && showProcessed)
            mLineData = new LineData(xVals, lnsProcessed);

        if (showSource && showProcessed)
            mLineData = new LineData(xVals, Arrays.asList(lnsSource,lnsProcessed));

        if (!showSource && !showProcessed)
            mLineData = new LineData();

        mLineChart.setData(mLineData);
        mLineChart.invalidate();
    }


    /********************************************************************************
     *
     *   Plots listeners
     *
     ********************************************************************************/
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
//        Log.i("", "low: " + mLineChart.getLowestVisibleXIndex() + ", high: " + mLineChart.getHighestVisibleXIndex());
    }

    @Override
    public void onNothingSelected() {
//        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /********************************************************************************
     *
     *   Button actions
     *
     ********************************************************************************/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    public void onPlotsMenu1Click(View v) {
        setTitle(mTitlePre + "Output signal");
    }

    public void onPlotsMenu2Click(View v) {
        setTitle(mTitlePre + "Input signal");
    }

    public void onPlotsMenu3Click(View v) {
        setTitle(mTitlePre + "Autocorr");
        Autocorr.execute(gDataSource.getOutput(),5);
    }

    public void toggleSource(View v) {
        showSource = !showSource;
        setLineChartData();
    }

    public void toggleProcessed(View v) {
        showProcessed = !showProcessed;
        setLineChartData();
    }



    /********************************************************************************
     *
     *   Menu on ActionBar
     *
     ********************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plots, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (DataSet<?> set : mLineChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mLineChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mLineChart.getData()
                        .getDataSets();

                for (LineDataSet set : sets) {
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                mLineChart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {

                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mLineChart.getData()
                        .getDataSets();

                for (LineDataSet set : sets) {
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mLineChart.invalidate();
                break;
            }

            case R.id.actionToggleCubic: {
                ArrayList<LineDataSet> sets = (ArrayList<LineDataSet>) mLineChart.getData()
                        .getDataSets();

                for (LineDataSet set : sets) {
                    if (set.isDrawCubicEnabled())
                        set.setDrawCubic(false);
                    else
                        set.setDrawCubic(true);
                }
                mLineChart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (mLineChart.isPinchZoomEnabled())
                    mLineChart.setPinchZoom(false);
                else
                    mLineChart.setPinchZoom(true);

                mLineChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mLineChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mLineChart.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            }
            case R.id.animateXY: {
                mLineChart.animateXY(3000, 3000);
                break;
            }

            case R.id.actionSave: {
                if (mLineChart.saveToPath("title" + System.currentTimeMillis(), "")) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();

                // mLineChart.saveToGallery("title"+System.currentTimeMillis())
                break;
            }
        }
        return true;
    }

}

