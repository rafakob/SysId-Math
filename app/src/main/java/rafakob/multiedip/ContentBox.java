package rafakob.multiedip;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class ContentBox {
    View mBox;
    GridLayout mGrid;
    Context mContext;

    public ContentBox(View view, int layoutResId) {
        mBox = view.findViewById(layoutResId);
        mGrid = (GridLayout) mBox.findViewById(R.id.box_grid);
        mContext = view.getContext();

    }

    public void setTitle(String text) {
        ((TextView) mBox.findViewById(R.id.box_title)).setText(text);
    }

    public void setTitle(int resId) {
        ((TextView) mBox.findViewById(R.id.box_title)).setText(mContext.getString(resId));
    }

    public void setButtonText(String text) {
        ((Button) mBox.findViewById(R.id.box_button)).setText(text);
    }

    public void setButtonText(int resId) {
        ((Button) mBox.findViewById(R.id.box_button)).setText(mContext.getString(resId));
    }

    public void setOnClickListener(View.OnClickListener l) {
        mBox.findViewById(R.id.box_button).setOnClickListener(l);
    }

    public void addToGrid(TextView textView, String text, int row, int col) {
        textView.setText(text);
        textView.setTextAppearance(mContext, R.style.Base_TextAppearance_AppCompat_Small);
        textView.setTextColor(mContext.getResources().getColor(R.color.text_small_primary));
        textView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col)));
        mGrid.addView(textView);
    }

    public void addToGrid(TextView textView, int resId, int row, int col) {
        textView.setText(mContext.getString(resId));
        textView.setTextAppearance(mContext, R.style.Base_TextAppearance_AppCompat_Small);
        textView.setTextColor(mContext.getResources().getColor(R.color.text_small_primary));
        textView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col)));
        mGrid.addView(textView);
    }

    public void cleatGrid(){
        mGrid.removeAllViews();
    }
}
