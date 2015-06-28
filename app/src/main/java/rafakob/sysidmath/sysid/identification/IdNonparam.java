package rafakob.sysidmath.sysid.identification;

import rafakob.sysidmath.sysid.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-06-06
 */
public class IdNonparam implements IdentificationModel {

    @Override
    public void execute(IdData iddata) {

    }

    @Override
    public String getFunctionDescription() {
        return "Nonparametric model";
    }

    @Override
    public String getResult() {
        return "Nonparametric model are shown on plots";
    }
}
