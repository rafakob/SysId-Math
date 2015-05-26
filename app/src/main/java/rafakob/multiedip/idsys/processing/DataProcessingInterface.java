package rafakob.multiedip.idsys.processing;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-26
 */
public interface DataProcessingInterface {

    IdData execute(IdData iddata);
    String getFunctionDescription();
}
