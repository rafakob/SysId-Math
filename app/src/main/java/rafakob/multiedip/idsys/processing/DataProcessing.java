package rafakob.multiedip.idsys.processing;

import java.util.List;

import rafakob.multiedip.idsys.IdData;


public class DataProcessing {

    public DataProcessing() {

    }


    public IdData process(IdData iddata, List<DataProcessingInterface> listFun) {
        for (DataProcessingInterface function : listFun) {
            function.execute(iddata);
        }
        return iddata;
    }

}
