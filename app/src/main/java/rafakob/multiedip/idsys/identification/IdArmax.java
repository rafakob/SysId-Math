package rafakob.multiedip.idsys.identification;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-04-06
 */
public class IdArmax implements IdentificationModel {
    @Override
    public IdData execute(IdData iddata) {
        return null;
    }

    @Override
    public String getResult() {
        return "Finished Armax";
    }

    @Override
    public String getFunctionDescription() {
        return "Armax";
    }
}
