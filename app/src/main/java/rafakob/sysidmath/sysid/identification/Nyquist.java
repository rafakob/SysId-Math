package rafakob.sysidmath.sysid.identification;

import java.util.Arrays;
import java.util.Comparator;

import rafakob.sysidmath.sysid.MathDbl;
import rafakob.sysidmath.sysid.processing.Fft;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-06-27
 */
public class Nyquist {
    public static double[] yData;
    public static double[] xData;
    private static double[] hReal;
    private static double[] hImag;


    public static void execute(double[] u, double[] y){
        int N = y.length;
        double[] f = new double[N];
        xData = new double[N];
        yData = new double[N];
        double[] den;

        double[] uImag = MathDbl.getFilledVector(N, 0);
        double[] yImag = MathDbl.getFilledVector(N, 0);
        double[] uReal = u.clone();
        double[] yReal = y.clone();

        Fft.transform(uReal, uImag);
        Fft.transform(yReal, yImag);

        den = MathDbl.addVectors(MathDbl.multiplyVectors(uReal,uReal), MathDbl.multiplyVectors(uImag,uImag));

        hReal = MathDbl.addVectors(MathDbl.multiplyVectors(yReal, uReal), MathDbl.multiplyVectors(yImag, uImag));
        hImag = MathDbl.subtractVectors(MathDbl.multiplyVectors(yImag, uReal), MathDbl.multiplyVectors(yReal, uImag));

        hReal = MathDbl.divideVectors(hReal, den);
        hImag = MathDbl.divideVectors(hImag, den);

        Integer[] idx = new Integer[hReal.length];
        for( int i = 0 ; i < idx.length; i++ )
            idx[i] = i;
        Arrays.sort(idx, new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return Double.compare(hReal[i1], hReal[i2]);
            }
        });

        for (int i = 0; i < idx.length; i++) {
            xData[i] = hReal[idx[i]];
            yData[i] = hImag[idx[i]];
        }

        xData = MathDbl.extract(0, N / 2 - 1, xData);
        yData = MathDbl.extract(0, N / 2 - 1, yData);
//        xData = MathDbl.extract(0, N / 2 - 1, hReal);
//        yData = MathDbl.extract(0,N/2-1,hImag);
    }

}
