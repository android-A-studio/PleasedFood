package net.hycollege.ljl.pleasedfood.view.RegistTransition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import net.hycollege.ljl.pleasedfood.Model.UserInfoModel;
import net.hycollege.ljl.pleasedfood.Model.UserInfoModelImp;
import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.bean.User;
import net.hycollege.ljl.pleasedfood.bean.Yieid;
import net.hycollege.ljl.pleasedfood.utils.InternetData;
import net.hycollege.ljl.pleasedfood.utils.PasswordTool;
import net.hycollege.ljl.pleasedfood.view.FindUserBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 这个是登陆页面
 * 整个界面和功能的实现都在这里面
 */
public class AActivityOne extends AppCompatActivity implements InternetData.DataListener {

    private EditText etUsername;
    private TextView mTextView;
    private EditText etPassword;
    String phonenums;
    private CardView btGo;
    private CardView cv;
    final int isSuccess = 1;
    private FloatingActionButton fab;
    UserInfoModelImp userInfoModelImp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_one);
        if (userInfoModelImp == null) {
            //实例化数据库操作类
            userInfoModelImp = new UserInfoModel(this);
        }
        initView();
        setListener();
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        mTextView = findViewById(R.id.findUserBack);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
    }

    /**
     * 设置事件监听
     */
    private void setListener() {
        //跳转到找回用户密码页面
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explode explode = new Explode();
                explode.setDuration(500);
                getWindow().setExitTransition(explode);
                getWindow().setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(AActivityOne.this);
                Intent i2 = new Intent(v.getContext(), FindUserBack.class);
                startActivity(i2, oc2.toBundle());
            }
        });
        //登陆按钮监听
        btGo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                //这里不能用finish(),因为使用了动画,否则容易触发bug
                //finish();
                phonenums = etUsername.getText().toString();
                String passwords = etPassword.getText().toString();
                if (PasswordTool.isSetUserInfo(phonenums, passwords)) {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("phonenum", phonenums.trim());
                        obj.put("password", passwords);
                        //
                        InternetData.getRequest(Yieid.userLogin, obj.toString(), AActivityOne.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    AActivityOne.super.onBackPressed();
                } else {
                    Toast.makeText(AActivityOne.this, "请完整正确填写信息", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //注册
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AActivityOne.this, fab, fab.getTransitionName());
                startActivity(new Intent(AActivityOne.this, AActivityTwo.class), options.toBundle());
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setVisibility(View.VISIBLE);
    }

    //网络数据接收
    List<User> search = null;

    /**
     * 得到服务器返回数据
     * @param data
     */
    @Override
    public void getdata(String data) {
        Log.w("data", data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getString("loginstatus").equals("true")) {
                Gson gson = new Gson();
                //解释json数据,并封装于User对象中
                final User user = gson.fromJson(data, User.class);
                user.setPassword(etPassword.getText().toString());
                user.setUserinfobean("success");
                Log.e("test",user.getUsername());
                search = userInfoModelImp.QueryAllinfofromData();
                ////查询本地数据库如果没有记录,就插入数据
                if (search.size() == 0) {
                    userInfoModelImp.InsertUserData(user);
                } else {
                    List<User> issert = userInfoModelImp.getSearchfromId(user.getId());
                    //查询不到存在同Id的记录,就插入数据
                        userInfoModelImp.DeleteNotefromData(user);
                        userInfoModelImp.InsertUserData(user);
                }
                //根据Userinfobean 搜索数据
                search = userInfoModelImp.QueryAllinfofromData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < search.size(); i++) {
                            Log.e("getId==", search.get(i).getId() + search.get(i).getUsername() + search.get(i).getPassword() + search.get(i).getUserinfobean());
                        }
                    }
                });
                Message message = new Message();
                message.what = isSuccess;
                handler.sendMessage(message);
            }
            if (jsonObject.getString("loginstatus").equals("false")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AActivityOne.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.w("data", "登陆失败");
            }
            AActivityOne.super.onBackPressed();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * handler,就是消息机制
     * 负责程序内部消息的接收和处理
     * 当有地方调用handler.sendMessage(message)方法时
     * 它就能收到具有msg.what下标的数据,
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case isSuccess:
                    AActivityOne.super.onBackPressed();
                    break;
            }
            return false;
        }
    });
}
