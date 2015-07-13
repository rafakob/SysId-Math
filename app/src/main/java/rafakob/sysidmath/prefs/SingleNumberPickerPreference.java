package rafakob.sysidmath.prefs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import rafakob.sysidmath.R;


public class SingleNumberPickerPreference extends DialogPreference {
    private String mCurrentValue;
    private EditText mEditText1;


    private TextView mLabel1;


    private String mStrLabel1;


    private String mNumeric;

    public SingleNumberPickerPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SingleNumberPicker);
        mStrLabel1 = a.getString(R.styleable.SingleNumberPicker_label);

        mNumeric = a.getString(R.styleable.SingleNumberPicker_numericInput);

        setDialogLayoutResource(R.layout.pref_single_number_picker);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
        a.recycle();
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mEditText1 = (EditText) view.findViewById(R.id.dnum_number1);

        if(mNumeric.equals("decimal")){
            mEditText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        mLabel1 = (TextView) view.findViewById(R.id.dnum_picker_label1);
    }

    @Override
    protected void onPrepareDialogBuilder(Builder builder) {
        super.onPrepareDialogBuilder(builder);

        //TODO: dodać zabezpieczenie przed nullem!
        mEditText1.setText(mCurrentValue);

        mLabel1.setText(mStrLabel1 + ":");
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            // Restore existing state
//            mCurrentValue = this.getPersistedString("0;");
            mCurrentValue = this.getPersistedString("1");
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

            mCurrentValue = mEditText1.getText().toString();
            this.persistString(mCurrentValue);
            this.updateSummary();
        }
    }

    private void updateSummary() {
//        super.setSummary(super.getTitle() + " " + mCurrentValue);
        super.setSummary("\t" + mCurrentValue);
    }
}
