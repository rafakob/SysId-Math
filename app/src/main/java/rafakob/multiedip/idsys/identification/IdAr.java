package rafakob.multiedip.idsys.identification;

import com.google.common.primitives.Doubles;

import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;

import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MathDbl;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-04-14
 */
public class IdAr implements IdentificationModel {
    double[] A;
    double[] B;
    long length;
    int dA, dB, k;
    double L;


    public IdAr(int dA) {
        this.dA = dA;
        dB = 0;
        k = 1;
        L = 1;

    }

    @Override
    public void execute(IdData iddata) {
        double[] y = iddata.getOutput();
        double[] u = new double[y.length];
        Arrays.fill(u,0);
        this.length = iddata.getLength();

//        int dA = 2, dB = 1, k = 1;
//        double L = 1;
        int n = dB + dA + 1;
        int S = 1000;


        SimpleMatrix theta = new SimpleMatrix(n, 1);
        SimpleMatrix kw = new SimpleMatrix(n, 1);
        SimpleMatrix fi = new SimpleMatrix(n, 1);
        theta.zero();
        kw.zero();
        fi.zero();

        SimpleMatrix P = SimpleMatrix.identity(n).scale(S);
        SimpleMatrix num, dem, E, Ptemp;

//        int dA = 2, dB = 1, k = 1;
        for (int i = 0; i < y.length; i++) {


            if(i>=k+dB)
                fi.setColumn(0, 0, getReverseVec(i-k-dB, i-k, u));

            if(i>=dA)
                fi.setColumn(0, k + dB, getReverseVecNeg(i - dA, i - 1, y));

            num = P.mult(fi).mult(fi.transpose()).mult(P);
            dem = fi.transpose().mult(P).mult(fi).plus(L);

            Ptemp = (P.minus(num.divide(dem.get(0)))).divide(L);
            if ((Ptemp.trace()) < (S * n + 1))
                P = Ptemp;

            kw = P.mult(fi);
            E = (theta.transpose().mult(fi)).divide(-1).plus(y[i]);

            theta = (kw.mult(E)).plus(theta);

            // b0 b1 a1 a2

            B = MathDbl.extract(0, dB, MathDbl.SimpleMatrixToDoubles(theta));
            A = Doubles.concat(MathDbl.getNumberAsArray(1.0),
                    MathDbl.extract(dB + 1, MathDbl.SimpleMatrixToDoubles(theta).length - 1, MathDbl.SimpleMatrixToDoubles(theta)));


        }

    }

    @Override
    public String getResult() {
        return "AR model: \n \tA = " + MathDbl.getString(A);

    }


    private double[] getReverseVecNeg(int start, int end, double[] values) {
        int len = end - start + 1;
        double[] v = new double[len];

        for (int i = 0; i < len; i++) {
            v[i] = values[end - i] * (-1);
        }
        return v;
    }


    private double[] getReverseVec(int start, int end, double[] values) {
        int len = end - start + 1;
        double[] v = new double[len];

        for (int i = 0; i < len; i++) {
            v[i] = values[end - i];
        }

        return v;
    }

    @Override
    public String getFunctionDescription() {
        return "AR(" + dA + ")";
    }
}
