package rafakob.multiedip.idsys.processing;

import rafakob.multiedip.idsys.DataStatistics;
import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public class Normalization implements DataProcessingInterface {

    public Normalization() {
    }

    @Override
    public IdData execute(IdData iddata) {
        if(iddata.isSiso()) {
            iddata.setInput(normalize(iddata.getInput().clone()));
            iddata.setOutput(normalize(iddata.getOutput().clone()));
        } else{
            iddata.setOutput(normalize(iddata.getOutput().clone()));
        }
        iddata.setLength(50-5+1);
        return iddata;
    }

    @Override
    public String getFunctionDescription() {
        return "Normalization";
    }

    private double[] normalize(double[] v){
        DataStatistics ds = new DataStatistics();
        ds.calculate(v);

        for (int i = 0; i < v.length; i++) {
            v[i] = (v[i] - ds.getMean())/ds.getStddev();
        }

        return v;
    }

}
