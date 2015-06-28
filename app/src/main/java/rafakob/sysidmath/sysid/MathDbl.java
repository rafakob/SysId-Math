package rafakob.sysidmath.sysid;

import org.ejml.simple.SimpleMatrix;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 *
 */
public class MathDbl {
    public static DecimalFormat df = new DecimalFormat("0.000000");

    public static double[] extract(int start, int end, double[] array) {
        int len = end - start + 1;
        double[] v = new double[len];

        for (int i = 0; i < len; i++) {
            v[i] = array[i + start];
        }
        return v;
    }

    public static double[] SimpleMatrixToDoubles(SimpleMatrix matrix) {
        double[] v = new double[matrix.getNumElements()];
        for (int i = 0; i < matrix.getNumElements(); i++) {
            v[i] = matrix.get(i);
        }

        return v;
    }

    public static double[] getNumberAsArray(double number) {
        double[] v = new double[1];
        v[0] = number;
        return v;
    }

    public static String getString(double[] array) {
        String s = "[";

        for (int i = 0; i < array.length; i++) {
            s = s + df.format(array[i]).replace(',','.');
            if (i < array.length - 1)
                s = s + ", ";
        }

        s = s + "]";

        return s;
    }

    public static double[] getFilledVector(int length, double fillValue){
        double[] v = new double[length];
        Arrays.fill(v, fillValue);
        return v;
    }

    public static double[] multiplyScalar(double[] v, double multiplier){
        for (int i = 0; i < v.length; i++) {
            v[i] = v[i]*multiplier;
        }
        return v;
    }

    public static double[] addScalar(double[] v, double scalar){
        for (int i = 0; i < v.length; i++) {
            v[i] = v[i]+scalar;
        }
        return v;
    }

    public static double[] vectorAbs(double[] v){
        for (int i = 0; i < v.length; i++) {
            v[i] = Math.abs(v[i]);
        }
        return v;
    }

    public static double[] multiplyVectors(double[] v1, double[] v2) {
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] * v2[i];
        }
        return result;
    }

    public static double[] addVectors(double[] v1, double[] v2) {
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] + v2[i];
        }
        return result;
    }

    public static double[] divideVectors(double[] v1, double[] v2) {
        double[] result = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            result[i] = v1[i] / v2[i];
        }
        return result;
    }

    public static double sumElements(double[] v){
        double sum = 0;
        for (double aV : v) {
            sum += aV;
        }

        return sum;
    }

    public static double[] subtractVectors(double[] v1, double[] v2) {
        double[] y = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            y[i] = v1[i] - v2[i];
        }
        return y;
    }
}
