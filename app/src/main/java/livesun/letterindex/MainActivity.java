package livesun.letterindex;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;

import livesun.translucent.BaseTranslucentActivity;


public class MainActivity extends BaseTranslucentActivity{




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        LinearLayout title= (LinearLayout) findViewById(R.id.title);
        View nivagtion = findViewById(R.id.nivagtion);
//        setStatusColor(toolbar,R.color.colorPrimary);
        setStatusPicture(title,R.drawable.demobg,false);
//        setStatusAndNaviagtion(title,nivagtion,R.color.colorPrimary);
    }

    /**
     * 是否设置导航条为透明，默认返回false。
     * @return
     */
    @Override
    protected boolean setNavigationBarisTranslucent() {
        return true;
    }

}
