package rafakob.multiedip.prefs;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Author: Rafal Kobylko
 * Created on: 2015-03-23
 */
public class ListPreference extends android.preference.ListPreference{

    public ListPreference(final Context context) {
        this(context, null);
    }

    public ListPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        setSummary(getSummary());
    }

    @Override
    public CharSequence getSummary() {

        final CharSequence entry = getEntry();
        final CharSequence summary = super.getSummary();
        if (summary == null || entry == null) {
            return null;
        } else {
            return String.format(summary.toString(), entry);
        }
    }
}
