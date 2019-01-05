package net.hycollege.ljl.pleasedfood.fragment;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.hycollege.ljl.pleasedfood.MainActivity;
import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.bean.ArrayListFoodBean;
import net.hycollege.ljl.pleasedfood.bean.FoodBean;
import net.hycollege.ljl.pleasedfood.bean.Yieid;
import net.hycollege.ljl.pleasedfood.utils.InternetData;
import net.hycollege.ljl.pleasedfood.utils.ScaleTransformer0;
import net.hycollege.ljl.pleasedfood.view.ViewPagerListAdapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class getFragmentMenu extends BaseFragment implements View.OnClickListener, InternetData.DataListener {
    private View view = null;
    ViewPager viewPager, viewPagerlist;
    public List<FoodBean> foodBean = null;
    ViewPagerListAdapter listAdapter = null;
    ProgressBarCircularIndeterminate mProgressBarCircularIndeterminate = null;
    Timer t;

    @Override
    protected void firstRefresh() {
    }


    /**
     * 回调函数,用来解决刷新问题
     */
    @Override
    public void initResume() {
        //延迟执行
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void init() {
        t = new Timer();
        if (foodBean == null) {
            foodBean = new ArrayList<FoodBean>();
        }
        InternetData.getRequest(Yieid.foodMenu, null, this);
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        view = View.inflate(context, R.layout.fragmentmenu, null);
        initFoodData();
        viewPagerlist = view.findViewById(R.id.viewpager_list);
        mProgressBarCircularIndeterminate = view.findViewById(R.id.progressBarCircularIndetermininate);
        //请求网络数据更多列表
        listAdapter = new ViewPagerListAdapter(getActivity());
        return view;
    }

    //保存食物信息
    private void initFoodData() {
        listitem = new ArrayList<Map<String, Object>>();
        list = foodBean;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", list.get(i).getName_f());
            map.put("money", list.get(i).getPrice_f());
            map.put("pic", list.get(i).getPicture_f());
            listitem.add(map);
        }
        //把菜谱信息存储起来
        ArrayListFoodBean.setFoodBeans(foodBean);
    }

    private static List<Map<String, Object>> listitem;
    private List<FoodBean> list = null;
    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        gotoRunRetFragment();
        mProgressBarCircularIndeterminate.setVisibility(View.VISIBLE);
        viewPagerlist.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        viewPagerlist.setVisibility(View.VISIBLE);

        if (foodBean.size() == 0) {
            InternetData.getRequest(Yieid.foodMenu, null, this);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //异步类,用来实现数据刷新
                    new MyAsyncTask().execute();
                }
            }, 500);
        } else {
            viewPagerlist.setAdapter(listAdapter);
        }
    }
    Runnable runnable;
    //异步类,用来实现数据刷新
    class MyAsyncTask extends AsyncTask<Boolean, Boolean, Boolean> {
        //onPreExecute用于异步处理前的操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //此处将progressBar设置为可见.
            mProgressBarCircularIndeterminate.setVisibility(View.VISIBLE);
        }

        //在doInBackground方法中进行异步任务的处理.
        @Override
        protected Boolean doInBackground(Boolean... voids) {
            SystemClock.sleep(1000);
            if (foodBean.size() > 0) {
                a = true;
            }
            return a;
        }

        //onPostExecute用于UI的更新.此方法的参数为doInBackground方法返回的值.
        @Override
        protected void onPostExecute(Boolean v) {
            super.onPostExecute(v);
            if (v) {
                //如果是在fragment中刷新viewpage,一定要放到runOnUiThread中
                try {
                    initFoodData();
                    listAdapter = new ViewPagerListAdapter(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewPagerlist.setAdapter(listAdapter);
                mProgressBarCircularIndeterminate.setVisibility(View.INVISIBLE);
            }
            a = false;
        }
    }

    /**
     * 创建个加载视图线程
     */
    boolean a = false;

    @Override
    public void destroyView() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 切换到RunRetFragment
     */
    public void gotoRunRetFragment() {
        MainActivity.startVideo = false;
        showFragment(FragmentFactory.MenuFragment);
    }

    /**
     * 获取网络响应数据
     * @param data
     */
    @Override
    public void getdata(String data) {
        Gson gson = new Gson();
        //解释json数据,并封装于User对象中
        foodBean = gson.fromJson(data, new TypeToken<List<FoodBean>>() {
        }.getType());
        Log.e("test",foodBean.get(0)+"");
        Log.e("test",foodBean.get(1)+"");
    }

    /**
     * 离开后执行的方法
     */
    @Override
    public void destroy() {
        runnable=null;
        t.cancel();
        a = false;
    }
}
