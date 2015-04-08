package rafakob.multiedip.idsys;

import com.google.common.math.DoubleMath;

import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public class DataStatistics {
    private double mean = 0;
    private double stddev = 0;
    private double skewness = 0;
    private double kurtosis = 0;
    private double peakcoef = 0;
    
    
    private static final int DECIMAL_PLACES = 5;


    public void calculate(double[] val){
        mean(val);
        stddev(val);
        skewness(val);
        kurtosis(val);
    }



    private void mean(double[] val){
        mean = round(DoubleMath.mean(val), DECIMAL_PLACES);
    }

    private void stddev(double[] val){
        stddev = round(Math.sqrt(var(val)), DECIMAL_PLACES);
    }

    private void skewness(double[] val){
        skewness = round((new Skewness()).evaluate(val), DECIMAL_PLACES);
    }

    private void kurtosis(double[] val){
        kurtosis = round((new Kurtosis()).evaluate(val), DECIMAL_PLACES);
    }



    /**
     * Returns the sample variance in the array a[], NaN if no such value.
     */
    private double var(double[] val) {
        if (val.length == 0) return Double.NaN;
        double avg = DoubleMath.mean(val);
        double sum = 0.0;
        for (int i = 0; i < val.length; i++) {
            sum += (val[i] - avg) * (val[i] - avg);
        }
        return sum / (val.length - 1);
    }


    /**
     * Taken from http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     */
    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    /*
     * Getters
     *
     */
    public double getMean() {
        return mean;
    }

    public double getStddev() {
        return stddev;
    }

    public double getKurtosis() {
        return kurtosis;
    }

    public double getSkewness() {
        return skewness;
    }

    public double getPeakcoef() {
        return peakcoef;
    }
}
