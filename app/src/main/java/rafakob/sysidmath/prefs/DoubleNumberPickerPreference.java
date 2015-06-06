package rafakob.sysidmath.prefs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import rafakob.sysidmath.R;


public class DoubleNumberPickerPreference extends DialogPreference {
    private String mCurrentValue;
    private EditText mEditText1;
    private EditText mEditText2;

    private TextView mLabel1;
    private TextView mLabel2;

    private String mStrLabel1;
    private String mStrLabel2;

    public DoubleNumberPickerPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DoubleNumberPicker);
        mStrLabel1 = a.getString(R.styleable.DoubleNumberPicker_labelFirst);
        mStrLabel2 = a.getString(R.styleable.DoubleNumberPicker_labelSecond);

        setDialogLayoutResource(R.layout.pref_double_number_picker);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
        a.recycle();
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mEditText1 = (EditText) view.findViewById(R.id.dnum_number1);
        mEditText2 = (EditText) view.findViewById(R.id.dnum_number2);

        mLabel1 = (TextView) view.findViewById(R.id.dnum_picker_label1);
        mLabel2 = (TextView) view.findViewById(R.id.dnum_picker_label2);
    }

    @Override
    protected void onPrepareDialogBuilder(Builder builder) {
        super.onPrepareDialogBuilder(builder);

        //TODO: dodać zabezpieczenie przed nullem!
        mEditText1.setText(mCurrentValue.substring(0, mCurrentValue.lastIndexOf(";")));
        mEditText2.setText(mCurrentValue.substring(mCurrentValue.lastIndexOf(";")+1));

        mLabel1.setText(mStrLabel1 + ":");
        mLabel2.setText(mStrLabel2 + ":");
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            // Restore existing state
            mCurrentValue = this.getPersistedString("0;");
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

            mCurrentValue = mEditText1.getText().toString() + ";" + mEditText2.getText().toString();
            this.persistString(mCurrentValue);
            this.updateSummary();
        }
    }

    private void updateSummary() {
//        super.setSummary(super.getTitle() + " " + mCurrentValue);
        super.setSummary("\t" + mCurrentValue);
    }
}
