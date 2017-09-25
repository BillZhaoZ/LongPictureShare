package zhao.siqi.com.longpictureshare;

import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Bill on 2017/9/22.
 */

public class Utils {

    private static ArrayList<LinearLayout> mViewArrayList = new ArrayList<>();

    public static void addView(LinearLayout v) {
        mViewArrayList.add(v);
    }

    public static ArrayList<LinearLayout> getView() {
        return mViewArrayList;
    }
}
