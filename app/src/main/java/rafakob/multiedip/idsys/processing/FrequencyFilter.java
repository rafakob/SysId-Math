package rafakob.multiedip.idsys.processing;

import java.util.Arrays;

import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MatrixUtils;

/**
 * Created by Rafal on 2015-03-31.
 */
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
