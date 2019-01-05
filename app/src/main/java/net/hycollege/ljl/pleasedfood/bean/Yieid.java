package net.hycollege.ljl.pleasedfood.bean;

import android.content.Context;
import android.content.Intent;
import android.transition.Explode;

import net.hycollege.ljl.pleasedfood.fragment.getFragmentMe;
import net.hycollege.ljl.pleasedfood.view.FindUserBack;
import android.support.v4.app.ActivityOptionsCompat;

import net.hycollege.ljl.pleasedfood.view.RegistTransition.AActivityOne;

public class Yieid {
    public static String userAplipay="http://47.106.170.112:8080/alipay_web/userAplipay";//支付宝支付链接
    private static String urls="http://47.106.170.112:8080/FoodWebs";
    public static String foodMenu=urls+"/foodMenu";//远程服务器链接--菜单列表
    public static String urlregist = urls+"/register";//远程服务器链接--注册
    public static String urluserexist = urls+"/isUserExist";//远程服务器链接--判断用户是否存在
    public static String userLogin = urls+"/userLogin";//远程服务器链接--登陆

    private final static int REQUEST_CODE = 1;
    /**
     * 启动登陆页面
     * @param context
     * @param activity
     */
    public static void toLoginPage(Context context, getFragmentMe activity){
        //动画实现
        Explode explode = new Explode();
                explode.setDuration(500);

                activity.setExitTransition(explode);
                activity.setEnterTransition(explode);
                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(activity.getActivity());
                Intent i2 = new Intent(context, AActivityOne.class);
                context.startActivity(i2, oc2.toBundle());
    }

    public static void toUserBack(Context context){
        Intent mintent=new Intent(context.getApplicationContext(),FindUserBack.class);
        context.startActivity(mintent);
    }
}
