package net.hycollege.ljl.pleasedfood.utils;

import android.content.Context;

import net.hycollege.ljl.pleasedfood.Model.UserInfoModel;
import net.hycollege.ljl.pleasedfood.Model.UserInfoModelImp;
import net.hycollege.ljl.pleasedfood.bean.User;
import net.hycollege.ljl.pleasedfood.bean.Yieid;
import net.hycollege.ljl.pleasedfood.fragment.getFragmentMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RefreshUserInfo implements InternetData.DataListener {


    public RefreshUserInfo(Context context) {
        if (userInfoModelImp == null) {
            userInfoModelImp = new UserInfoModel(context);
        }
    }

    public UserInfoModelImp userInfoModelImp = null;
    public List<User> isSuccess = null;
    /**
     * 获取当前登陆状态
     */
    List<User> search = null;

    public boolean getLoginState() {
        boolean mBoolean = true;
        //查询本地数据库如果没有记录,就插入数据
        isSuccess = userInfoModelImp.getSearchfromState("success");
        if (isSuccess.size() == 0) {
            mBoolean = false;
            return mBoolean;
        } else {
            //进行数据更新
            JSONObject obj = new JSONObject();
            try {
                obj.put("phonenum", isSuccess.get(0).getPhonenum());
                obj.put("password", isSuccess.get(0).getPassword());
                //用户密码进行网络请求匹配
                InternetData.getRequest(Yieid.userLogin, obj.toString(), this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mBoolean;
        }
    }

    public void unlogin() {
        search = userInfoModelImp.QueryAllinfofromData();
        ////查询本地数据库如果没有记录,就插入数据
        if (search.size() > 0) {
            isSuccess = userInfoModelImp.getSearchfromState("success");
            isSuccess.get(0).setUserinfobean("aaa");
            userInfoModelImp.ChangeNotetoData(isSuccess.get(0));
        }
    }

    @Override
    public void getdata(String data) {

        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.getString("loginstatus").equals("true")) {
                //逻辑待完善
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
