package rafakob.sysidmath.prefs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import rafakob.sysidmath.R;


public class ModelStructurePreference extends DialogPreference {
    private String mCurrentValue;
    private EditText mEditTextParamK;
    private EditText mEditTextParamDa;
    private EditText mEditTextParamDb;
    private EditText mEditTextParamDc;



    public ModelStructurePreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DoubleNumberPicker);

        setDialogLayoutResource(R.layout.pref_model_structure);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
        a.recycle();
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mEditTextParamDa = (EditText) view.findViewById(R.id.param_da);
        mEditTextParamDb = (EditText) view.findViewById(R.id.param_db);
        mEditTextParamDc = (EditText) view.findViewById(R.id.param_dc);
        mEditTextParamK = (EditText) view.findViewById(R.id.param_k);
    }

    @Override
    protected void onPrepareDialogBuilder(Builder builder) {
        super.onPrepareDialogBuilder(builder);

        String[] values = mCurrentValue.split(",", -1);

        //TODO: dodać wyszarzanie na podstawie wybranego modelu
        mEditTextParamDa.setText(values[0]);
        mEditTextParamDb.setText(values[1]);
        mEditTextParamDc.setText(values[2]);
        mEditTextParamK.setText(values[3]);


    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            // Restore existing state
            mCurrentValue = this.getPersistedString("1,1,1,1");
        } else {
            // Set default state from the XML attribute
            mCurrentValue = (String) defaultValue;
            persistString(mCurrentValue);
        }

        this.updateSummary();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }


    @Override
    protected void onDialogClosed(final boolean positiveResult) {
        if (positiveResult && this.shouldPersist()) {

            mCurrentValue = mEditTextParamDa.getText().toString() +
                    "," + mEditTextParamDb.getText().toString() +
                    "," + mEditTextParamDc.getText().toString() +
                    "," + mEditTextParamK.getText().toString();

            this.persistString(mCurrentValue);
            this.updateSummary();
        }
    }

    private void updateSummary() {
//        super.setSummary(super.getTitle() + " " + mCurrentValue);
        super.setSummary("\t" + mCurrentValue);
    }
}
