package rafakob.sysidmath.sysid.processing;

import rafakob.sysidmath.sysid.IdData;
import rafakob.sysidmath.sysid.MathDbl;


public class FrequencyFilter implements DataProcessingInterface {
    @Override
    public IdData execute(IdData iddata) {
        double[] y = iddata.getOutput().clone();
        double[] u = MathDbl.getFilledVector(y.length, 0);

        Fft.transform(y, u);

        //<<< Failed to pull data from GitHub server. Resolve conflicts!! todo

        return iddata;
    }git commit

    @Override
    public String getFunctionDescription() {
        return "FrequencyFilter";
    }
}
