package rafakob.multiedip.idsys.processing;

import com.rits.cloning.Cloner;
import java.util.List;

import rafakob.multiedip.idsys.IdData;


public class DataProcessing {
    private IdData mIddataProcessed;
    private Cloner mCloner;

    public DataProcessing() {
        mCloner = new Cloner();
    }


    public IdData process(IdData iddata, List<DataProcessingFunction> listFun) {
        mIddataProcessed = mCloner.deepClone(iddata);
        for (DataProcessingFunction el : listFun) {
            el.execute(mIddataProcessed);
        }
        return mIddataProcessed;
    }
}
