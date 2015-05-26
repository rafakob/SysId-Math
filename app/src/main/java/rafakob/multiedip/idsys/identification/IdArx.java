package rafakob.multiedip.idsys.identification;

import com.google.common.primitives.Doubles;

import org.ejml.simple.SimpleMatrix;

import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MatrixUtils;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-04-06
 */
public class IdArx implements IdentificationModel {
    double[] A;
    double[] B;
    long length;
    int dA, dB, k;
    double L;

    public IdArx(int dA, int dB, int k, double l) {
        this.dA = dA;
        this.dB = dB;
        this.k = k;
        this.L = 1;
    }

    @Override
    public void execute(IdData iddata) {
        double[] y = iddata.getOutput();
        double[] u = iddata.getInput();
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

            B = MatrixUtils.extract(0, dB, MatrixUtils.SimpleMatrixToDoubles(theta));
            A = Doubles.concat(MatrixUtils.getNumberAsArray(1.0),
                    MatrixUtils.extract(dB + 1, MatrixUtils.SimpleMatrixToDoubles(theta).length - 1, MatrixUtils.SimpleMatrixToDoubles(theta)));


        }

    }

    private double[] getA() {
        return A;
    }

    private double[] getB() {
        return B;
    }

    @Override
    public String getResult() {
        return "ARX model: \n \tA = " + MatrixUtils.getString(A) +
                "\n\tB = " + MatrixUtils.getString(B);

    }

    @Override
    public String getFunctionDescription() {
        return "ARX(" + dA + ", " + dB + ", " + k + ")";
    }

    private double[] getReverseVec(int start, int end, double[] values) {
        int len = end - start + 1;
        double[] v = new double[len];

        for (int i = 0; i < len; i++) {
            v[i] = values[end - i];
        }

        return v;
    }

    private double[] getReverseVecNeg(int start, int end, double[] values) {
        int len = end - start + 1;
        double[] v = new double[len];

        for (int i = 0; i < len; i++) {
            v[i] = values[end - i] * (-1);
        }
        return v;
    }
}


//Discrete-time ARX model:  A(z)y(t) = B(z)u(t) + e(t)
//        A(z) = 1 - 1.5 z^-1 + 0.7 z^-2
//
//        B(z) = z^-1 + 0.5 z^-2
//
//        Sample time: 1 seconds
//
//        Parameterization:
//        Polynomial orders:   na=2   nb=2   nk=1