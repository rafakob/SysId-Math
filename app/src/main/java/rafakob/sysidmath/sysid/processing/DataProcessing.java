package rafakob.sysidmath.sysid.processing;

import java.util.List;

import rafakob.sysidmath.sysid.IdData;


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
