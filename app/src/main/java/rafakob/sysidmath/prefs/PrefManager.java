package rafakob.sysidmath.prefs;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;

import rafakob.sysidmath.GlobalApp;
import rafakob.sysidmath.sysid.IdData;
import rafakob.sysidmath.sysid.identification.IdAr;
import rafakob.sysidmath.sysid.identification.IdArma;
import rafakob.sysidmath.sysid.identification.IdArmax;
import rafakob.sysidmath.sysid.identification.IdArx;
import rafakob.sysidmath.sysid.identification.IdMa;
import rafakob.sysidmath.sysid.identification.IdNonparam;
import rafakob.sysidmath.sysid.identification.IdentificationModel;
import rafakob.sysidmath.sysid.processing.DataProcessingInterface;
import rafakob.sysidmath.sysid.processing.DataRange;
import rafakob.sysidmath.sysid.processing.AverageRemoval;
import rafakob.sysidmath.sysid.processing.FrequencyFilter;
import rafakob.sysidmath.sysid.processing.Normalization;
import rafakob.sysidmath.sysid.processing.PolynomialTrendRemoval;
import rafakob.sysidmath.sysid.processing.Scaling;


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
        ArrayList methodList = new ArrayList<>();

        Map<String, ?> all = PreferenceManager.getDefaultSharedPreferences(mContext).getAll();


        if (getBoolean("pre_flag_datarange", false)) {
            String[] params = getString("pre_datarange_range","").split(";");
            methodList.add(new DataRange(Integer.parseInt(params[0]),Integer.parseInt(params[1])));
        }
        if (getBoolean("pre_flag_filter_freq", false)) methodList.add(new FrequencyFilter());
        if (getBoolean("pre_flag_scaling", false)) {
            String[] params = getString("pre_scaling_values","").split(";");
            methodList.add(new Scaling(Double.parseDouble(params[0]),Double.parseDouble(params[1])));
        }

        if (getBoolean("pre_flag_avgremoval", false)) methodList.add(new AverageRemoval());
        if (getBoolean("pre_flag_polyremoval", false)) methodList.add(new PolynomialTrendRemoval(Integer.parseInt(getString("pre_polyremoval_order",""))));
        if (getBoolean("pre_flag_normalization", false)) methodList.add(new Normalization());


//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.clear();
//        editor.commit();

        return methodList;
    }

    public IdentificationModel getIdentificationConfig(String modelType){
        String[] params = getString("id_par_structure","").split(",");

        if (getBoolean("id_flag_parametric", false) && modelType.equals("siso")){
            if (getString("id_par_siso_model","").equals("arx"))
                return new IdArx(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[3]), 0.99);

            if (getString("id_par_siso_model","").equals("armax"))
                return new IdArmax(Integer.parseInt(params[0]), Integer.parseInt(params[1]), Integer.parseInt(params[2]), Integer.parseInt(params[3]), 0.99);
        }


        if (getBoolean("id_flag_parametric", false) && modelType.equals("timeseries")){
            if (getString("id_par_timeseries_model","").equals("ar"))
                return new IdAr(Integer.parseInt(params[0]));
            if (getString("id_par_timeseries_model","").equals("ma"))
                return new IdMa();
            if (getString("id_par_timeseries_model","").equals("arma"))
                return new IdArma();
            // TODO: add forgetting factor and sample time
        }

        if (getBoolean("id_flag_nonparametric", false) && modelType.equals("siso")){
            return new IdNonparam();
        }

        if (getBoolean("id_flag_nonparametric", false) && modelType.equals("timeseries")){
            return new IdNonparam();
        }

        return null;
    }

    public double getSampleTime(){
        return Double.parseDouble(getString("id_par_sample_time",""));
    }

}
