package zhao.siqi.com.longpictureshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 第三页
 */
public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        LinearLayout view3 = (LinearLayout) findViewById(R.id.cl3);
        PicUtils.addView(view3);

        // 设置
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Title");
        toolbar.setSubtitle("SubTitle");
        toolbar.setLogo(R.mipmap.chose_role);

        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.btn_back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
         * (只会在第一次初始化菜单时调用)
         *
         * 在actionbar上面使用的
         */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * 菜单项被点击时调用，也就是菜单项的监听方法。
         * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象.
         * method stub
         */
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // 截图
            ScreenshotUtil.getBitmapByView(this, PicUtils.getView());
            return true;
        } else if (id == R.id.action_share || id == R.id.action_collection) {
            Toast.makeText(getBaseContext(), "还没做呢，别点了，OK！！！", Toast.LENGTH_SHORT).show();
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
