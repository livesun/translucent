package livesun.translucent;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 29028 on 2017/6/16.
 */

public class BaseTranslucentActivity extends AppCompatActivity {
    boolean NavigationBarisTranslucent=false;//导航条是否透明

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //大于等于4.4并且小于5.0----在setContentView之前。
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP
                ){
            //设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if(setNavigationBarisTranslucent()){
                //设置导航条透明
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }

        }
    }
    /**
     * 设置纯色--标题栏和status颜色一致
     */
       protected void  setStatusColor(View titleBar,@ColorRes int resId,boolean isShowUi){
           TranslucentUtils.setStatusColor(titleBar,resId,this,isShowUi);
        }
    /**
     * 设置标题栏和状态栏为一张图片
     */
        protected void  setStatusPicture(View titleBar, @DrawableRes int drawableId,boolean isShowUi){
        TranslucentUtils.setStatusPicture(titleBar,drawableId,this,isShowUi);
        }
    /**
     * 设置导航条和状态栏颜色一致
     */
        protected void setStatusAndNaviagtion(View titleBar, View botoomBar ,@ColorRes int resId){
            TranslucentUtils.setStatusAndNaviagtion(titleBar,botoomBar,resId,this);
        }


    /**
     * 是否设置导航条为透明--钩子方法
     */

    protected boolean setNavigationBarisTranslucent(){
        return NavigationBarisTranslucent;
    }

}
