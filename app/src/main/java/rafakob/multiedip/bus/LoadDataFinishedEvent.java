package rafakob.multiedip.bus;

import rafakob.multiedip.idsys.IdData;

/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-22
 */
public class LoadDataFinishedEvent {
    public final IdData iddata;

    public LoadDataFinishedEvent(IdData iddata) {
        this.iddata = iddata;
    }
}
