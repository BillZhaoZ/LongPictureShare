package zhao.siqi.com.longpictureshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 截图操作（生成图片  拼接图片   保存图片）
 * Created by Bill on 2017/9/22.
 */
class ScreenshotUtil {

    private final static String FILE_SAVEPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String pathfile = FILE_SAVEPATH + "/ScreenshotUtil.png";

    /**
     * 因为课表是可以滑动 的所以截取
     * 截取scrollview的屏幕
     **/
    public static void getBitmapByView(Context mContext, ArrayList<LinearLayout> listView) {

        ArrayList<Bitmap> bitmaps = new ArrayList<>(); // 保存图片

        for (LinearLayout scrollView : listView) {
            // 获取listView实际高度
            int h = 0;

            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();

                // 设置背景底色
               /* int background = scrollView.getChildAt(i).getDrawingCacheBackgroundColor();
                scrollView.getChildAt(i).setBackgroundColor(background);*/
            }

            // 创建对应大小的bitmap
            Bitmap bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);

            bitmaps.add(bitmap);
        }

        // 拼接头部  尾部   暂时用不到
     /*   Bitmap head = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share_term_table_header);
        Bitmap foot = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.share_term_table_footer);*/

        // 合成图片   三张合成一张
        Bitmap v = toConformBitmap(bitmaps);

        // 保存图片到本地
        Utils.saveImageToGallery(mContext, v);

        // Utils.savePhoto(mContext, v, pathfile);
    }

    /**
     * 合并图片
     */
    private static Bitmap toConformBitmap(ArrayList<Bitmap> bitmaps) {
        if (bitmaps == null) {
            return null;
        }

        Bitmap first = null;
        Bitmap second = null;
        Bitmap third = null;

        for (int i = 0; i < bitmaps.size(); i++) {
            Bitmap bitmap = bitmaps.get(i);

            if (i == 0) {
                first = bitmap;
            } else if (i == 1) {
                second = bitmap;
            } else {
                third = bitmap;
            }
        }

        // 获取图片的宽高
        int headWidth = first.getWidth();
        int secondWidth = second.getWidth();
        int lastWidth = third.getWidth();

        int headHeight = first.getHeight();
        int secondHeight = second.getHeight();
        int lastHeight = third.getHeight();

        //生成三个图片合并大小的Bitmap
        Bitmap newBmp = Bitmap.createBitmap(secondWidth, headHeight + secondHeight + lastHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBmp);
        cv.drawBitmap(first, 0, 0, null);// 在 0，0坐标开始画入headBitmap

        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (headWidth < secondWidth) {
            System.out.println("绘制头");

            Bitmap ne = Bitmap.createBitmap(secondWidth - headWidth, headHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, headWidth, 0, null);
        }

        cv.drawBitmap(second, 0, headHeight, null);// 在 0，headHeight坐标开始填充第二张Bitmap
        cv.drawBitmap(third, 0, headHeight + secondHeight, null);// 在 0，headHeight + secondHeight 坐标开始第三张图片

        //因为手机不同图片的大小的可能小了 就绘制白色的界面填充剩下的界面
        if (lastWidth < secondWidth) {
            System.out.println("绘制");
            Bitmap ne = Bitmap.createBitmap(secondWidth - lastWidth, lastHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(ne);
            canvas.drawColor(Color.WHITE);
            cv.drawBitmap(ne, lastWidth, headHeight + secondHeight, null);
        }

        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储

        //回收
        first.recycle();
        second.recycle();
        third.recycle();

        return newBmp;
    }

}
