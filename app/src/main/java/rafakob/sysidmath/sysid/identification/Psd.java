package rafakob.sysidmath.sysid.identification;

import org.apache.commons.math3.linear.MatrixUtils;

import rafakob.sysidmath.sysid.MathDbl;
import rafakob.sysidmath.sysid.processing.Fft;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-05-22
 */
public class Psd {
    public static double[] freq;
    public static double[] vals;

    public static void periodogram(double[] x, double ts){
        int N = x.length;
        double[] y = x.clone();
        double[] u = MathDbl.getFilledVector(N, 0);
        double[] f = new double[N];

        Fft.transform(y, u);

        double f_step = (2*Math.PI)/N;
        for (int i = 0; i < N; i++)
            f[i] = i*f_step;

        y = MathDbl.multiplyScalar(y, ts);
        y = MathDbl.multiplyVectors(y, y);
        y = MathDbl.multiplyScalar(y, 1/(N*ts));


        freq = MathDbl.extract(0, N / 2 + 1, f);
        vals = MathDbl.extract(0, N / 2 + 1, y);
    }

    public static void corelogram(double[] Rxx){
        int N = Rxx.length;
        double[] f = new double[N];
        double[] real = Rxx.clone();
        double[] imag = MathDbl.getFilledVector(Rxx.length, 0);
        double f_step = (2*Math.PI)/N;

        for (int i = 0; i < N; i++)
            f[i] = i*f_step;

        Fft.transform(real,imag);

        freq = MathDbl.extract(0, N / 2 + 1, f);
        vals = MathDbl.extract(0, N / 2 + 1, real);
    }
}
