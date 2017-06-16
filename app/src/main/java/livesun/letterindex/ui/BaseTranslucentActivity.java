package livesun.letterindex.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by 29028 on 2017/6/15.
 */

public class BaseTranslucentActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断当前版本如果大于4.4 并且小于5.0
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
                &&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP
                ){
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     *
     * @param titleBar 顶布局
     * @param bootomBar 导航条
     * @param primaryColor 背景颜色
     * @param resId 图片
     */
    public void setOrChangeTranslucentColor(View titleBar,@Nullable View bootomBar,int primaryColor,int resId){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
                &&Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP
                ){
            //顶部
                if(titleBar!=null){
                    ViewGroup.LayoutParams params = titleBar.getLayoutParams();
                    params.height+=getStatusBarHeight();
                    titleBar.setLayoutParams(params);
                    titleBar.setPadding(titleBar.getPaddingLeft(),
                                        titleBar.getPaddingRight(),
                                        titleBar.getPaddingTop()+getStatusBarHeight(),
                                        titleBar.getPaddingBottom()
                                        );
                    if(primaryColor!=Color.TRANSPARENT){
                        titleBar.setBackgroundColor(primaryColor);
                    }else{
                        titleBar.setBackground(getResources().getDrawable(resId));
                    }


                }
            //底部
                if(bootomBar!=null){
                    ViewGroup.LayoutParams bootomParams = bootomBar.getLayoutParams();
                    if(isBootomBarShow()){
                        bootomParams.height+=getNavigationBarHeight();
                        bootomBar.setBackgroundColor(primaryColor);
                        bootomBar.setLayoutParams(bootomParams);
                    }else{
                        bootomParams.height=0;
                        bootomBar.setLayoutParams(bootomParams);
                    }
                }
            toggleHideyBar();
            //5.0
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            //如果颜色为透明，则用图片
            if(primaryColor== Color.TRANSPARENT){
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);

                ViewGroup.LayoutParams params = titleBar.getLayoutParams();
                params.height+=getStatusBarHeight();
                titleBar.setLayoutParams(params);
                titleBar.setPadding(titleBar.getPaddingLeft(),
                        titleBar.getPaddingRight(),
                        titleBar.getPaddingTop()+getStatusBarHeight(),
                        titleBar.getPaddingBottom()
                );
            }
            if(bootomBar!=null){
                getWindow().setNavigationBarColor(primaryColor);
            }
            getWindow().setStatusBarColor(primaryColor);
            toggleHideyBar();
        }
    }

    //获取顶部状态栏的高度
    public int getStatusBarHeight(){
        int statusBarId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight=-1;
        if(statusBarId>0){
             statusBarHeight = getResources().getDimensionPixelSize(statusBarId);
        }
        //代码默认为24dp
        return statusBarHeight==-1?dp2px(24):statusBarHeight;
    }

    //获取底部的导航条的高度
    public int getNavigationBarHeight(){
        int navigationId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationBaHeight=-1;
        if(navigationId>0){
            navigationBaHeight = getResources().getDimensionPixelSize(navigationId);
        }
        //代码默认为48dp
        return navigationBaHeight==-1?dp2px(48):navigationBaHeight;
    }

    //判断是否打开底部导航条 5.1以上没有导航栏也可以设置颜色。
    public    boolean isBootomBarShow(){
        Display display = getWindowManager().getDefaultDisplay();
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


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    public void toggleHideyBar() {

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {

        } else {
        }

        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

}
