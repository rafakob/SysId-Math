package rafakob.multiedip.idsys;

public class Autocorr {


    public static double[] execute(double[] y, int kmax) {
        int N = y.length;
        double r0 = 0.0;
        double[] R = new double[kmax];
        double[] r = new double[kmax];
        double rk;
        double sum = 0.0;
        for (int k = 0; k < kmax; k++) {

            if (k == 0) {

                for (int i = 0; i < N; i++)
                    sum += Math.pow(y[i], 2);

                r0 = sum / N;
                R[0] = r0 / r0;
                r[0] = r0;
            } else {
                sum = 0;
                for (int i = k; i < N; i++) {
                    sum += y[i] * y[i - k];
                }
                rk = sum / (N - k);

                R[k] = rk / r0;
                r[k] = rk;
            }
        }
        return R;
    }
}
