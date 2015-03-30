package rafakob.multiedip;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Map;

import rafakob.multiedip.idsys.processing.DataProcessingFunction;
import rafakob.multiedip.idsys.processing.Normalization;


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

    public ArrayList<DataProcessingFunction> getPreprocesingConfig() {
        ArrayList methodList = new ArrayList<DataProcessingFunction>();

        Map<String, ?> all = PreferenceManager.getDefaultSharedPreferences(mContext).getAll();

        for (String key : all.keySet()) {
            if (key.startsWith("pre_flag")) { // get method flags
                if (getBoolean(key, false)) { // check if true
                    System.out.println(key);
                    switch (key){
                        case "pre_flag_datarange":
                            methodList.add(new Normalization()); break;
                        case "pre_flag_filter_freq":
                            methodList.add(new Normalization()); break;
                        case "pre_flag_normalization":
                            methodList.add(new Normalization()); break;
                        case "pre_flag_scaling":
                            methodList.add(new Normalization()); break;
                        case "pre_flag_dcremoval":
                            methodList.add(new Normalization()); break;
                        case "pre_flag_polyremoval":
                            methodList.add(new Normalization()); break;

                    }
                }
            }
        }

//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
//        SharedPreferences.Editor editor = sharedPrefs.edit();
//        editor.clear();
//        editor.commit();

        return methodList;
    }

}
