package rafakob.sysidmath.sysid.processing;

import rafakob.sysidmath.sysid.IdData;
import rafakob.sysidmath.sysid.MathDbl;


public class DataRange implements DataProcessingInterface {
    int start;
    int end;

    public DataRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public IdData execute(IdData iddata) {
        if(iddata.isSiso()) {
            iddata.setInput(MathDbl.extract(start - 1, end - 1, iddata.getInput()));
            iddata.setOutput(MathDbl.extract(start - 1, end - 1, iddata.getOutput()));
            iddata.setSamples(MathDbl.extract(start - 1, end - 1, iddata.getSamples()));
        }
        else{
            iddata.setOutput(MathDbl.extract(start - 1, end - 1, iddata.getOutput()));
            iddata.setSamples(MathDbl.extract(start - 1, end - 1, iddata.getSamples()));
        }
        iddata.setLength(end-start+1);
        return iddata;
    }

    @Override
    public String getFunctionDescription() {
        return "Data range: " + start + "-" + end;
    }
}
