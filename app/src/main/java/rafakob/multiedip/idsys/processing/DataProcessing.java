package rafakob.multiedip.idsys.processing;

import com.rits.cloning.Cloner;
import java.util.List;

import rafakob.multiedip.idsys.IdData;


public class DataProcessing {
    private IdData dataProcessed;
    private Cloner mCloner;

    public DataProcessing() {
        mCloner = new Cloner();
    }


    public IdData process(IdData iddata, List<DataProcessingInterface> listFun) {
        dataProcessed = mCloner.deepClone(iddata);
        for (DataProcessingInterface el : listFun) {
            el.execute(dataProcessed);
        }
        return dataProcessed;
    }

    public IdData getDataProcessed() {
        return dataProcessed;
    }
}
