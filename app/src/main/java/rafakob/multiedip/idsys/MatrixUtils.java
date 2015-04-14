package rafakob.multiedip.idsys;

import org.ejml.simple.SimpleMatrix;

import java.text.DecimalFormat;

/**
 *
 */
public class MatrixUtils {
    public static DecimalFormat df = new DecimalFormat("0.0000");

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
        String s = new String();
        s = "[";

        for (int i = 0; i < array.length; i++) {
            s = s + df.format(array[i]);
            if (i < array.length - 1)
                s = s + ", ";
        }

        s = s + "]";

        return s;
    }
}
