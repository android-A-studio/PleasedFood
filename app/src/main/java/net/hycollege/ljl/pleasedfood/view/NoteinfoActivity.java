package net.hycollege.ljl.pleasedfood.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.hycollege.ljl.pleasedfood.R;

/**
 * 菜单详细界面类
 * 点击了菜单后会来到这里
 */
public class NoteinfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteinfo);
    }
}
