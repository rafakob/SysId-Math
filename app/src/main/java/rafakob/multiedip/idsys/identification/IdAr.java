package rafakob.multiedip.idsys.identification;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-04-14
 */
public class IdAr implements IdentificationModel {

    @Override
    public void execute(IdData iddata) {

    }

    @Override
    public String getResult() {
        return "Finished AR";
    }

    @Override
    public String getFunctionDescription() {
        return "AR";
    }
}
