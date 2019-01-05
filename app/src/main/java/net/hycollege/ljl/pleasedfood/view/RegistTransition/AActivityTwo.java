package net.hycollege.ljl.pleasedfood.view.RegistTransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.bean.Yieid;
import net.hycollege.ljl.pleasedfood.utils.InternetData;
import net.hycollege.ljl.pleasedfood.utils.PasswordTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 这是注册界面类
 * 整个界面和功能的实现都在这里面
 */
public class AActivityTwo extends AppCompatActivity implements View.OnClickListener, InternetData.DataListener {
    final int register = 1;//注册成功状态码
    final int isUserExist = 2;//判断用户是否重复状态码
    final int isUserNoExist = 3;//判断用户是否重复状态码
    final int isPassIsOk = 4;//判断密码是否重复状态码
    String usernames;
    Button gobutton;
    private FloatingActionButton fab;
    private CardView cvAdd;
    EditText username, password, phonenum;
    String userIsExist = "false";//用户是否存在标识符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_two);
        ShowEnterAnimation();
        initView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    private void initView() {
        gobutton = findViewById(R.id.bt_go);
        phonenum = findViewById(R.id.phonenum);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        //初始化事件
        initEven();
    }

    private void initEven() {
        username.addTextChangedListener(mTextWatcher);
        phonenum.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
        gobutton.setOnClickListener(this);
    }

    /**
     * 动画开始
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    /**
     * 显示时的动画
     */
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    /**
     * 关闭时的动画
     */
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.mipmap.plus);
                AActivityTwo.super.onBackPressed();
            }
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
    /**
     * 注册界面按钮点击
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_go:
                usernames = username.getText().toString();
                String passwords = password.getText().toString();
                String phonenums = phonenum.getText().toString();
                if (PasswordTool.isSetUserInfo(this,usernames, passwords, phonenums, userIsExist)) {
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("username", usernames.trim());
                        obj.put("password", passwords);
                        obj.put("phonenum", phonenums);
                        InternetData.getRequest(Yieid.urlregist, obj.toString(), this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    animateRevealClose();
                } else {
                    Log.e("TAG","请完整正确填写信息");
                }
                break;
        }
    }
    /**
     * 接收服务器返回的数据
     * 进一步做判断
     *
     * @param data 返回的数据
     */
    @Override
    public void getdata(String data) {
        if (!data.equals("")) {
            Log.w("code", data);
            try {
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.getString("register").equals("ok")) {
                    Message message = new Message();
                    message.what = register;
                    message.obj = jsonObject;
                    handler.sendMessage(message);
                }
                if (jsonObject.getString("register").equals("isExist")) {
                    Message message = new Message();
                    message.what = isUserExist;
                    message.obj = jsonObject;
                    handler.sendMessage(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Message message = new Message();
            message.what = isUserNoExist;
            message.obj = "";
            handler.sendMessage(message);
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
            try {
                JSONObject jsonObject;
                switch (msg.what) {
                    case register:
                        Toast.makeText(AActivityTwo.this, "接受的数据" + msg.obj, Toast.LENGTH_SHORT).show();
                        break;
                    case isUserExist:
                        jsonObject = new JSONObject(String.valueOf(msg.obj));
                        userIsExist = "true";
                        Toast.makeText(AActivityTwo.this,"用户" + jsonObject.getString("username") + "已经存在",Toast.LENGTH_SHORT).show();
                        break;
                    case isUserNoExist:
                        userIsExist = "false";
                        break;
                    case isPassIsOk:
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    });
    /**
     * 输入框监听,
     * afterTextChanged在用户输入内容后执行的方法
     * 这里进行了
     * 用户是否存在
     * 和
     * 密码是否合格的判断
     */
    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {        }
        String passwords;
        @Override
        public void afterTextChanged(Editable s) {
            usernames = username.getText().toString();
            passwords = password.getText().toString();
            Log.w("editable", s + "");
            try {
                JSONObject obj = new JSONObject();
                obj.put("username", usernames.trim());
                //数据提交,让服务器判断用户是否存在,返回的结果在getdata()方法
                InternetData.getRequest(Yieid.urluserexist, obj.toString(), AActivityTwo.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (passwords.length() < 6 || passwords.length() > 18) {
                Message message = new Message();
                message.what = isPassIsOk;
                message.obj = "密码不合格";
                handler.sendMessage(message);

            } else {
                Message message = new Message();
                message.what = isPassIsOk;
                message.obj = "";
                handler.sendMessage(message);
            }
        }
    };
}
