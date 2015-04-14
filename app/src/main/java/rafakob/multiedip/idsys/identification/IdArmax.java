package rafakob.multiedip.idsys.identification;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-04-06
 */
public class IdArmax implements IdentificationModel {
    double[] A;
    double[] B;
    double[] C;
    int dA, dB, dC, k;
    double L;


    public IdArmax(int dA, int dB, int dC, int k, double l) {
        this.dA = dA;
        this.dB = dB;
        this.dC = dC;
        this.k = k;
        L = l;
    }

    @Override
    public void execute(IdData iddata) {

    }

    @Override
    public String getResult() {
        return "Finished Armax";
    }

    @Override
    public String getFunctionDescription() {
        return "ARMAX(" + dA + ", " + dB + ", " + dC + ", " + k + ")";
    }
}
