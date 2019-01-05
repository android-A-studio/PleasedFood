package net.hycollege.ljl.pleasedfood.fragment;

import android.util.SparseArray;
/**
 * 生产Fragment的工厂
 *
 * @author GaoWeiJing
 */
public class FragmentFactory {

    public static final int MenuFragment = 0;
    public static final int SetFragment = 1;
    public static final int AutoStartFragment = 6;
    public static int curFrgment = AutoStartFragment;
    // 提高效率
    private static SparseArray<BaseFragment> fragmentMap = new SparseArray<BaseFragment>();
    /**
     * 在Fragment工厂中获取一个Fragment
     *
     * @param position
     */
    public static BaseFragment getFragmentIntance(int position) {
        curFrgment = position;
        BaseFragment fragment = null;
        BaseFragment newFragment = fragmentMap.get(position);
        if (newFragment != null) {
            fragment = newFragment;
            return fragment;
        }
        switch (position) {
            case MenuFragment:
              fragment= new getFragmentMenu();
                break;
            case SetFragment:
                //显示设置页面
                fragment=new getFragmentMe();
                break;
        }
        fragmentMap.put(position, fragment);
        return fragment;
    }

}
