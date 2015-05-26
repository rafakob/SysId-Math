package rafakob.multiedip.idsys.processing;

import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MathDbl;

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
            iddata.setInput(MathDbl.multiplyScalar(iddata.getInput().clone(), multIn));
            iddata.setOutput(MathDbl.multiplyScalar(iddata.getOutput().clone(), multOut));
        }
        else{
            iddata.setOutput(MathDbl.multiplyScalar(iddata.getOutput().clone(), multOut));
        }

        return iddata;

    }

    @Override
    public String getFunctionDescription() {

        return "Scaling: input " + multIn + " ; output " + multOut;
    }
}
