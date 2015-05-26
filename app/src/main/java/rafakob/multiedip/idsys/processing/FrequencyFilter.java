package rafakob.multiedip.idsys.processing;

import rafakob.multiedip.idsys.IdData;


public class FrequencyFilter implements DataProcessingInterface {
    @Override
    public IdData execute(IdData iddata) {
//        double[] y = iddata.getOutput().clone();
//        double[] u = MatrixUtils.getFilledVector(y.length,0);
//
//        Fft.transform(y, u);
//
//        System.out.println(MatrixUtils.getString(iddata.getOutput()));
//        System.out.println(MatrixUtils.getString(u));
//
//

        return iddata;
    }

    @Override
    public String getFunctionDescription() {
        return "FrequencyFilter";
    }
}
