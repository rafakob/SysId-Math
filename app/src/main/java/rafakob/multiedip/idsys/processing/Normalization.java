package rafakob.multiedip.idsys.processing;

import com.google.common.primitives.Doubles;

import java.text.DecimalFormat;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public class Normalization implements DataProcessingFunction {

    public Normalization() {
    }

    @Override
    public IdData execute(IdData iddata) {
        double[] array = iddata.getOutput();
        double maxAbs = findMaxAbs(array);

        for(int i = 0; i < array.length; i++){
//            iddata.getOutput()[i] = array[i] / maxAbs; TODO: wrócic
            iddata.getOutput()[i] = array[i] *2;
        }
        return iddata;
    }

    @Override
    public String getFunctionDescription() {
        return "Normalization";
    }

    private double findMaxAbs(double[] array) {
        double min = Doubles.min(array);
        double max = Doubles.max(array);
        return Math.abs(min) > max ? Math.abs(min) : max;
    }
}
