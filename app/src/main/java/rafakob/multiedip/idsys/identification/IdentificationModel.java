package rafakob.multiedip.idsys.identification;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public interface IdentificationModel {
    public IdData execute(IdData iddata);
    public String getFunctionDescription();
    public String getResult();
}
