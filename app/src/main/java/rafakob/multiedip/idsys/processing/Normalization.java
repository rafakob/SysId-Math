package rafakob.multiedip.idsys.processing;

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

        int i;
        DecimalFormat decFormat = new DecimalFormat("#.####");

        double maxAbs = findMaxAbs(array, array.length);

        for(i = 0; i<array.length; i++){
            iddata.getOutput()[i] = array[i] / maxAbs;
        }

        return iddata;
    }


    private double findMaxAbs(double[] in, int length) {
        int i = 0;
        double max = in[0];
        for(i = 1; i<length; i++){

            if( Math.abs( in[i] ) > max ){
                max = Math.abs( in[i] );
            }

        }
        return max;
    }
}
