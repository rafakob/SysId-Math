package rafakob.multiedip.prefs;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;

import rafakob.multiedip.idsys.identification.IdArmax;
import rafakob.multiedip.idsys.identification.IdArx;
import rafakob.multiedip.idsys.identification.IdentificationModel;
import rafakob.multiedip.idsys.processing.DataProcessingInterface;
import rafakob.multiedip.idsys.processing.DataRange;
import rafakob.multiedip.idsys.processing.DcOffsetRemoval;
import rafakob.multiedip.idsys.processing.FrequencyFilter;
import rafakob.multiedip.idsys.processing.Normalization;
import rafakob.multiedip.idsys.processing.PolynomialTrendRemoval;
import rafakob.multiedip.idsys.processing.Scaling;


public class PrefManager {
    private Context mContext;

    public PrefManager() {
    }

    public PrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public String getString(String key, String defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getString(key, "test");
    }


    public boolean getBoolean(String key, boolean defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(mContext).getInt(key, defValue);
    }

    public ArrayList<DataProcessingInterface> getPreprocesingConfig() {
        ArrayList methodList = new ArrayList<DataProcessingInterface>();

        Map<String, ?> all = PreferenceManager.getDefaultSharedPreferences(mContext).getAll();


        if (getBoolean("pre_flag_datarange", false)) methodList.add(new DataRange());
        if (getBoolean("pre_flag_filter_freq", false)) methodList.add(new FrequencyFilter());
        if (getBoolean("pre_flag_scaling", false)) methodList.add(new Scaling());
        if (getBoolean("pre_flag_dcremoval", false)) methodList.add(new DcOffsetRemoval());
        if (getBoolean("pre_flag_polyremoval", false)) methodList.add(new PolynomialTrendRemoval());
        if (getBoolean("pre_flag_normalization", false)) methodList.add(new Normalization());



//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.clear();
//        editor.commit();

        return methodList;
    }

    public IdentificationModel getIdentificationConfig(String modelType){
        if (getBoolean("id_flag_parametric", false) && modelType.equals("siso")){
            if (getString("id_par_siso_model","").equals("arx")) return new IdArx(2,1,1,0.99);
            if (getString("id_par_siso_model","").equals("armax")) return new IdArmax();
        }


        if (getBoolean("id_flag_parametric", false) && modelType.equals("timeseries")){

        }

        return null;
    }

}
