package net.hycollege.ljl.pleasedfood.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.bean.ArrayListFoodBean;
import net.hycollege.ljl.pleasedfood.bean.FoodBean;
import net.hycollege.ljl.pleasedfood.bean.OrderFoodBean;
import net.hycollege.ljl.pleasedfood.bean.Yieid;
import net.hycollege.ljl.pleasedfood.pay.PayResult;
import net.hycollege.ljl.pleasedfood.utils.InternetData;
import net.hycollege.ljl.pleasedfood.utils.OrderFoodData;
import net.hycollege.ljl.pleasedfood.utils.RefreshUserInfo;
import net.hycollege.ljl.pleasedfood.utils.SerializableMap;
import net.hycollege.ljl.pleasedfood.utils.StringToMap;
import net.hycollege.ljl.pleasedfood.view.RegistTransition.AActivityOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 来的订单页面
 */
public class OrderPages extends AppCompatActivity implements InternetData.DataListener {
    private RecyclerView recyclerView;
    View back, paybtn;
    TextView amountts;
    private static final int SDK_PAY_FLAG = 1;
    RefreshUserInfo refreshUserInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderpages);
        back = findViewById(R.id.toolbar_Order);
        amountts = findViewById(R.id.amount);
        paybtn = findViewById(R.id.txt_order_submit);
        orderConsolidation();
        initRecyclerView();
        initEvent();
        refreshUserInfo = new RefreshUserInfo(this);

    }
    //订单整理
    private void orderConsolidation() {
        Log.e("whats",OrderFoodBean.getFoodBean()+"");
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> l=new ArrayList<String>();
        for(Map<String, Object> obj:OrderFoodBean.getFoodBean()){
            l.add(obj+"");
        }
        for(String item: l){
            if(map.containsKey(item)){
                map.put(item, map.get(item).intValue() + 1);
            }else{
                map.put(item, new Integer(1));
            }
        }

        Iterator<String> keys = map.keySet().iterator();
        StringBuffer sb=new StringBuffer();
        List<Map<String,String>> dumpFood=new ArrayList<>();
        while(keys.hasNext()){
            String key = keys.next();
            //测试
            sb.append(key/* + "x" + map.get(key).intValue() + ", "*/);
            //string转map
            Map<String,String> countryMap=StringToMap.stringToMap(key);
            //加入数量
            countryMap.put("num",map.get(key).intValue()+"");
            Log.e("aa",countryMap+"");
            //保存
            dumpFood.add(countryMap);
        }
        //保存到内存
        OrderFoodBean.setFoodBeansAndNum(dumpFood);
    }

    //返回
    private void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //完成支付
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登陆状态
                if (refreshUserInfo.getLoginState()) {
                    payV2();
                } else {
                    //执行动画跳转到登陆页面
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(OrderPages.this);
                    //准备加载登陆页面
                    Intent i2 = new Intent(v.getContext(), AActivityOne.class);
                    //跳转到登陆页面
                    startActivity(i2, oc2.toBundle());
                }
            }
        });
    }

    String amounts=null;
    /**
     * 支付宝支付业务示例
     */
    public void payV2() {

        if(!amounts.equals("")){
            InternetData.getRequest(Yieid.userAplipay, amounts, this);
        }
    }
    /**
     * 对订单列表的初始化
     */
    private void initRecyclerView() {//对recyclerview进行实例化

        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //订单列表页面
        OrderRecyclerView orderRecyclerView = new OrderRecyclerView(OrderFoodBean.getFoodBeanAndNum(),this);
        //取总金额
        orderRecyclerView.setGerAmountListener(new OrderRecyclerView.OnAmountListener() {
            @Override
            public void onAddListAmount(int amount) {
                Log.e("amount",amount+"");
                amounts=amount+"";
                amountts.setText("合计￥"+amounts+"");
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(orderRecyclerView);
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(final Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        View centerview = layoutInflater.inflate(R.layout.activity_set_editpassworddialog, null);
        TextView textView = centerview.findViewById(R.id.messages);
        textView.setText(info);
        final Button button = centerview.findViewById(R.id.payfinisbtn);
        final AlertDialog alertDialog = builder.setView(centerview).create();
        alertDialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    String orderInfo = "";
    //支付信息获取
    @Override
    public void getdata(String data) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            if (jsonObject.getString("paystatus").equals("true")) {

                orderInfo = jsonObject.getString("aplipay");
                Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(OrderPages.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.w("result", payResult + "");
                        showAlert(OrderPages.this, "支付成功: 谢谢品尝\n ^_^" /*+ payResult*/);
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Log.w("result", payResult + "");
                        showAlert(OrderPages.this, "支付失败: 未完成支付\n请到订单页面完成支付");
                    }
                    break;
                }
            }
        }

        ;
    };
}
