package rafakob.multiedip.idsys.processing;

import com.google.common.math.DoubleMath;

import org.apache.commons.math3.util.DoubleArray;

import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MatrixUtils;

/**
 * Created by Rafal on 2015-03-31.
 */
public class Scaling implements DataProcessingInterface {
    private double multIn = 1;
    private double multOut = 1;

    public Scaling(double multIn, double multOut) {
        this.multIn = multIn;
        this.multOut = multOut;
    }

    @Override
    public IdData execute(IdData iddata) {
        if(iddata.isSiso()) {
            iddata.setInput(MatrixUtils.multiplyScalar(iddata.getInput().clone(), multIn));
            iddata.setOutput(MatrixUtils.multiplyScalar(iddata.getOutput().clone(), multOut));
        }
        else{
            iddata.setOutput(MatrixUtils.multiplyScalar(iddata.getOutput().clone(), multOut));
        }

        return iddata;

    }

    @Override
    public String getFunctionDescription() {

        return "Scaling: input " + multIn + " ; output " + multOut;
    }
}
