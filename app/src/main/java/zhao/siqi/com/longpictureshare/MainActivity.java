package zhao.siqi.com.longpictureshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout viewById = (LinearLayout) findViewById(R.id.cl);

        TextView nextClick = (TextView) findViewById(R.id.tv_next);

        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.addView(viewById);

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
