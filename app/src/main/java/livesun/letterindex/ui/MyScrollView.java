package livesun.letterindex.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by 29028 on 2017/6/14.
 */

public class MyScrollView extends ScrollView {

    private OnScrollChangedListener listener;
    private int heightPixels;

    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕高度
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        heightPixels = metrics.heightPixels;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (listener!=null) {
            //滑动的高度小于等于
            int scrollY = getScrollY();
            if(scrollY<=heightPixels/3f){
                listener.change(1-scrollY/(heightPixels/3f));
            }
        }
    }


    public interface OnScrollChangedListener{
        /*
        alpha 1-0;
         */
        void change(float alpha);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener){

        this.listener = listener;
    }
}
