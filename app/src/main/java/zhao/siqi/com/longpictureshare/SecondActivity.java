package zhao.siqi.com.longpictureshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 第二页
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final LinearLayout viewById2 = (LinearLayout) findViewById(R.id.cl2);
        TextView nextClick = (TextView) findViewById(R.id.tv_next);

        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PicUtils.addView(viewById2);

                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }
}
