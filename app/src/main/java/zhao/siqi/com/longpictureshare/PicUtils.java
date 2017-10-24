package zhao.siqi.com.longpictureshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 工具类
 * Created by Bill on 2017/9/22.
 */
class PicUtils {

    private static ArrayList<LinearLayout> mViewArrayList = new ArrayList<>();

    /**
     * 保存View
     *
     * @param v
     */
    public static void addView(LinearLayout v) {
        mViewArrayList.add(v);
    }

    /**
     * 获取View
     *
     * @return
     */
    public static ArrayList<LinearLayout> getView() {
        return mViewArrayList;
    }

    /**
     * 保存文件到指定路径
     *
     * @param context
     * @param bmp
     * @return
     */
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {

        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "medbanks";
        File appDir = new File(storePath);

        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            if (isSuccess) {
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 保存图片
     *
     * @param mContext
     * @param v
     */
    public static void savePhoto(Context mContext, Bitmap v, String pathfile) {
        File savedir = new File(pathfile);
        if (!savedir.exists()) {
            savedir.mkdirs();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(pathfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
        }

        try {
            if (null != out) {
                v.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }

            Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }

}
