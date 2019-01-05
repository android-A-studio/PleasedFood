package net.hycollege.ljl.pleasedfood;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import net.hycollege.ljl.pleasedfood.Model.UserInfoModel;
import net.hycollege.ljl.pleasedfood.Model.UserInfoModelImp;
import net.hycollege.ljl.pleasedfood.bean.User;
import net.hycollege.ljl.pleasedfood.fragment.FragmentFactory;
import net.hycollege.ljl.pleasedfood.view.BNVEffect;

import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private FragmentManager fm = null;
    private FragmentTransaction transaction = null;
    public static boolean startVideo = true;
    //创建底部选项栏
    private BottomNavigationView bottomNavigationView;
    UserInfoModelImp mUserInfoModelImp = null;
    List<User> mUsers = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载界面布局
        setContentView(R.layout.activity_main);
        //初始化方法
        if (fm == null) {
            fm = getFragmentManager();
            if (transaction == null) {
                transaction = fm.beginTransaction();
            }
        }
        init();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("====","activity被激活");

        if (mUserInfoModelImp == null) {
            mUserInfoModelImp = new UserInfoModel(this);
        }
        mUsers = mUserInfoModelImp.QueryAllinfofromData();
        if (mUsers.size() > 0) {
            for (int i = 0; i < mUsers.size(); i++) {
                Log.e("mUsers=id", mUsers.get(i).getId() + "");
                Log.e("mUsers=pwd", mUsers.get(i).getPassword() + "");
                Log.e("mUsers=num", mUsers.get(i).getPhonenum() + "");
                Log.e("mUsers=info", mUsers.get(i).getUserinfobean() + "");
                Log.e("mUsers=name", mUsers.get(0).getUsername() + "");
            }
        }
    }
    FrameLayout frameLayout;
    //初始化方法
    private void init() {
        //从布局文件里面查找到控件对应的id
        bottomNavigationView = findViewById(R.id.bottomNavigation1);
        frameLayout=findViewById(R.id.fragmentview);
        //设置点击选择监听
        bottomNavigationView.setOnNavigationItemSelectedListener(NavigationItemSelectedListener);
        //改变底部选项栏属性的类
        BNVEffect.disableShiftMode(bottomNavigationView);
        //默认启动第一页面
        showFragment(0);
    }

    /**
     * 显示需要显示的Fragment
     * @param position
     */
    private void showFragment(int position) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragmentview, FragmentFactory.getFragmentIntance(position));
        transaction.commit();
    }

    //点击底部选项栏实体
    BottomNavigationView.OnNavigationItemSelectedListener NavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_menu:
                    showFragment(0);
                    return true;
                case R.id.item_set:
                    showFragment(1);
                    return true;
            }
            return false;
        }
    };
}
