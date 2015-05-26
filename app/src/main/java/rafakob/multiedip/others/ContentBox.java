package rafakob.multiedip.others;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import rafakob.multiedip.R;

public class ContentBox {
    View mBox;
    GridLayout mGrid;
    Context mContext;
    private final int mGravityDefault = Gravity.LEFT;

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

    /**
        PRIVATE
     */
    private void addGrid(int row, int col, TextView textView, String text, int gravity) {
        textView.setText(text);
        textView.setTextAppearance(mContext, R.style.Base_TextAppearance_AppCompat_Small);
        textView.setTextColor(mContext.getResources().getColor(R.color.text_small_primary));
        textView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(row), GridLayout.spec(col)));
        mGrid.addView(textView);
        ((GridLayout.LayoutParams)textView.getLayoutParams()).setGravity(gravity);
    }

    public void addToGrid(int row, int col, int resStringId){
        addGrid(row, col, new TextView(mContext), mContext.getString(resStringId), mGravityDefault);
    }

    public void addToGrid(int row, int col, String text){
        addGrid(row, col, new TextView(mContext), text, mGravityDefault);
    }

    public void addToGrid(int row, int col, TextView textView, int resStringId) {
        addGrid(row, col, textView, mContext.getString(resStringId), mGravityDefault);
    }

    public void addToGrid(int row, int col, TextView textView, String text) {
        addGrid(row, col, textView, text, mGravityDefault);
    }

    /**
        With gravity
     */
    public void addToGrid(int row, int col, int resStringId, int gravity){
        addGrid(row, col, new TextView(mContext), mContext.getString(resStringId), gravity);
    }

    public void addToGrid(int row, int col, String text, int gravity){
        addGrid(row, col, new TextView(mContext), text, gravity);
    }

    public void addToGrid(int row, int col, TextView textView, int resStringId, int gravity) {
        addGrid(row, col, textView, mContext.getString(resStringId), gravity);
    }

    public void addToGrid(int row, int col, TextView textView, String text, int gravity) {
        addGrid(row, col, textView, text, gravity);
    }



    public void setButtonVisibility(int visibility){
        mBox.findViewById(R.id.box_button).setVisibility(visibility);
    }

    public void cleatGrid(){
        mGrid.removeAllViews();
    }
}
