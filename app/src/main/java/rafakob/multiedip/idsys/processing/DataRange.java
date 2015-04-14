package rafakob.multiedip.idsys.processing;

import rafakob.multiedip.idsys.IdData;
import rafakob.multiedip.idsys.MatrixUtils;

/**
 * Created by Rafal on 2015-03-31.
 */
public class DataRange implements DataProcessingInterface {
    int start;
    int end;

    public DataRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public IdData execute(IdData iddata) {
        iddata.setInput(MatrixUtils.extract(start-1,end-1,iddata.getInput()));
        iddata.setOutput(MatrixUtils.extract(start-1,end-1,iddata.getOutput()));
        iddata.setLength(end-start+1);
        return iddata;
    }

    @Override
    public String getFunctionDescription() {
        return "Data range: " + start + "-" + end;
    }
}
