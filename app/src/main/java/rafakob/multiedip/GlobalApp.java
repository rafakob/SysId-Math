package rafakob.multiedip;

import android.app.Application;

import rafakob.multiedip.idsys.IdData;


public class GlobalApp extends Application {
    private IdData dataSource;
    private IdData dataProcessed;

    public GlobalApp() {
        dataSource = new IdData();
        dataProcessed = new IdData();
    }

    public IdData getDataSource() {
        return dataSource;
    }

    public void setDataSource(IdData dataSource) {
        this.dataSource = dataSource;
    }

    public IdData getDataProcessed() {
        return dataProcessed;
    }

    public void setDataProcessed(IdData dataProcessed) {
        this.dataProcessed = dataProcessed;
    }
}
