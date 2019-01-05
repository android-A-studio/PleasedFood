package net.hycollege.ljl.pleasedfood.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import net.hycollege.ljl.pleasedfood.R;

public abstract class BaseFragment extends Fragment {
    public View fragmentView;
    public Context context;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        fragmentManager = getFragmentManager();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = initView(inflater,container);
        initData();
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (!hidden) {
//        }
            firstRefresh();
    }

    protected abstract void firstRefresh();

    /**
     * 销毁视图
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();
    }

    /**
     * 回调函数,用来解决刷新问题
     */
    @Override
    public void onResume() {
        initResume();
        super.onResume();
    }
    public abstract void initResume();
    /**
     * 获取当前的Fragment页面的View
     *
     * @return
     */
    public View getFragmentView() {
        return fragmentView;
    }

    public abstract void init();

    public abstract View initView(LayoutInflater inflater,ViewGroup container);

    public abstract void initData();

    public abstract void initEvent();

    public abstract void destroyView();

    public abstract void destroy();

    /**
     * 显示需要显示的Fragment
     * @param position
     */
    public void showFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentview, FragmentFactory.getFragmentIntance(position));
        transaction.commit();
    }
}
