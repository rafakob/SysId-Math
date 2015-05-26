package rafakob.multiedip.idsys;

import rafakob.multiedip.idsys.processing.Fft;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-05-22
 */
public class Psd {
    public static double[] freq;
    public static double[] vals;

    public static void periodogram(double[] Rxx){
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
