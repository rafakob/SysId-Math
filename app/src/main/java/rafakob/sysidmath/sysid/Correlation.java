package rafakob.sysidmath.sysid;

import java.util.Arrays;

public class Correlation {

    public static double[] auto;

    public static void auto(double[] x, String type) {
        int N = x.length;
        double[] Rxx = MathDbl.getFilledVector(N, 0);

        for (int m = 0; m < N; m++) {
            for (int i = 0; i < N-1-m; i++) {
                Rxx[m] += x[i+m]*x[i];
            }

            if(type.equals("biased"))
                Rxx[m] = Rxx[m]/N;

            else if(type.equals("unbiased"))
                Rxx[m] = Rxx[m]/(N-m);
        }
        auto = Rxx;
    }

//    public static double[] execute(double[] y, int kmax) {
//        int N = y.length;
//        double r0 = 0.0;
//        double[] R = new double[kmax];
//        double[] r = new double[kmax];
//        double rk;
//        double sum = 0.0;
//        for (int k = 0; k < kmax; k++) {
//
//            if (k == 0) {
//
//                for (int i = 0; i < N; i++)
//                    sum += Math.pow(y[i], 2);
//
//                r0 = sum / N;
//                R[0] = r0 / r0;
//                r[0] = r0;
//            } else {
//                sum = 0;
//                for (int i = k; i < N; i++) {
//                    sum += y[i] * y[i - k];
//                }
//                rk = sum / (N - k);
//
//                R[k] = rk / r0;
//                r[k] = rk;
//            }
//        }
//        return R;
//    }


    /**
     * Computes the cross correlation between sequences a and b.
     */
    public static double[] xcorr(double[] a, double[] b)
    {
        int len = a.length;
        if(b.length > a.length)
            len = b.length;

        return xcorr(a, b, len-1);

        // // reverse b in time
        // double[] brev = new double[b.length];
        // for(int x = 0; x < b.length; x++)
        //     brev[x] = b[b.length-x-1];
        //
        // return conv(a, brev);
    }

    /**
     * Computes the auto correlation of a.
     */
    public static double[] xcorr(double[] a)
    {
        return xcorr(a, a);
    }

    /**
     * Computes the cross correlation between sequences a and b.
     * maxlag is the maximum lag to
     */
    public static double[] xcorr(double[] a, double[] b, int maxlag)
    {
        double[] y = new double[2*maxlag+1];
        Arrays.fill(y, 0);

        for(int lag = b.length-1, idx = maxlag-b.length+1;
            lag > -a.length; lag--, idx++)
        {
            if(idx < 0)
                continue;

            if(idx >= y.length)
                break;

            // where do the two signals overlap?
            int start = 0;
            // we can't start past the left end of b
            if(lag < 0)
            {
                //System.out.println("b");
                start = -lag;
            }

            int end = a.length-1;
            // we can't go past the right end of b
            if(end > b.length-lag-1)
            {
                end = b.length-lag-1;
                //System.out.println("a "+end);
            }

            //System.out.println("lag = " + lag +": "+ start+" to " + end+"   idx = "+idx);
            for(int n = start; n <= end; n++)
            {
                //System.out.println("  bi = " + (lag+n) + ", ai = " + n);
                y[idx] += a[n]*b[lag+n];
            }
            //System.out.println(y[idx]);
        }

        return(y);
    }
}
