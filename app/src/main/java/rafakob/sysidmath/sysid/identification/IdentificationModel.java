package rafakob.sysidmath.sysid.identification;

import rafakob.sysidmath.sysid.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public interface IdentificationModel {
    void execute(IdData iddata);
    String getFunctionDescription();
    String getResult();
}
