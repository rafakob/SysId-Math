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
        freq = new double[N];
        double[] real = Rxx.clone();
        double[] imag = MatrixUtils.getFilledVector(Rxx.length,0);
        double f_step = (2*Math.PI)/N;

        for (int i = 0; i < N; i++)
            freq[i] = i*f_step;

        Fft.transform(real,imag);

        freq = MatrixUtils.extract(0,N/2+1,freq);
        vals = MatrixUtils.extract(0,N/2+1,real);

    }
}
