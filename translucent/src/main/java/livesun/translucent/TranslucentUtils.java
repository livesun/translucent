package livesun.translucent;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 29028 on 2017/6/16.
 */

public class TranslucentUtils {


    /**
     *
     * @param titleBar 标题栏view
     * @param resId 状态栏颜色的引用
     * @param activity
     * @param isShowUi 是否开启显示或关闭导航条和状态栏
     */
    public static void  setStatusColor(View titleBar, @ColorRes int resId, Activity activity,boolean isShowUi){
        int color;
        if(resId>0){
            color = activity.getResources().getColor(resId);
        }else {
            color= Color.parseColor("#FF4081");
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
                &&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP
                ){
            if(titleBar!=null){
                ViewGroup.LayoutParams params = titleBar.getLayoutParams();
                int statusHeight = getStatusHeight(activity);
                params.height+= statusHeight;
                titleBar.setLayoutParams(params);
                titleBar.setPadding(titleBar.getPaddingLeft(),
                        titleBar.getPaddingRight(),
                        titleBar.getPaddingTop()+ statusHeight,
                        titleBar.getPaddingBottom());
                titleBar.setBackgroundColor(color);
            }
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().setStatusBarColor(color);
        }
        if(isShowUi){
            toggleHideyBar(activity);
        }
    }

    /**
     *
     * @param titleBar 标题栏view
     * @param drawableId 状态栏图片的引用
     * @param activity
     * @param isShowUi 是否开启显示或关闭导航条和状态栏
     */
    public static void setStatusPicture(View titleBar, @DrawableRes int drawableId,Activity activity,boolean isShowUi){
        Drawable drawableBg;
        if(drawableId>0){
            drawableBg = activity.getResources().getDrawable(drawableId);
        }else {
            drawableBg= activity.getResources().getDrawable(R.drawable.ic_launcher);
        }
        int statusHeight = getStatusHeight(activity);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
                &&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP
                ){
            if(titleBar!=null){
                ViewGroup.LayoutParams params = titleBar.getLayoutParams();
                params.height+= statusHeight;
                titleBar.setLayoutParams(params);
                titleBar.setPadding(titleBar.getPaddingLeft(),
                        titleBar.getPaddingRight(),
                        titleBar.getPaddingTop()+ statusHeight,
                        titleBar.getPaddingBottom());
                titleBar.setBackground(drawableBg);
            }

        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);

            ViewGroup.LayoutParams params = titleBar.getLayoutParams();
            params.height+= statusHeight;
            titleBar.setLayoutParams(params);
            titleBar.setPadding(titleBar.getPaddingLeft(),
                    titleBar.getPaddingRight(),
                    titleBar.getPaddingTop()+ statusHeight,
                    titleBar.getPaddingBottom()
            );
            titleBar.setBackground(drawableBg);
        }
        if(isShowUi){
            toggleHideyBar(activity);
        }
    }

    /**
     *
     * @param titleBar 标题
     * @param botoomBar 导航栏view 4.4版本只能设置为透明，如果想要改变颜色，只能在xml布局中在底部写一个宽高和导航栏一致的view。
     * @param resId
     * @param activity
     */
    public static void setStatusAndNaviagtion(View titleBar, View botoomBar,  @ColorRes int resId ,Activity activity){
        int color;
        if(resId>0){
            color = activity.getResources().getColor(resId);
        }else {
            color= Color.parseColor("#FF4081");
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
                &&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP
                ){
            if(titleBar!=null){
                ViewGroup.LayoutParams params = titleBar.getLayoutParams();
                int statusHeight = getStatusHeight(activity);
                params.height+= statusHeight;
                titleBar.setLayoutParams(params);
                titleBar.setPadding(titleBar.getPaddingLeft(),
                        titleBar.getPaddingRight(),
                        titleBar.getPaddingTop()+ statusHeight,
                        titleBar.getPaddingBottom());
                titleBar.setBackgroundColor(color);
            }
            if(botoomBar!=null){
                ViewGroup.LayoutParams bootomParams = botoomBar.getLayoutParams();

                    if(isBootomBarShow(activity)){
                        bootomParams.height+=getNavigationHeight(activity);
                        botoomBar.setBackgroundColor(color);
                        botoomBar.setLayoutParams(bootomParams);
                    }else{
                        bootomParams.height=0;
                        botoomBar.setLayoutParams(bootomParams);
                    }
            }

        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            activity.getWindow().setStatusBarColor(color);
            activity.getWindow().setNavigationBarColor(color);
            toggleHideyBar(activity);
        }

    }

    /**
     * 获取状态栏的高度
     * @param activity
     * @return
     */
    private static int getStatusHeight(Activity activity){
        int stastusHeight=-1;
        int statusId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(statusId>0){
            stastusHeight = activity.getResources().getDimensionPixelSize(statusId);
        }

        return stastusHeight==-1?dp2px(24,activity):stastusHeight;
    }

    /**
     * 获取底部导航栏的高度
     * @param activity
     * @return
     */
    private static int getNavigationHeight(Activity activity){
        int navigationHeight=-1;
        int navigationId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if(navigationId>0){
            navigationHeight=activity.getResources().getDimensionPixelSize(navigationId);
        }
        return navigationHeight==-1?dp2px(48,activity):navigationHeight;
    }

    public static boolean isVirtualKeyShow(Activity activity){
            int resourceId =  activity.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
            if (resourceId > 0) {
                boolean b = activity.getResources().getBoolean(resourceId);
                return b;
            }

        return false;
    }
    //判断是否打开底部导航条 5.1以上没有导航栏也可以设置颜色。
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public  static   boolean isBootomBarShow(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics=new DisplayMetrics();
        //获取整个屏幕的高度
        display.getRealMetrics(outMetrics);
        int realHeight = outMetrics.heightPixels;
        int realWidth = outMetrics.widthPixels;
        //获取内容展示的高度
        outMetrics=new DisplayMetrics();
        display.getMetrics(outMetrics);
        int contentHeight = outMetrics.heightPixels;
        int contentWidth = outMetrics.widthPixels;

        int h = realHeight - contentHeight;
        int w = realWidth - contentWidth;

        return h>0||w>0;//竖屏情况和横屏情况。
    }

    /**
     * dip 转 px
     * @param dp
     * @param activity
     * @return
     */
    private static int dp2px(int dp,Activity activity) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,activity.getResources().getDisplayMetrics());
    }


    /**
     * 开关
     * @param activity
     */
    public static void toggleHideyBar(Activity activity) {

        int newUiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();

        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
