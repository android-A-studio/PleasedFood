package net.hycollege.ljl.pleasedfood.view.aboutme;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.graphics.Bitmap;

import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.utils.SnackbarUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class AboutMe extends AppCompatActivity {
    Button button;
    private static final String SD_PATH = "/sdcard/zzmw/pic/";
    private static final String IN_PATH = "/zzmw/pic/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutme);
        button = findViewById(R.id.picture);

        initEvent();

    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap cacheBitmap = screenShotWholeScreen();
                String path=saveBitmap(AboutMe.this, cacheBitmap);
                SnackbarUtils.Short(button,"保存照片到"+path).leftAndRightDrawable(R.mipmap.i9,R.mipmap.i11).show();
                //退出当前页面
                //延迟执行
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
//                        AboutMe.super.onBackPressed();
                    }
                }, 2000);
            }
        });
    }

    /**
     * 调用系统截图功能获取屏幕截图
     *
     * @return
     */
    public Bitmap getScreenShot(View view) {
        view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    /**
     * 可以利用android为了提高滚动等各方面的绘制速度，为每一个view创建了一个缓存，使用    View.buildDrawingCache方法可以获取相应view的cache，这个cache就是一个bitmap对象
     *
     * @return Bitmap
     */

    private Bitmap screenShotWholeScreen() {
        View dView = getWindow().getDecorView().getRootView();//拿到当前屏幕的rootview
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = dView.getDrawingCache();
        return bitmap;
    }


    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }


}
