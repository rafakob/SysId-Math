package rafakob.multiedip;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-05-26
 */
public class PlotDataset {
    private double[] x;
    private double[] y;
    private ArrayList<String> xVals;
    private ArrayList<Entry> yVals;
    private String label;
    private int colorId;
    private LineDataSet set;
    private boolean visible = true;

    public PlotDataset(double[] y, String label, int colorId) {
        this.x = new double[y.length];
        this.y = y;
        this.label = label;
        this.colorId = colorId;

        for (int i = 0; i < y.length; i++)
            x[i] = i;

        initSet();
    }

    public PlotDataset(double[] x, double[] y, String label, int colorId) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.colorId = colorId;

        initSet();
    }



    private void initSet(){
        DecimalFormat df = new DecimalFormat("0.00");
        xVals = new ArrayList<>();
        yVals = new ArrayList<>();

        // create data entries for both axis
        for (int i = 0; i < y.length; i++) {
            xVals.add(df.format(x[i]) + "");
            yVals.add(new Entry( (float) y[i], i));
        }

        // setup line set:
        set = new LineDataSet(yVals, label);

        // set the line to be drawn like this "- - - - - -"
        // set.enableDashedLine(10f, 5f, 0f);
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
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public LineDataSet getSet() {
        return set;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ArrayList<String> getxVals() {
        return xVals;
    }

    public void setxVals(ArrayList<String> xVals) {
        this.xVals = xVals;
    }

    public ArrayList<Entry> getyVals() {
        return yVals;
    }

    public void setyVals(ArrayList<Entry> yVals) {
        this.yVals = yVals;
    }

}
