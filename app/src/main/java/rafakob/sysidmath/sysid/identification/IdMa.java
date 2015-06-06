package rafakob.sysidmath.sysid.identification;

import rafakob.sysidmath.sysid.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-04-14
 */
public class IdMa implements IdentificationModel {

    @Override
    public void execute(IdData iddata) {

    }

    @Override
    public String getResult() {
        return "Finished Armax";
    }

    @Override
    public String getFunctionDescription() {
        return "MA";
    }
}
