package rafakob.sysidmath.bus;

import rafakob.sysidmath.sysid.IdData;

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
