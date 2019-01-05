package net.hycollege.ljl.pleasedfood.view.Settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import net.hycollege.ljl.pleasedfood.BuildConfig;
import net.hycollege.ljl.pleasedfood.R;

import java.io.File;
import java.text.DecimalFormat;



/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/15
 * owspace
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener{
    Toolbar toolBar;
    Switch pushToggle;
    TextView cacheSize;
    RelativeLayout cacheLayout;
    RelativeLayout about;
    RelativeLayout feedback;
    TextView versionTv;
    RelativeLayout checkUpgrade;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpage_settings);
        toolBar=findViewById(R.id.toolBar);
        //ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        cacheLayout=findViewById(R.id.cacheLayout);
        cacheLayout.setOnClickListener(this);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        versionTv=findViewById(R.id.version_tv);
        versionTv.setText(BuildConfig.VERSION_NAME);
        //获取Glide缓存路径
        File file = Glide.getPhotoCacheDir(this);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dd = fnum.format(getDirSize(file));
        cacheSize=findViewById(R.id.cacheSize);
        cacheSize.setText(dd + "M");
    }
    //获取目录缓存大小
    private float getDirSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                float size = 0;
                for (File f : children) {
                    size += getDirSize(f);
                }
                return size;
            } else {
                float size = (float) file.length() / 1024 / 1024;
                return size;
            }
        } else {
            return 0.0f;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cacheLayout:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getApplication()).clearDiskCache();
                    }
                }).start();
                cacheSize.setText("0.00M");
                break;
            case R.id.push_toggle:
                break;
            case R.id.about:
                break;
            case R.id.feedback:
                break;
            case R.id.checkUpgrade:
                break;
        }
    }
}
